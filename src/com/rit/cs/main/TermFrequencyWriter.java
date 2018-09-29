package com.rit.cs.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * TermFrequencyWriter.java
 * 
 * @author Prajwal Guthinabail
 *  Oct 20, 2017
 */
public class TermFrequencyWriter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(
				"Writing terms and document frequency matrix files...");
		String inputDataFile = "resources/data";
		String titleMatrixFile = "resources/titleMatrix.txt";
		String titleTermsFile = "resources/titleTerms.txt";
		String contentTermsFile = "resources/contentTerms.txt";
		String contentMatrixFile = "resources/contentMatrix.txt";

		// Input read and load
		Collection<Record> records = Processor.readFiles(inputDataFile)
				.values();

		// Tokenization
		Map<Integer, Map<String, Integer>> titleMap = Processor
				.tokenizeRecord(records.stream().collect(
						Collectors.toMap(Record::getId, Record::getTitle)));

		List<String> titleTerms = createTermPool(titleMap.values());
		Processor.writeTerms(titleTerms, titleTermsFile);

		// Create term-document frequency matrix
		writeDocMatrix(titleMap, titleTerms, titleMatrixFile);
		titleMap = null;
		titleTerms = null;

		// Tokenization
		Map<Integer, Map<String, Integer>> contentMap = Processor
				.tokenizeRecord(records.stream().collect(
						Collectors.toMap(Record::getId, Record::getContent)));

		List<String> contentTerms = createTermPool(contentMap.values());
		Processor.writeTerms(contentTerms, contentTermsFile);

		// Create term-document frequency matrix
		writeDocMatrix(contentMap, contentTerms, contentMatrixFile);
		contentMap = null;
		records = null;
		System.out.println("Done");
	}

	private static List<String> createTermPool(
			Collection<Map<String, Integer>> map) {
		Set<String> result = new HashSet<>();
		for (Map<String, Integer> terms : map) {
			result.addAll(terms.keySet());
		}
		return result.stream().collect(Collectors.toList());
	}

	private static void writeDocMatrix(Map<Integer, Map<String, Integer>> map,
			List<String> titleTerms, String filename) {
		int docCount = map.size();
		double[] recIds;
		Integer docTerm;
		try (FileWriter writer = new FileWriter(new File(filename))) {
			writer.write(titleTerms.size() + "\n");
			for (String term : titleTerms) {
				recIds = new double[docCount];
				for (int id : map.keySet()) {
					docTerm = map.get(id).get(term);
					if (docTerm != null) {
						recIds[id] = docTerm;
					}
				}
				writer.write(Processor.arrayToString(recIds));
				writer.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
