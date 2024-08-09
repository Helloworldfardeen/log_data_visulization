package com.graph.graph.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
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

@CrossOrigin(origins = "http://localhost:3000/app", maxAge = 3600)
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileServices fileService;

    @Value("${project.csv}")
    private String csvPath; // Path where uploaded CSV files are stored

    @Value("${project.image}")
    private String imagePath; // Path where generated images are stored

    /**
     * Endpoint to upload a CSV file and associate it with a graph name.
     *
     * @param file The CSV file to be uploaded.
     * @param graphName The name of the graph associated with the file.
     * @return ResponseEntity containing the result of the upload operation.
     */
    @PostMapping("/upload")
    public ResponseEntity<FileResponse> fileUpload(
            @RequestParam("csv") MultipartFile file,
            @RequestParam("graphName") String graphName) {
        try {
            // Upload the file using the FileServices class
            String fileName = fileService.uploadImage(csvPath, file, graphName);
            // Return success response
            return ResponseEntity.ok(new FileResponse(fileName, "File is successfully uploaded"));
        } catch (Exception e) {
            // Log error and return error response
            e.printStackTrace(); // Consider using a logger here
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new FileResponse(null, "File is not uploaded due to server error"));
        }
    }

    /**
     * Endpoint to download an image file.
     *
     * @param imageName The name of the image file to be downloaded.
     * @return ResponseEntity containing the image file data.
     */
    @GetMapping("/images/{imageName}")
    public ResponseEntity<byte[]> downloadFile(
            @PathVariable("imageName") String imageName) {
        // Create a File object for the requested image
        File file = new File(imagePath, imageName);
        
        // Check if the file exists
        if (!file.exists()) {
            // Return a 404 Not Found response if the file does not exist
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(null);
        }

        try (InputStream resource = new FileInputStream(file)) {
            // Read the file data into a byte array
            byte[] data = StreamUtils.copyToByteArray(resource);
            
            // Prepare HTTP headers for the response
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Set content type; adjust if necessary
            headers.setContentDispositionFormData("attachment", imageName); // Set the file name for download
            
            // Return the image data with the appropriate headers
            return ResponseEntity.ok()
                                 .headers(headers)
                                 .body(data);
        } catch (IOException e) {
            // Log error and return error response
            e.printStackTrace(); // Consider using a logger here
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(null);
        }
    }
}
