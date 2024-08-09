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

	/**
	 * Constructor to initialize the CSVReader with the given file path.
	 *
	 * @param path The path of the CSV file to be read.
	 */
	public ReadingData(String path) {
		try {
			reader = new CSVReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace(); // Log error if the file is not found
		}
	}

	/**
	 * Reads data for PieChart from the CSV file.
	 *
	 * @return A map where keys are labels and values are integer counts.
	 * @throws IOException If an I/O error occurs while reading the file.
	 */
	public Map<String, Integer> getPieData() throws IOException {
		Map<String, Integer> pieData = new LinkedHashMap<>();
		nextline = reader.readNext(); // Read header line
		while ((nextline = reader.readNext()) != null) {
			if (nextline != null) {
				pieData.put(nextline[0], Integer.parseInt(nextline[1]));
			}
		}
		return pieData;
	}

	/**
	 * Reads data for Histogram from the CSV file.
	 *
	 * @return A map where keys are categories and values are lists of integers for
	 *         each category.
	 * @throws IOException If an I/O error occurs while reading the file.
	 */
	// ReadingData.java (Assumed class)

	public Map<String, List<Integer>> getHistogramData() throws IOException {
	    List<Integer> histData = new ArrayList<>();
	    String[] nextline = reader.readNext();
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
	/**
	 * Reads data for BubbleChart from the CSV file.
	 *
	 * @return A map where keys are series names and values are lists of integers
	 *         for each series.
	 * @throws IOException If an I/O error occurs while reading the file.
	 */
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

	/**
	 * Reads data for LineChart from the CSV file.
	 *
	 * @return A map where keys are series names and values are lists of data
	 *         points.
	 * @throws IOException If an I/O error occurs while reading the file.
	 */
	public Map<String, List<String>> getLineData() throws IOException {
		Map<String, List<String>> lineData = new LinkedHashMap<>();
		String[] nextline;

		// Read header line to get categories
		nextline = reader.readNext();
		if (nextline == null) {
			return lineData;
		}

		// Initialize lists for each category
		for (String col : nextline) {
			if (!col.isEmpty()) {
				lineData.put(col, new ArrayList<>());
			}
		}

		// Read the rest of the data
		while ((nextline = reader.readNext()) != null) {
			int i = 0;
			for (Map.Entry<String, List<String>> entry : lineData.entrySet()) {
				if (i + 1 < nextline.length) { // Ensure we don't exceed the array bounds
					String val = nextline[i] + "," + nextline[i + 1];
					entry.getValue().add(val);
					i += 2;
				}
			}
		}
		return lineData;
	}

	/**
	 * Reads data for AreaChart from the CSV file.
	 *
	 * @return A map where keys are series names and values are lists of double
	 *         values.
	 * @throws IOException If an I/O error occurs while reading the file.
	 */
	public Map<String, List<Double>> getAreaData() throws IOException {
		Map<String, List<Double>> areaData = new LinkedHashMap<>();
		nextline = reader.readNext(); // Read header line
		for (String col : nextline) {
			if (!col.isEmpty()) {
				areaData.put(col, new ArrayList<>());
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

	/**
	 * Reads data for ScatterChart from the CSV file.
	 *
	 * @return A map where keys are series names and values are lists of data points
	 *         (formatted as "x,y").
	 * @throws IOException If an I/O error occurs while reading the file.
	 */
	public Map<String, List<String>> getScatterData() throws IOException {
		Map<String, List<String>> scatterData = new LinkedHashMap<>();

		// Read the header line to get series names
		nextline = reader.readNext();
		if (nextline == null) {
			return scatterData;
		}

		// Initialize lists for each series
		for (String col : nextline) {
			if (!col.isEmpty()) {
				scatterData.put(col, new ArrayList<>());
			}
		}

		// Read the rest of the data
		while ((nextline = reader.readNext()) != null) {
			if (nextline != null) {
				int i = 0;
				for (Map.Entry<String, List<String>> entry : scatterData.entrySet()) {
					// Ensure there is enough data for x and y values
					if (i + 1 < nextline.length) {
						String value = nextline[i] + "," + nextline[i + 1];
						entry.getValue().add(value);
						i += 2;
					}
				}
			}
		}

		return scatterData;
	}

	/**
	 * Reads data for BarChart from the CSV file.
	 *
	 * @return A map where keys are categories and values are maps of sub-categories
	 *         and their corresponding values.
	 * @throws IOException If an I/O error occurs while reading the file.
	 */
	public Map<String, Map<String, Double>> getBarData() throws IOException {
		Map<String, Map<String, Double>> barData = new LinkedHashMap<>();
		nextline = reader.readNext(); // Read header line
		int i = 0;
		for (String col : nextline) {
			if (i == 0) {
				i++;
				continue;
			}
			if (!col.isEmpty()) {
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
