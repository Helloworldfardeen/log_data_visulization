package com.graph.graph.charts;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.springframework.beans.factory.annotation.Value;

import com.graph.graph.CSVReading.ReadingData;
import com.graph.graph.chart.services.GraphServices;
import com.opencsv.exceptions.CsvValidationException;

public class Histogram implements GraphServices {

	@Value("${project.image}")
	String imagePath;

	@Override
	public void generateGraph(String path, String imageName) throws IOException, CsvValidationException {
		System.out.println(path);
		ReadingData data = new ReadingData(path);
		Map<String, List<Integer>> histogramData = data.getHistogramData();
		String lab = "";
		double values[] = {};
		for (Map.Entry<String, List<Integer>> e : histogramData.entrySet()) {
			lab = e.getKey();
			int i = 0;
			values = new double[e.getValue().size()];
			for (int num : e.getValue()) {
				values[i] = (double) num;
				i++;
			}
		}

		HistogramDataset dataset = new HistogramDataset();
		dataset.addSeries("key", values, 20);

		JFreeChart chart = ChartFactory.createHistogram("JFreeChart Histogram", lab, "Frequency", dataset,
				PlotOrientation.VERTICAL, false, false, false);
		String pathImage = String.format("path_to_the_folder_where_you_want_to_store_the_image\\image\\%s", imageName);
		try {
			ChartUtilities.saveChartAsJPEG(new File(pathImage), chart, 500, 300);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}