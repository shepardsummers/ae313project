package main;

public class MatrixMath {
	/* 
	 * CLASS PURPOSE: To handle matrix calculations
	 * Methods: inverse, 
	 * 
	 */
	
	public MatrixMath() {
		
	}

	public static double[][] inverse(double[][] mat){
		// Function for inverting a 3x3 matrix
		
		
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
		
		// First row
		inv[0][0] = p * (e*i - f*h);
		inv[0][1] = p * (c*h - b*i);
		inv[0][2] = p * (b*f - c*e);
		
		// Second row
		inv[1][0] = p * (f*g - d*i);
		inv[1][1] = p * (a*i - c*g);
		inv[1][2] = p * (c*d - a*f);
		
		// Third row
		inv[2][0] = p * (d*h - e*g);
		inv[2][1] = p * (b*g - a*h);
		inv[2][2] = p * (a*e - b*d);
		
		return inv;
	}
}
