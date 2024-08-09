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
import com.graph.graph.chart.service.GraphServices;

public class Histogram implements GraphServices {

	@Value("${project.image}")
	String imagePath;

	@Override
	public void generateGraph(String path, String imageName) throws IOException {
		System.out.println(path);
		ReadingData data = new ReadingData(path);
		Map<String, List<Integer>> histogramData = data.getHistogramData();
		String label = "";
		double[] values = {};
		for (Map.Entry<String, List<Integer>> entry : histogramData.entrySet()) {
			label = entry.getKey();
			values = new double[entry.getValue().size()];
			for (int i = 0; i < entry.getValue().size(); i++) {
				values[i] = (double) entry.getValue().get(i);
			}
		}

		HistogramDataset dataset = new HistogramDataset();
		dataset.addSeries(label, values, 20);

		JFreeChart chart = ChartFactory.createHistogram("JFreeChart Histogram", label, "Frequency", dataset,
				PlotOrientation.VERTICAL, true, true, false);

		String pathImage = String.format("%s%s", imagePath, imageName);
		try {
			ChartUtilities.saveChartAsJPEG(new File(pathImage), chart, 500, 300);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}