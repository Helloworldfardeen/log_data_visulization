package com.graph.graph.controllers;

import java.io.IOException;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.graph.graph.payload.FileResponse;
import com.graph.graph.services.FileServices;

import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/file")
public class FileController {

	@Autowired
	private FileServices fileService;

	@Value("${project.csv}")
	private String path1;

	@Value("${project.image}")
	private String path2;

	@PostMapping("/upload")//http://localhost:8081/url/file/upload
	public ResponseEntity<FileResponse> fileUpload(@RequestParam("csv") MultipartFile file,
			@RequestParam("graphName") String graphName) {
		try {
			String fileName = this.fileService.uploadImage(path1, file, graphName);
			return new ResponseEntity<>(new FileResponse(fileName, "File is successfully uploaded"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new FileResponse(null, "File is not uploaded due to error on server!!"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Method to serve file

	@GetMapping("/images/{imageName}")
	public void downloadFile(@PathVariable("imageName") String imageName, HttpServletResponse response) {
		InputStream resource = this.fileService.getResource(path2, imageName);

		response.setContentType(MediaType.ALL_VALUE);
		try {
			StreamUtils.copy(resource, response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}