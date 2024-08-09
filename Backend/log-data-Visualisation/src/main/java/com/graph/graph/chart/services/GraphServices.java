package com.graph.graph.chart.services;

import java.io.IOException;

import com.opencsv.exceptions.CsvValidationException;

public interface GraphServices {
 public void generateGraph(String path, String imageName) throws IOException, CsvValidationException;
}