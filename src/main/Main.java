package main;

public class Main {
	
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
	
	// topocentric right acension angle (degrees)
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
		
		//double[][] pHat = findPHat();
		double[] c = findC(r2);
		
		double[][] mat = {{2, 2, 3}, {4, 5, 6}, {7, 8, 9}};
		double[][] inv = MatrixMath.inverse(mat);
		
		for (int row = 0; row <= 2; row++) {
			System.out.println("Row " + (row + 1));
			for (int col = 0; col <= 2; col++) {
				System.out.println(inv[row][col]);
			}
		}
		
		//System.out.println(c[0]);
		//System.out.println(c[1]);
	}
	
	static double[] findC(double r2) {
		
		double c1 = (tau3/tau) * (1 + (m/Math.pow(r2, 3)) * (Math.pow(tau, 2) - Math.pow(tau3, 2)));
		double c3 = (-tau1/tau) * (1 + (m/Math.pow(r2, 3)) * (Math.pow(tau, 2) - Math.pow(tau1, 2)));
		
		double[] out = {c1,c3};
		return out;
	}
	
	static double[][] findPHat() {
		
		double[] ra = {Math.toRadians(ra1), Math.toRadians(ra2), Math.toRadians(ra3)};
		double[] dec = {Math.toRadians(dec1), Math.toRadians(dec2), Math.toRadians(dec3)};
		
		double[][] p = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
		
		for(int i = 0; i < 3; i++) {
			
			p[i][0] = Math.cos(dec[i]) * Math.cos(ra[i]);
			p[i][1] = Math.cos(dec[i]) * Math.sin(ra[i]);
			p[i][2] = Math.sin(dec[i]);
			
			/*
			System.out.println(out[0][i]);
			System.out.println(out[1][i]);
			System.out.println(out[2][i]);
			*/
				
		}
		
		double[][] out = MatrixMath.inverse(p);
		
		return out;
	}
	
	
}