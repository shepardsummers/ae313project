package main;

public class Main {
	
	/*
	 * AE 313 HON PROJECT
	 * AUTHOR: Shepard Summers
	 * PURPOSE: To use Gauss method to obtain radius and velocity
	 * 
	 */
	
	// INPUT VARIABLES
	
	// assorted constants
	public final static int m = 398600; // km^3/s^2
	public final static int eRad = 6378; // km
	public final static int alt = 500; // km
	public final static int tLat = 60; // deg
	public final static int tAlt = eRad + alt; // km
	
	// time (s)
	public final static int t1 = 0;
	public final static int t2 = 5 * 60;
	public final static int t3 = 10 * 60;
	
	public final static double tau = t3 - t1;
	public final static double tau1 = t1 - t2;
	public final static double tau3 = t3 - t2;
	
	// mean anomaly (degrees)
	public final static double theta1 = 150.000;
	public final static double theta2 = 151.253;
	public final static double theta3 = 152.507;
	
	// topocentric right ascension angle (degrees)
	public final static double ra1 = 157.783;
	public final static double ra2 = 159.221;
	public final static double ra3 = 160.526;
	
	// declination angle (degrees)
	public final static double dec1 = 24.2403;
	public final static double dec2 = 27.2993;
	public final static double dec3 = 29.8982;
	
	public static void main(String[] args) {
		
		System.out.println("===================================");
		
		// test
		double r2 = 900; //km
		//double[][] mat = {{2, 2, 3}, {4, 5, 6}, {7, 8, 9}};
		
		double[][] pHat = findPHat();
		double[] c = findC(r2);
		
		MatrixMath.printMat(pHat);
		
	}
	
	static double[] findC(double r2) {
		// Method to calculate c1 and c3 values
		
		// Calculate c1 and c3 based off of time difference
		double c1 = (tau3/tau) * (1 + (m/Math.pow(r2, 3)) * (Math.pow(tau, 2) - Math.pow(tau3, 2)));
		double c3 = (-tau1/tau) * (1 + (m/Math.pow(r2, 3)) * (Math.pow(tau, 2) - Math.pow(tau1, 2)));
		
		// Return array
		double[] out = {c1,c3};
		
		return out;
	}
	
	static double[][] findPHat() {
		// Method to find the p hat matrix
		
		// Putting right ascension and declination into arrays after conversion to radians
		double[] ra = {Math.toRadians(ra1), Math.toRadians(ra2), Math.toRadians(ra3)};
		double[] dec = {Math.toRadians(dec1), Math.toRadians(dec2), Math.toRadians(dec3)};
		
		// Empty 3x3 matrix
		double[][] p = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
		
		// For loop to calculate p matrix
		for(int i = 0; i < 3; i++) {
			
			p[i][0] = Math.cos(dec[i]) * Math.cos(ra[i]);
			p[i][1] = Math.cos(dec[i]) * Math.sin(ra[i]);
			p[i][2] = Math.sin(dec[i]);
			
		}
		
		// Inverting the p matrix
		//double[][] out = MatrixMath.inverse(p);
		
		return p;
	}
	
	
}