package com.graph.graph.charts;

import java.io.File;
import java.io.IOException;

import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Value;

import com.graph.graph.CSVReading.ReadingData;
import com.graph.graph.chart.services.GraphServices;
import com.opencsv.exceptions.CsvValidationException;

public class BarChart implements GraphServices {

	@Value("${project.image}")
	String imagePath;

	@Override
	public void generateGraph(String path, String imageName) throws IOException, CsvValidationException {
		ReadingData data = new ReadingData(path);
		Map<String, Map<String, Double>> barData = data.getBarData();

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (Map.Entry<String, Map<String, Double>> e : barData.entrySet()) {
			for (Map.Entry<String, Double> m : e.getValue().entrySet()) {
				dataset.addValue(m.getValue(), e.getKey(), m.getKey());
			}
		}

		JFreeChart chart = ChartFactory.createBarChart("Car Comparision", "Category", "Score", dataset);
		String pathImage = String.format("path_to_the_folder_where_you_want_to_store_the_image\\image\\%s", imageName);

		try {
			ChartUtilities.saveChartAsJPEG(new File(pathImage), chart, 500, 300);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}