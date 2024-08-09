package com.graph.graph.charts;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.DefaultXYZDataset;
import org.springframework.beans.factory.annotation.Value;

import com.graph.graph.CSVReading.ReadingData;
import com.graph.graph.chart.service.GraphServices;

public class BubbleChart implements GraphServices {

	@Value("${project.image}")
	String imagePath;

	@Override
	public void generateGraph(String path, String imageName) throws IOException {
		ReadingData data = new ReadingData(path);
		Map<String, List<Integer>> bubbleData = data.getBubbleData();
		String labels[] = new String[3];
		int i = 0;
		int n = 0;
		for (Map.Entry<String, List<Integer>> iter : bubbleData.entrySet()) {
			n = iter.getValue().size();
			break;
		}
		List<double[]> arr = new ArrayList<>();
//  double[][] values = new double[3][n];
		for (Map.Entry<String, List<Integer>> e : bubbleData.entrySet()) {
			labels[i] = e.getKey();
			int col = 0;
			double a[] = new double[n];
			for (int num : e.getValue()) {
				a[col] = (double) num;
				col++;
				System.out.print(num + " ");
			}
			arr.add(a);
			System.out.println();
		}
		DefaultXYZDataset dataset = new DefaultXYZDataset();
		double values[][] = { arr.get(0), arr.get(1), arr.get(2) };
		dataset.addSeries("Series 1", values);

		JFreeChart chart = ChartFactory.createBubbleChart("WEIGHT vs AGE vs WORK", labels[0], labels[1], dataset);
		String pathImage = String.format("D:\\PROJECT\\ALL_IMP_To_Project\\image_graph_generated\\%s", imageName);

		try {
			ChartUtilities.saveChartAsJPEG(new File(pathImage), chart, 500, 300);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}