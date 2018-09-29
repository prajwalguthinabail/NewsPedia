package com.rit.cs.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Jama.Matrix;

/**
 * ProcessRetrieval.java
 * 
 * @author Prajwal Guthinabail Nov 20, 2017
 */
public class ProcessRetrieval {

	private static final double SCORE_THRESHOLD = 0.1;
	private static final int RESULT_THRESHOLD = 10;
	// Input read and load
	public static Map<Integer, Record> records;
	private static Matrix lsMatrix;
	private static Matrix rMatrixReduce;
	private static List<String> terms;
	private static double[][] tdf;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Generates popular term pairs per document. For test purposes only
//start();
//
//int a1,a2;
//
//double[] d;
//double s;
//for(int i=0;i< tdf[0].length;i++) {
//	int c1 = 0,c2 = 0;
//	d= tdf[0];
//	double b1 = 0,b2=0;
//	for(int j=0; j<tdf.length;j++) {
//		s= tdf[j][i];
//		if(s> b1) {
//			b1=s;
//			c1=j;
//		}else if(s>b2) {
//			b2=s;
//			c2=j;
//		}
//	}
//	System.out.println(i +" " +terms.get(c1)+" "+b1+" "+b2+" "+terms.get(c2));
//}


	}

	public static void start() {
		System.out.println("Records and SVD matrix are being read...");
		records = Processor.readFiles("resources/data");
		lsMatrix = new Matrix(Processor.readMatrix("resources/lsMatrix.txt"));
		rMatrixReduce = new Matrix(
				Processor.readMatrix("resources/rMatrixReduce.txt"));
		terms = Processor.readTerm("resources/contentTerms.txt");
		tdf = Processor.readMatrix("resources/contentMatrix.txt");
		System.out.println("Done");
	}

	public static List<Record> search(List<String> qTerms) {
		Matrix qMatC = getQueryMatrix(qTerms);

		List<Result> scores = findCosineSim(qMatC);
		List<Record> searchItems = new ArrayList<>();
		System.out.println("Score threshold = 0.1");
		System.out.println("id\tscore");
		int count = 0;
		Iterator<Result> iter = scores.iterator();
		Result result;
		Record rec;
		while (iter.hasNext() && count < RESULT_THRESHOLD) {
			result = iter.next();
			if (result.similarityScore > SCORE_THRESHOLD) {
				System.out
						.println(result.docId + " \t" + result.similarityScore);
				rec = records.get(result.docId);
				rec.setScore((int) (result.similarityScore * 100));
				// processContent(rec);
				if(searchItems.size()<10) {
				searchItems.add(rec);// To display		
				}else {
					break;
				}
				count++; // complete data
			}
		}
		return searchItems;
	}

	public static List<Record> keysearch(List<String> qTerms) {
		List<double[]> selectDocs = new ArrayList<>();
		List<Record> result= new ArrayList<>();
		for (String queryTerm : qTerms) {
			if (terms.contains(queryTerm)) {
				selectDocs.add(tdf[terms.indexOf(queryTerm)]);
			}
		}
		int docCount = tdf[0].length;
		Map<Integer, Integer> docIds= new HashMap<>();
		int tot=0;
		if (!selectDocs.isEmpty() && selectDocs.size() <= qTerms.size()) {
			for (int i = 0; i < docCount; i++) {
				tot=0;
				for(int j=0;j<selectDocs.size();j++) {
					if(selectDocs.get(j)[i]==0) {
						break;
					}
					else {
					tot+=	selectDocs.get(j)[i];
					}
					if(j==selectDocs.size()-1) {
						docIds.put(i, tot);
					}
				}
			}
			int loop= docIds.size()>9? 10: docIds.size();
		
			Integer rec = null;
			double freqBest;
			while(loop>0) {
				freqBest=-1;
				for(Integer docID:docIds.keySet()) {
					if (docIds.get(docID)> freqBest) {
						freqBest= docIds.get(docID);
						rec= docID;
					}
				}
				docIds.remove(rec);
				result.add(records.get(rec));
				loop--;
			}
		}
		return result;
	}

	private static void processContent(Record rec) {
		String content = rec.getContent();
		String[] words = content.split(" ");
		int wCount = words.length;
		StringBuffer sb = new StringBuffer();
		int counter = 150;
		if (wCount > 150) {
			while (counter > 100) {
				sb.append(Arrays.copyOfRange(words, 0, 150));
			}
		}
	}

	private static List<Result> findCosineSim(Matrix qMat) {
		double dot;
		double magn;
		double res;
		Matrix docMat;
		List<Result> scores = new ArrayList<>(
				rMatrixReduce.getColumnDimension());
		for (int i = 0; i < rMatrixReduce.getColumnDimension(); i++) {
			docMat = rMatrixReduce.getMatrix(0,
					rMatrixReduce.getRowDimension() - 1, i, i);
			dot = qMat.times(docMat).norm1();
			magn = qMat.normF() * docMat.normF();
			res = dot / magn;
			scores.add(new Result(res, i));
		}
		Collections.sort(scores);
		Collections.reverse(scores);
		return scores;
	}

	private static Matrix getQueryMatrix(List<String> keys) {
		Matrix qMat = new Matrix(terms.size(), 1);
		int index;
		for (String key : keys) {
			index = terms.indexOf(key);
			if (index != -1) {
				qMat.set(index, 0, qMat.get(index, 0) + 1);

			}
		}
		return qMat.transpose().times(lsMatrix);
	}

	private static class Result implements Comparable<Result> {
		double similarityScore;
		int docId;

		Result(double similarityScore, int docId) {
			this.similarityScore = similarityScore;
			this.docId = docId;
		}

		@Override
		public int compareTo(Result other) {
			if (this.similarityScore == other.similarityScore) {
				return 0;
			}
			if (this.similarityScore > other.similarityScore) {
				return 1;
			} else {
				return -1;
			}
		}

	}
}
