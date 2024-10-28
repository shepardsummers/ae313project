package main;

public class Main {
	
	// INPUT VARIABLES
	
	// system constants
	public final static int m = 398600; // km^3/s^2
	public final static int eRad = 6378; // km
	
	// time (s)
	public final static int t1 = 0;
	public final static int t2 = 5 * 60;
	public final static int t3 = 10 * 60;
	
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
		
		System.out.println(m);
		
	}
	
	
}