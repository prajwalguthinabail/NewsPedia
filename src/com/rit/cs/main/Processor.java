package com.rit.cs.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import Jama.Matrix;

/**
 * Processor.java
 * 
 * @author Prajwal Guthinabail
 *  Oct 20, 2017
 */
public class Processor {

	public static Map<Integer, Record> readFiles(String folderName) {
		File[] listOfFiles = new File(folderName).listFiles();
		Map<Integer, Record> records = new HashMap<>();
		int idStart = 0;
		for (File file : listOfFiles) {
			records.putAll(readFile(file, idStart));
		}
		return records;
	}

	private static Map<Integer, Record> readFile(File file, int id) {
		Map<Integer, Record> recs = new HashMap<>();
		try (FileReader reader = new FileReader(file)) {
			Iterator<CSVRecord> records = CSVFormat.DEFAULT
					.withIgnoreSurroundingSpaces().parse(reader).iterator();
			records.next();
			while (records.hasNext()) {
				CSVRecord record = records.next();
				try {
					if (record.size() == 5) {

						recs.put(id,
								new Record(id, record.get(0), record.get(1),
										record.get(2),
										new SimpleDateFormat("MM/dd/yyyy")
												.parse(record.get(3)),
										record.get(4)));
						id++;
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return recs;
	}

	private static Set<String> createStopSet(String fileName) {
		Set<String> stopSet = new HashSet<>();

		try (BufferedReader reader = new BufferedReader(
				new FileReader(new File(fileName)))) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				stopSet.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stopSet;
	}

	public static Map<Integer, Map<String, Integer>> tokenizeRecord(
			Map<Integer, String> lines) {
		Map<Integer, Map<String, Integer>> tokens = new HashMap<>();
		for (int id : lines.keySet()) {
			tokens.put(id, getTokens(lines.get(id)));
		}
		return tokens;
	}

	public static Map<String, Integer> getTokens(String line) {
		String[] tokens = line.split(" ");
		Set<String> stopWords = createStopSet("resources/stopwords.txt");
		Map<String, Integer> tokenMap = new HashMap<>();
		String token;
		Integer freq;
		Stemmer stemmer = new Stemmer();
		for (String tok : tokens) {
			token = tok.toLowerCase();
			if (token != null && !stopWords.contains(token)
					&& token.matches("[a-zA-Z0-9]{2,}")) {
				stemmer.add(token.toCharArray(), token.length());
				stemmer.stem();
				token = stemmer.toString();
				freq = tokenMap.get(token);
				if (freq == null) {
					freq = 0;
				}
				tokenMap.put(token, ++freq);
			}
		}
		return tokenMap;
	}

	public static double[][] readMatrix(String filename) {
		try (BufferedReader reader = new BufferedReader(
				new FileReader(new File(filename)))) {
			// Read row count
			String line = reader.readLine();
			String[] vals;
			int count = 0;
			double[][] valueMatrix = new double[Integer.parseInt(line)][];
			while ((line = reader.readLine()) != null) {
				vals = line.split(",");
				valueMatrix[count++] = Arrays.stream(vals)
						.mapToDouble(Double::parseDouble).toArray();
			}
			return valueMatrix;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<String> readTerm(String filename) {
		try (BufferedReader reader = new BufferedReader(
				new FileReader(new File(filename)))) {
			String line = reader.readLine();
			List<String> result = new ArrayList<>(Integer.parseInt(line));
			while ((line = reader.readLine()) != null) {
				result.add(line);
			}
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void writeTerms(List<String> terms, String filename) {
		try (FileWriter writer = new FileWriter(new File(filename))) {
			writer.write(terms.size() + "\n");
			for (String term : terms) {
				writer.write(term + "\n");
				writer.flush();
			}
			File f = new File(filename);
			System.out.println(f.getPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeMatrix(Matrix mat, String filename) {
		double[][] ar = mat.getArray();
		try (FileWriter writer = new FileWriter(new File(filename))) {
			writer.write(mat.getRowDimension() + "\n");
			for (int i = 0; i < ar.length; i++) {
				for (int j = 0; j < ar[i].length; j++) {
					writer.write(ar[i][j] + ",");
				}
				writer.write("\n");
				writer.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String arrayToString(double[] vals) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(Arrays.toString(vals).replace("[", "").replace("]", "")
				.replace(",", "").replace(" ", ",")).append("\n");
		return buffer.toString();
	}

}
