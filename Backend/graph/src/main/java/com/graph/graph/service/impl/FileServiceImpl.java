package com.graph.graph.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.graph.graph.charts.AreaChart;
import com.graph.graph.charts.BarChart;
import com.graph.graph.charts.BubbleChart;
import com.graph.graph.charts.Histogram;
import com.graph.graph.charts.LineChart;
import com.graph.graph.charts.PieChart;
import com.graph.graph.charts.ScatterPlot;
import com.graph.graph.chart.service.GraphServices;
import com.graph.graph.services.FileServices;

@Service
public class FileServiceImpl implements FileServices {

    @Value("${project.image}")
    private String imagePath; // Path where images will be stored

    private final Map<String, GraphServices> chartGenerators = new HashMap<>();

    /**
     * Constructor initializes chart generators for various chart types.
     */
    public FileServiceImpl() {
        chartGenerators.put("histogram", new Histogram());
        chartGenerators.put("area", new AreaChart());
        chartGenerators.put("bubble", new BubbleChart());
        chartGenerators.put("scatter", new ScatterPlot());
        chartGenerators.put("pie", new PieChart());
        chartGenerators.put("line", new LineChart());
        chartGenerators.put("bar", new BarChart());
    }

    /**
     * Uploads a file to the specified path and generates a chart based on the provided graph name.
     *
     * @param path       The path where the file will be uploaded.
     * @param file       The file to be uploaded.
     * @param graphName  The name of the chart to be generated.
     * @return           The name of the generated image file.
     * @throws IOException If an error occurs during file operations.
     */
    @Override
    public String uploadImage(String path, MultipartFile file, String graphName) throws IOException {
        // Generate a random file name
        String originalFilename = file.getOriginalFilename();
        String randomID = UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String fileName = randomID.concat(extension);

        String filePath = Paths.get(imagePath, fileName).toString();

        // Create directory if it does not exist
        File directory = new File(imagePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Copy file to the specified path
        try {
            Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Error copying the file to " + filePath, e);
        }

        // Generate the chart using the appropriate chart generator
        String imageName = randomID + ".jpeg";
        GraphServices chartGenerator = chartGenerators.get(graphName.toLowerCase());
        if (chartGenerator != null) {
            chartGenerator.generateGraph(filePath, imageName);
        } else {
            throw new IllegalArgumentException("Invalid graph name: " + graphName);
        }

        return imageName;
    }

    /**
     * Retrieves a file as an InputStream from the specified path.
     *
     * @param path       The base path where the file is located.
     * @param imageName  The name of the image file.
     * @return           An InputStream for the file, or null if the file is not found.
     */
    @Override
    public InputStream getResource(String path, String imageName) {
        String fullPath = Paths.get(imagePath, imageName).toString();
        try {
            System.out.println("Looking for file at: " + fullPath);
            return new FileInputStream(fullPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
