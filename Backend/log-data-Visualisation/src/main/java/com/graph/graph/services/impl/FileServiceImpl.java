package com.graph.graph.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.graph.graph.charts.AreaChart;
import com.graph.graph.charts.BarChart;
import com.graph.graph.charts.BubbleChart;
import com.graph.graph.charts.Histogram;
import com.graph.graph.charts.LineChart;
import com.graph.graph.charts.PieChart;
import com.graph.graph.charts.ScatterPlot;
import com.graph.graph.services.FileServices;
import com.opencsv.exceptions.CsvValidationException;

@Service
public class FileServiceImpl implements FileServices {

	@Override
	public String uploadImage(String path, MultipartFile file, String graphName) throws IOException {

		// File name
		String name = file.getOriginalFilename();
		// Fullpath

		String randomID = UUID.randomUUID().toString();
		String fileName1 = randomID.concat(name.substring(name.lastIndexOf('.')));

		String filePath = String.format("C:/Users/izaan/Downloads//%s", fileName1);
		// Create Folder if not created
		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}
		// File copy
		try {
			Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String imageName = randomID + ".jpeg";
		if ("histogram".equals(graphName)) {
			try {
				new Histogram().generateGraph(filePath, imageName);
			} catch (CsvValidationException e) {
				System.out.println("here is here solve this");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if ("area".equals(graphName)) {
			try {
				new AreaChart().generateGraph(filePath, imageName);
			} catch (CsvValidationException e) {
				System.out.println("here is here solve this");
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if ("bubble".equals(graphName)) {
			try {
				new BubbleChart().generateGraph(filePath, imageName);
			} catch (CsvValidationException e) {
				System.out.println("here is here solve this");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if ("scatter".equals(graphName)) {
			try {
				new ScatterPlot().generateGraph(filePath, imageName);
			} catch (CsvValidationException e) {
				System.out.println("here is here solve this");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if ("pie".equals(graphName)) {
			try {
				new PieChart().generateGraph(filePath, imageName);
			} catch (CsvValidationException e) {
				System.out.println("here is here solve this");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if ("line".equals(graphName)) {
			try {
				new LineChart().generateGraph(filePath, imageName);
			} catch (CsvValidationException e) {
				System.out.println("here is here solve this");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if ("bar".equals(graphName)) {
			try {
				new BarChart().generateGraph(filePath, imageName);
			} catch (CsvValidationException e) {
				System.out.println("here is here solve this");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return imageName;
	}

	@Override
	public InputStream getResource(String path, String imageName) {
		String fullPath = String.format("C:/Users/izaan/Downloads//%s", imageName);
		InputStream is;
		try {
			is = new FileInputStream(fullPath);
			return is;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

}