package main;

public class Gauss {
	/* 
	 * CLASS PURPOSE: To handle Gauss method calculations
	 * METHODS: 
	 * 
	 */
	
	public Gauss() {
		// TODO Auto-generated constructor stub
	}
	static double[] findC(double r2, double tau, double tau3, double tau1, double m) {
		// Method to calculate c1 and c3 values
		
		// Calculate c1 and c3 based off of time difference
		double c1 = (tau3/tau) * (1 + (m/(6*Math.pow(r2, 3))) * (Math.pow(tau, 2) - Math.pow(tau3, 2)));
		double c3 = (-tau1/tau) * (1 + (m/(6*Math.pow(r2, 3))) * (Math.pow(tau, 2) - Math.pow(tau1, 2)));
		
		// Return array
		double[] out = {c1,c3};
		
		return out;
	}
	
	public static double[][] findPHat(double ra1, double ra2, double ra3, double dec1, double dec2, double dec3) {
		// Method to find the p hat matrix
		
		// Putting right ascension and declination into arrays after conversion to radians
		double[] ra = {Math.toRadians(ra1), Math.toRadians(ra2), Math.toRadians(ra3)};
		double[] dec = {Math.toRadians(dec1), Math.toRadians(dec2), Math.toRadians(dec3)};
		
		// Empty 3x3 matrix
		double[][] p = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
		
		// For loop to calculate p matrix
		for(int i = 0; i < 3; i++) {
			
			p[0][i] = Math.cos(dec[i]) * Math.cos(ra[i]);
			p[1][i] = Math.cos(dec[i]) * Math.sin(ra[i]);
			p[2][i] = Math.sin(dec[i]);
			
		}
		
		// Inverting the p matrix
		//double[][] out = MatrixMath.inverse(p);
		
		return p;
	}
	
	public static double[][] findR(double theta1, double theta2, double theta3, double tLat, double tRad) {
		// Method to find the location of the tracking station in ECI frame
		
		// List of theta values
		double[] theta = {Math.toRadians(theta1), Math.toRadians(theta2), Math.toRadians(theta3)};
		
		// height of tracking station
		double height = tRad * Math.sin(Math.toRadians(tLat));
		
		// Equatorial radius of the tracking station
		double eqR = tRad * Math.cos(Math.toRadians(tLat));
		
		// R matrix
		double[][] R = {{0, 0, 0}, {0, 0, 0}, {height, height, height}};
		
		// For loop to calculate i and j components of R vectors for each observation
		for (int i = 0; i <= 2; i++) {
			
			R[0][i] = eqR * Math.cos(theta[i]);
			R[1][i] = eqR * Math.sin(theta[i]);
			
		}
		
		return R;
	}
	
	public static double[] findAB(double[][] p, double[][] R, double tau, double tau1, double tau3) {
		// Method to acquire A and B values for the polynomial equation
		
		// Find M matrix
		double[][] M = MatrixMath.matMult(MatrixMath.inverse(p), R);
		
		// Find A and B based off of the formulas and the M matrix
		double A = M[1][0]*(tau3/tau) - M[1][1] - M[1][2]*(tau1/tau);
		double B = M[1][0]*(tau3/(6*tau))*(Math.pow(tau, 2) - Math.pow(tau3, 2)) - M[1][2]*(tau1/(6*tau))*(Math.pow(tau, 2) - Math.pow(tau1, 2));
		
		// Return A and B
		double[] out = {A, B};
		
		return out;
	}
	
	public static double interate(double[] AB, double[][] R, double[][] p, double m) {
		
		double guess = 9000;
		
		double[] p2 = {p[1][0], p[1][1], p[1][2]};
		double[] R2 = {R[1][0], R[1][1], R[1][2]};
		
		double a = -(Math.pow(AB[0], 2) + 2*AB[0]*MatrixMath.dot(R2, p2) + Math.pow(MatrixMath.mag(R2), 2));
		double b = -2*m*AB[1]*(AB[0] + MatrixMath.dot(R2, p2));
		double c = -Math.pow(m, 2)*Math.pow(AB[1], 2);
		
		System.out.println("A: " + a + " B: " + b + " C: " + c);
		
		int count = 0;
		double xn;
		double d = 1;
		while (Math.abs(d) >= 0.000001) {
			count ++;
			
			xn = guess;
			guess = xn - ((Math.pow(xn, 8) + a*Math.pow(xn, 6) + b*Math.pow(xn, 3) + c) / (8*Math.pow(xn, 7) + 6*a*Math.pow(xn, 5) + 3*b*Math.pow(xn, 2)));
			d = guess - xn;
			
			System.out.println("Run " + count + " -+-+-+-+-+-+-+-+-+-+-+-+-");
			System.out.println("  Xn: " + xn);
			System.out.println("  Xn+1: " + guess);
			System.out.println("  Disc: " + d);
			
		}
		
		return guess;
	}
	
	public static double[][] findECIr(double[][] pHat, double[][] R, double[] C) {
		
		// Find M matrix
		double[][] M = MatrixMath.matMult(MatrixMath.inverse(pHat), R);
		
		// Vector that will be multiplied
		double[] vect = {-C[0], 1, -C[1]};
		
		double[] M2 = MatrixMath.matMult1x3(M, vect);
		
		double[] P = {(M2[0]/C[0]), (M2[1]/-1), (M2[2]/C[1])};
		
		System.out.println("P");
		System.out.println(P[0]);
		System.out.println(P[1]);
		System.out.println(P[2]);
		
		double[][] r = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
		
		for (int col = 0; col <= 2; col++) {
			for (int row = 0; row <= 2; row++) {
				r[row][col] = R[row][col] + P[col]*pHat[row][col];
			}
		}
		
		
		return r;
	}
}
