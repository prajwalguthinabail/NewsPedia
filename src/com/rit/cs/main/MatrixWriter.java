package com.rit.cs.main;

import Jama.Matrix;
import Jama.SingularValueDecomposition;

/**
 * MatrixWriter.java. Title processing is commented as of now for test purposes
 * 
 * @author Prajwal Guthinabail
 */
public class MatrixWriter {

	public static void main(String[] args) {
		System.out.println("Writing into reduced matrix files...");
		processLSI(Processor.readMatrix("resources/contentMatrix.txt"));
		System.out.println("Done");
	}

	private static void processLSI(double[][] array) {
		SingularValueDecomposition svd = new SingularValueDecomposition(
				new Matrix(array));

		Matrix lMatrix = svd.getU();
		Matrix sMatrix = svd.getS();
		Matrix rMatrix = svd.getV();

		int k = lMatrix.getColumnDimension();

		Matrix lMatrixReduce = lMatrix.getMatrix(0,
				lMatrix.getRowDimension() - 1, 0, k - 1);

		Matrix sMatrixReduce = sMatrix.getMatrix(0, k - 1, 0, k - 1);

		Matrix rMatrixReduce = rMatrix
				.getMatrix(0, rMatrix.getRowDimension() - 1, 0, k - 1)
				.transpose();

		Matrix lsMatrix = lMatrixReduce.times(sMatrixReduce.inverse());

		Processor.writeMatrix(rMatrixReduce, "resources/rMatrixReduce.txt");
		Processor.writeMatrix(lsMatrix, "resources/lsMatrix.txt");
	}

}
