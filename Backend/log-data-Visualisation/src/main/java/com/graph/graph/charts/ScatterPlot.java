package com.graph.graph.charts;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.springframework.beans.factory.annotation.Value;

import com.graph.graph.CSVReading.ReadingData;
import com.graph.graph.chart.services.GraphServices;
import com.opencsv.exceptions.CsvValidationException;

public class ScatterPlot implements GraphServices {

	@Value("${project.image}")
	String imagePath;

	@Override
	public void generateGraph(String path, String imageName) throws IOException, CsvValidationException {
		ReadingData data = new ReadingData(path);
		Map<String, List<String>> scatterData = data.getScatterData();
		XYSeriesCollection dataset = new XYSeriesCollection();

		for (Map.Entry<String, List<String>> e : scatterData.entrySet()) {
			XYSeries series = new XYSeries(e.getKey());
			for (String points : e.getValue()) {
				String[] res = points.split(",");
				series.add(Integer.parseInt(res[0]), Integer.parseInt(res[1]));
			}
			dataset.addSeries(series);
		}

		JFreeChart chart = ChartFactory.createScatterPlot("JFreeChart Scatter Plot", // Chart title
				"Age", // X-Axis Label
				"Weight", // Y-Axis Label
				dataset // Dataset for the Chart
		);
		String pathImage = String.format("path_to_the_folder_where_you_want_to_store_the_image\\%s", imageName);

		try {
			ChartUtilities.saveChartAsJPEG(new File(pathImage), chart, 500, 300);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}