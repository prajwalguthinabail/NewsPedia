package com.rit.cs.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * ProcessQuery.java
 * 
 * @author Prajwal Guthinabail
 *  Nov 5, 2017
 */
public class ProcessQuery {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("Getting ready...\n\n");
		ProcessRetrieval.start();
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter your news search query");
		String query = reader.nextLine();
		new ProcessQuery().process(query);
		reader.close();
	}

	public List<Record> process(String query) {

		List<Record> results = new ArrayList<>();
		if (query.equals("")) {
			results.addAll(getLatest(10));
			results.addAll(getLatest(10));
			return results;
		}
		Map<String, Integer> qTerms = Processor.getTokens(query);
		System.out.print("query tokens: ");
		for (String k : qTerms.keySet()) {
			System.out.print(k + ", ");
		}
		System.out.println("");

		results = ProcessRetrieval
				.search(qTerms.keySet().stream().collect(Collectors.toList()));
		List<Record> keywordResults = ProcessRetrieval
				.keysearch(qTerms.keySet().stream().collect(Collectors.toList()));
		if (results.size() < 10) {
			results.addAll( getLatest(10-results.size()));
		}
		if (keywordResults.size() < 10) {
			keywordResults.addAll( getLatest(10-keywordResults.size()));
		}

		results.addAll(keywordResults);
		return results;
	}

	private List<Record> getLatest(int items) {
		// Show latest n docs
		 items = ProcessRetrieval.records.size() > 10 ? items
				: ProcessRetrieval.records.size();
		int count = 0;
		List<Record> results = new ArrayList<>(10);
		while (count < items) {
			results.add(ProcessRetrieval.records.get(count));
			count++;
		}
		return results;
	}

}
