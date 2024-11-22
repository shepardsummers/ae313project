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
	public final static int tRad = eRad + alt; // km
	
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
		
		//double[][] mat = {{2, 2, 3}, {4, 5, 6}, {7, 8, 9}};
		
		double[][] pHat = Gauss.findPHat(ra1, ra2, ra3, dec1, dec2, dec3);
		double[][] R = Gauss.findR(theta1, theta2, theta3, tLat, tRad);
		double[] AB = Gauss.findAB(pHat, R, tau, tau1, tau3);
		
		double out = Gauss.interate(AB, R, pHat, m);
		
		System.out.println("===================================");
		System.out.println(out);
		
		//System.out.println(AB[0]);
		//System.out.println(AB[1]);
		
	}	
	
}