package main;

public class MatrixMath {
	/* 
	 * CLASS PURPOSE: To handle 3x3 matrix calculations
	 * METHODS: inverse, matMult, printMat
	 * 
	 */
	
	public MatrixMath() {
		
	}

	public static double[][] inverse(double[][] mat){
		// Method for inverting a 3x3 matrix
		
		// Empty 3x3 matrix
		double[][] inv = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
		
		// First row
		double a = mat[0][0];
		double b = mat[0][1];
		double c = mat[0][2];
		
		// Second row
		double d = mat[1][0];
		double e = mat[1][1];
		double f = mat[1][2];
		
		// Third row
		double g = mat[2][0];
		double h = mat[2][1];
		double i = mat[2][2];
		
		// Factor each value multiplied by
		double p = 1 / (a*(e*i - f*h) - b*(d*i - f*g) + c*(d*h - e*g));
		
		// First row calculations
		inv[0][0] = p * (e*i - f*h);
		inv[0][1] = p * (c*h - b*i);
		inv[0][2] = p * (b*f - c*e);
		
		// Second row calculations
		inv[1][0] = p * (f*g - d*i);
		inv[1][1] = p * (a*i - c*g);
		inv[1][2] = p * (c*d - a*f);
		
		// Third row calculations
		inv[2][0] = p * (d*h - e*g);
		inv[2][1] = p * (b*g - a*h);
		inv[2][2] = p * (a*e - b*d);
		
		return inv;
	}
	
	public static double[][] matMult(double[][] mat1, double[][] mat2){
		// Method for multiplying two 3x3 matrices
		
		// Empty 3x3 matrix
		double[][] fin = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};		
		
		// First column calculations
		fin[0][0] = (mat1[0][0] * mat2[0][0]) + (mat1[0][1] * mat2[1][0]) + (mat1[0][2] * mat2[2][0]);
		fin[1][0] = (mat1[1][0] * mat2[0][0]) + (mat1[1][1] * mat2[1][0]) + (mat1[1][2] * mat2[2][0]);
		fin[2][0] = (mat1[2][0] * mat2[0][0]) + (mat1[2][1] * mat2[1][0]) + (mat1[2][2] * mat2[2][0]);
		
		// Second column calculations
		fin[0][1] = (mat1[0][0] * mat2[0][1]) + (mat1[0][1] * mat2[1][1]) + (mat1[0][2] * mat2[2][1]);
		fin[1][1] = (mat1[1][0] * mat2[0][1]) + (mat1[1][1] * mat2[1][1]) + (mat1[1][2] * mat2[2][1]);
		fin[2][1] = (mat1[2][0] * mat2[0][1]) + (mat1[2][1] * mat2[1][1]) + (mat1[2][2] * mat2[2][1]);
		
		// Second column calculations
		fin[0][2] = (mat1[0][0] * mat2[0][2]) + (mat1[0][1] * mat2[1][2]) + (mat1[0][2] * mat2[2][2]);
		fin[1][2] = (mat1[1][0] * mat2[0][2]) + (mat1[1][1] * mat2[1][2]) + (mat1[1][2] * mat2[2][2]);
		fin[2][2] = (mat1[2][0] * mat2[0][2]) + (mat1[2][1] * mat2[1][2]) + (mat1[2][2] * mat2[2][2]);		
		
		return fin;
	}
	
	public static double[] matMult1x3(double[][] mat1, double[] vect){
		// Method for multiplying 3x3 and 1x3 matrices
		
		// Empty 3x3 matrix
		double[] fin = {0, 0, 0};
		
		// First column calculations
		fin[0] = (mat1[0][0] * vect[0]) + (mat1[0][1] * vect[1]) + (mat1[0][2] * vect[2]);
		fin[1] = (mat1[1][0] * vect[0]) + (mat1[1][1] * vect[1]) + (mat1[1][2] * vect[2]);
		fin[2] = (mat1[2][0] * vect[0]) + (mat1[2][1] * vect[1]) + (mat1[2][2] * vect[2]);
		
		return fin;
	}
	
	public static void printMat(double[][] mat) {
		// Method to print out a 3x3 matrix row by row
		
		for (int col = 0; col <= 2; col++) {
			System.out.println("Col " + (col + 1));
			for (int row = 0; row <= 2; row++) {
				System.out.println(mat[row][col]);
			}
		}
		
	}
	
	public static double dot(double[] v1, double[] v2) {
		// Method to do the dot product of two vectors v1 and v2
		double fin = 0;
		
		// For loop to do dot product
		if (v1.length == v2.length) {
			for (int i = 0; i < v1.length; i++) {
				fin = fin + (v1[i] * v2[i]);
			}
		} else {
			System.out.println("Dot Product Error: length v1 != length v2");
		}
		
		
		return fin;
	}
	
	public static double mag(double[] v) {
		
		double mag = Math.sqrt(Math.pow(v[0], 2) + Math.pow(v[1], 2) + Math.pow(v[2], 2));
		
		return mag;
		
	}
	
	public static double[][] transpose(double[][] mat) {
		
		double[][] newMat = mat;
		
		for (int r = 0; r <= 2; r++) {
			for (int c = 0; c <= 2; c++) {
				newMat[r][c] = mat[c][r]; 
			}
		}
		
		
		return newMat;
	}
	
	public static double[][] matAdd(double[][] mat1, double[][] mat2) {
		
		// Empty 3x3 matrix
		double[][] out = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
		
		for (int r = 0; r <= 2; r++) {
			for (int c = 0; c <= 2; c++) {
				out[r][c] = mat1[r][c] + mat2[r][c]; 
			}
		}
		
		
		return out;
	}
}
