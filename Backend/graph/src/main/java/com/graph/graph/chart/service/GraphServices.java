package com.graph.graph.chart.service;

import java.io.IOException;

/**
 * Interface for generating different types of graphs.
 */
public interface GraphServices {

    /**
     * Generates a graph based on the data from the specified file and saves it with the given image name.
     *
     * @param path       The path to the file containing the data for the graph.
     * @param imageName  The name of the image file to save the generated graph.
     * @throws IOException If an error occurs while reading the data or saving the graph.
     */
    void generateGraph(String path, String imageName) throws IOException;
}
