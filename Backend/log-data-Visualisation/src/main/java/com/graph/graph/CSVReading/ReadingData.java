package com.graph.graph.CSVReading;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;

public class ReadingData {

	private CSVReader reader;
	String[] nextline;

	// read the file from the given path
	public ReadingData(String path) {
		try {
			reader = new CSVReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// reading data for PieChart
	public Map<String, Integer> getPieData() throws IOException {
		Map<String, Integer> pieData = new LinkedHashMap<>();

		nextline = reader.readNext();
		while ((nextline = reader.readNext()) != null) {
			if (nextline != null) {
				pieData.put(nextline[0], Integer.parseInt(nextline[1]));
			}
		}
		return pieData;
	}

	// reading data for Histogram
	public Map<String, List<Integer>> getHistogramData() throws IOException {

		List<Integer> histData = new ArrayList<>();
		nextline = reader.readNext();
		String name = nextline[0];
		while ((nextline = reader.readNext()) != null) {
			if (nextline != null) {
				histData.add(Integer.parseInt(nextline[0]));
			}
		}
		Map<String, List<Integer>> m = new LinkedHashMap<>();
		m.put(name, histData);

		return m;

	}

// reading data for BubbleChart
	public Map<String, List<Integer>> getBubbleData() throws IOException {
		Map<String, List<Integer>> bubbleData = new LinkedHashMap<>();
		String key1, key2, key3;
		nextline = reader.readNext();
		key1 = nextline[0];
		key2 = nextline[1];
		key3 = nextline[2];
		List<Integer> value1 = new ArrayList<>();
		List<Integer> value2 = new ArrayList<>();
		List<Integer> value3 = new ArrayList<>();
		while ((nextline = reader.readNext()) != null) {
			if (nextline != null) {
				value1.add(Integer.parseInt(nextline[0]));
				value2.add(Integer.parseInt(nextline[1]));
				value3.add(Integer.parseInt(nextline[2]));
			}
		}
		bubbleData.put(key1, value1);
		bubbleData.put(key2, value2);
		bubbleData.put(key3, value3);

		return bubbleData;
	}

	// reading data for LineChart
	public Map<String, List<String>> getLineData() throws IOException {
		Map<String, List<String>> lineData = new LinkedHashMap<>();
		nextline = reader.readNext();
		for (String col : nextline) {
			if (col != "") {
				lineData.put(col, new ArrayList<String>());
			}
		}
		nextline = reader.readNext();
		while ((nextline = reader.readNext()) != null) {
			if (nextline != null) {
				int i = 0;
				for (Map.Entry<String, List<String>> e : lineData.entrySet()) {
					String val = nextline[i] + "," + nextline[i + 1];
					e.getValue().add(val);
					i += 2;
				}
			}
		}
		return lineData;
	}

	// reading data for AreaChart
	public Map<String, List<Double>> getAreaData() throws IOException {
		Map<String, List<Double>> areaData = new LinkedHashMap<>();
		nextline = reader.readNext();
		for (String col : nextline) {
			if (col != "") {
				areaData.put(col, new ArrayList<Double>());
			}
		}
		while ((nextline = reader.readNext()) != null) {
			if (nextline != null) {
				int i = 0;
				for (Map.Entry<String, List<Double>> e : areaData.entrySet()) {
					String val = nextline[i];
					e.getValue().add(Double.parseDouble(val));
					i++;
				}
			}
		}
		return areaData;
	}

	// reading data for ScatterChart
	public Map<String, List<String>> getScatterData() throws IOException {
		Map<String, List<String>> scatterData = new LinkedHashMap<>();
		nextline = reader.readNext();
		for (String col : nextline) {
			if (col != "") {
				scatterData.put(col, new ArrayList<String>());
			}
		}
		nextline = reader.readNext();
		System.out.println(scatterData.keySet());
		while ((nextline = reader.readNext()) != null) {
			if (nextline != null) {
				int i = 0;
				for (Map.Entry<String, List<String>> e : scatterData.entrySet()) {
					String val = nextline[i] + "," + nextline[i + 1];
					e.getValue().add(val);
					i += 2;
				}
			}
		}
		return scatterData;
	}

	// reading data for BarData
	public Map<String, Map<String, Double>> getBarData() throws IOException {
		Map<String, Map<String, Double>> barData = new LinkedHashMap<>();
		nextline = reader.readNext();
		int i = 0;
		for (String col : nextline) {
			if (i == 0) {
				i++;
				continue;
			}
			if (col != "") {
				barData.put(col, new LinkedHashMap<String, Double>());
			}
		}
		while ((nextline = reader.readNext()) != null) {
			if (nextline != null) {
				int index = 1;
				String cat = nextline[0];
				for (Map.Entry<String, Map<String, Double>> e : barData.entrySet()) {
					e.getValue().put(cat, Double.parseDouble(nextline[index]));
					index++;
				}
			}
		}
		return barData;
	}

}