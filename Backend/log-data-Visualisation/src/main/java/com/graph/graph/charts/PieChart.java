package com.graph.graph.charts;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.annotation.Value;

import com.graph.graph.CSVReading.ReadingData;
import com.graph.graph.chart.services.GraphServices;
import com.opencsv.exceptions.CsvValidationException;

public class PieChart implements GraphServices {

	@Value("${project.image}")
	String imagePath;

	@SuppressWarnings("unchecked")
	@Override
	public void generateGraph(String path, String imageName) throws IOException, CsvValidationException {
		ReadingData data = new ReadingData(path);
		Map<String, Integer> pieData = data.getPieData();
		@SuppressWarnings("rawtypes")
		DefaultPieDataset pieDataset = new DefaultPieDataset();
		for (Map.Entry<String, Integer> e : pieData.entrySet()) {
			pieDataset.setValue(e.getKey(), e.getValue());
		}

		JFreeChart chart = ChartFactory.createPieChart("Countries Population Details", pieDataset, true, true, false);
		String pathImage = String.format("path_to_the_folder_where_you_want_to_store_the_image\\%s", imageName);

		try {
			ChartUtilities.saveChartAsJPEG(new File(pathImage), chart, 500, 300);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}