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
	
	public static void printMat(double[][] mat) {
		// Method to print out a 3x3 matrix row by row
		
		for (int row = 0; row <= 2; row++) {
			System.out.println("Row " + (row + 1));
			for (int col = 0; col <= 2; col++) {
				System.out.println(mat[row][col]);
			}
		}
		
	}
}
