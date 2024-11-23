package main;

public class Main {
	
	/*
	 * AE 313 HON PROJECT
	 * AUTHOR: Shepard Summers
	 * PURPOSE: To use Gauss method to obtain radius, velocity and orbital elements
	 * 
	 */
	
	// INPUT VARIABLES
	
	// assorted constants
	public static int m = 398600; // km^3/s^2
	public static int eRad = 6378; // km
	public static double alt = 500; // km
	public static int tLat = 60; // deg
	public static double tRad = eRad + alt; // km
	
	// time (s)
	public static double t1 = 0;
	public static double t2 = 5 * 60;
	public static double t3 = 10 * 60;
	
	public static double tau = t3 - t1;
	public static double tau1 = t1 - t2;
	public static double tau3 = t3 - t2;
	
	// mean anomaly (degrees)
	public static double theta1 = 150.000;
	public static double theta2 = 151.253;
	public static double theta3 = 152.507;
	
	// topocentric right ascension angle (degrees)
	public static double ra1 = 157.783;
	public static double ra2 = 159.221;
	public static double ra3 = 160.526;
	
	// declination angle (degrees)
	public static double dec1 = 24.2403;
	public static double dec2 = 27.2993;
	public static double dec3 = 29.8982;
	
	public static void main(String[] args) {
		
		dec("5.11");
		
		System.out.println("===================================");			
		
		double[][] pHat = Gauss.findPHat(ra1, ra2, ra3, dec1, dec2, dec3);
		double[][] R = Gauss.findR(theta1, theta2, theta3, tLat, tRad);
		double[] AB = Gauss.findAB(pHat, R, tau, tau1, tau3);
		double r2 = Gauss.interate(AB, R, pHat, m, false);
		
		System.out.println("===================================");		
		
		dispMat(false, R, pHat, AB);
		
		System.out.println("R2: " + r2);
		
		double[] cValues = Gauss.findC(r2, tau, tau3, tau1, m);		
		
		// Find ECI distance
		double[][] ECIr = Gauss.findECIr(pHat, R, cValues);
		
		MatrixMath.printMat(ECIr);
		
		//MatrixMath.printMat(MatrixMath.matMult(pHat, MatrixMath.inverse(pHat)));
		
		double[] v_g = Gibbs.run(ECIr, m);
		/*
		System.out.println("v2 vector (Gibbs): ");
		System.out.println(v_g[0]);
		System.out.println(v_g[1]);
		System.out.println(v_g[2]);
		*/
		
		double[] v_l = Lagrange.run(ECIr, tau1, tau3, m);
		/*
		System.out.println("v2 vector (Simple Lagrange): ");
		System.out.println(v_l[0]);
		System.out.println(v_l[1]);
		System.out.println(v_l[2]);
		*/
		 
		double[][] newECI = ECIr;
		double[] oldR2 = {newECI[0][1], newECI[1][1], newECI[0][2]};
		double d = 100000;
		double pf1 = 0;
		double pf3 = 0;
		double pg1 = 0;
		double pg3 = 0;
		int count = 0;
		Boolean first = true;
		while (d > 0.00000000000001) {
			count++;
			
			System.out.print("RUN " + count);
			System.out.println("--------------------");	
			double[][] out = InterativeGauss.run(newECI, v_g, m, tau1, tau3, false, pf1, pf3, pg1, pg3, pHat, R, first);

			double[] cList = {out[0][0], out[0][1]};
			newECI = Gauss.findECIr(pHat, R, cList);
			double[] newR2 = {newECI[0][1], newECI[1][1], newECI[0][2]};
			System.out.println("Old R2: " + oldR2[0] + " | " + oldR2[1] + " | " + oldR2[2]);
			System.out.println("New R2: " + newECI[0][1] + " | " + newECI[1][1] + " | " + newECI[2][1]);
			System.out.println("C Values: " + cValues[0] + " | " + cValues[1]);
			System.out.println("C Values: " + out[0][0] + " | " + out[0][1]);
			
			pf1 = out[1][0];
			pf3 = out[1][1];
			pg1 = out[1][2];
			pg3 = out[1][3];
			
			d = Math.abs(MatrixMath.mag(oldR2) - MatrixMath.mag(newR2));
			System.out.println("Disc: " + d);
			System.out.println("===================================");	
			
			if (count > 10) {
				d = 0;
			}
			if (first == true) {
				first = false;
			}
		}
		
	}	
	
	public static void dec(String input) {
		
		if (input == "Normal") {
			// assorted constants
			m = 398600; // km^3/s^2
			eRad = 6378; // km
			alt = 0.5; // km
			tLat = 60; // deg
			tRad = eRad + alt; // km
			
			// time (s)
			t1 = 0;
			t2 = 5 * 60;
			t3 = 10 * 60;
			
			tau = t3 - t1;
			tau1 = t1 - t2;
			tau3 = t3 - t2;
			
			// mean anomaly (degrees)
			theta1 = 150.000;
			theta2 = 151.253;
			theta3 = 152.507;
			
			// topocentric right ascension angle (degrees)
			ra1 = 157.783;
			ra2 = 159.221;
			ra3 = 160.526;
			
			// declination angle (degrees)
			dec1 = 24.2403;
			dec2 = 27.2993;
			dec3 = 29.8982;
		} else if (input == "5.11") {
			// assorted constants
			m = 398600; // km^3/s^2
			eRad = 6378; // km
			alt = 1; // km
			tLat = 40; // deg
			tRad = eRad + alt; // km
			
			// time (s)
			t1 = 0;
			t2 = 118.1;
			t3 = 237.58;
			
			tau1 = t1 - t2;
			tau3 = t3 - t2;
			tau = tau3 - tau1;
			
			// mean anomaly (degrees)
			theta1 = 44.506;
			theta2 = 45;
			theta3 = 45.499;
			
			// topocentric right ascension angle (degrees)
			ra1 = 43.537;
			ra2 = 54.420;
			ra3 = 64.318;
			
			// declination angle (degrees)
			dec1 = -8.7833;
			dec2 = -12.074;
			dec3 = -15.105;
		}
		
		
		
	}
	
	public static void dispMat(Boolean ans, double[][] R, double[][] pHat, double[] AB) {
		
		if (ans == true) {
			System.out.println("Tau values: " + tau1 + " | " + tau3 + " | " + tau + " | ");
			System.out.println("===================================");
			
			System.out.println("R: ");
			MatrixMath.printMat(R);
			System.out.println("===================================");
			
			System.out.println("pHat: ");
			MatrixMath.printMat(pHat);
			System.out.println("===================================");
			
			System.out.println("pHat inv: ");
			MatrixMath.printMat(pHat);
			System.out.println("===================================");
			
			System.out.println("M: ");
			MatrixMath.printMat(MatrixMath.matMult(MatrixMath.inverse(pHat), R));
			System.out.println("===================================");
			
			System.out.println("A: " + AB[0]);
			System.out.println("B: " + AB[1]);
			System.out.println("===================================");
		}
		
	}
}