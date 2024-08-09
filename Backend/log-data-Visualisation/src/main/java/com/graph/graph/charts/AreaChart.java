package com.graph.graph.charts;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.springframework.beans.factory.annotation.Value;

import com.graph.graph.CSVReading.ReadingData;
import com.graph.graph.chart.services.GraphServices;
import com.opencsv.exceptions.CsvValidationException;

public class AreaChart implements GraphServices {

	@Value("${project.image}")
	String imagePath;

	@Override
	public void generateGraph(String path, String imageName) throws IOException, CsvValidationException {
		ReadingData data = new ReadingData(path);
		Map<String, List<Double>> areaData = null;
		try {
			areaData = data.getAreaData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int n = 0;
		for (Map.Entry<String, List<Double>> e : areaData.entrySet()) {
			n = e.getValue().size();
			break;
		}
		double values[][] = new double[areaData.size()][n];
		ArrayList<double[]> arr = new ArrayList<>();
		for (Map.Entry<String, List<Double>> e : areaData.entrySet()) {
			double[] d = new double[n];
			int col = 0;
			for (double num : e.getValue()) {
				d[col] = num;
				col++;
			}
			arr.add(d);
		}
		int i = 0;
		for (double[] d : arr) {
			values[i] = d;
		}

		CategoryDataset dataset = DatasetUtilities.createCategoryDataset("Series ", "Type ", values);

		JFreeChart chart = ChartFactory.createAreaChart("Area Chart", "Category", "Value", dataset,
				PlotOrientation.VERTICAL, true, true, false);

		String pathImage = String.format("path_to_the_folder_where_you_want_to_store_the_image\\image\\%s", imageName);

		try {
			ChartUtilities.saveChartAsJPEG(new File(pathImage), chart, 500, 300);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}