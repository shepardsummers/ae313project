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
		
		// Sets values based on prompt or problem 5.11
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
		double[] r2r = {0, 0, 0};
		r2r[0] = ECIr[0][1];
		r2r[1] = ECIr[1][1];
		r2r[2] = ECIr[2][1];
		 
		// V and OE for Gibbs
		double[] v_g = Gibbs.run(ECIr, m);
		double[] oeG = OE.run(r2r, v_g, m);
		System.out.println("Gibbs OE: ");
		System.out.println("v2 vector (Gibbs): " + v_g[0] + " | " + v_g[1] + " | " + v_g[2]);
		System.out.println("Gibbs v2: " + MatrixMath.mag(v_g));
		System.out.println("h: " + oeG[0] + " e " + oeG[1] + " true anom: " + oeG[2] + " raan: " + + oeG[3] + " ap: " + oeG[4] + " inc: " + oeG[5]);
		
		// V and OE for Lagrange
		double[] v_L = Lagrange.run(ECIr, tau1, tau3, m);
		double[] oeL = OE.run(r2r, v_L, m);
		System.out.println("Lagrange OE: ");
		System.out.println("v2 vector (Simple Lagrange): " + v_L[0] + " | " + v_L[1] + " | " + v_L[2]);
		System.out.println("Lagrange v2: " + MatrixMath.mag(v_L));
		System.out.println("h: " + oeL[0] + " e " + oeL[1] + " true anom: " + oeL[2] + " raan: " + + oeL[3] + " ap: " + oeL[4] + " inc: " + oeL[5]);
		
		// V and OE difference between Gibbs and Lagrange
		System.out.println("Gibbs - Lagrange OE: ");
		System.out.println("v2 difference: " + (MatrixMath.mag(v_g)-MatrixMath.mag(v_L)));
		System.out.println("h: " + (oeG[0]-oeL[0]) + " e " + (oeG[1]-oeL[1]) + " true anom: " + (oeG[2]-oeL[2]) + " raan: " + + (oeG[3]-oeL[3]) + " ap: " + (oeG[4]-oeL[4]) + " inc: " + (oeG[5]-oeL[5]));
		
		// Lots of initial definitions
		double[][] newECI = ECIr;
		double[] oldR2 = {newECI[0][1], newECI[1][1], newECI[0][2]};
		double d = 100000;
		double pf1 = 0;
		double pf3 = 0;
		double pg1 = 0;
		double pg3 = 0;
		int count = 0;
		double[] newR2 = {0, 0, 0};
		double[] newV2 = {0, 0, 0};
		Boolean first = true;
		System.out.println("===================================");
		double[] oe = {};
		
		// Iteration while loop
		while (d > 0.0000000000000000000000001) {
			count++;
			
			System.out.print("RUN " + count);
			System.out.println("--------------------");	
			double[][] out = InterativeGauss.run(newECI, v_g, m, tau1, tau3, false, pf1, pf3, pg1, pg3, pHat, R, first);

			double[] cList = {out[0][0], out[0][1]};
			newECI = Gauss.findECIr(pHat, R, cList);
			double[] newR1 = {newECI[0][0], newECI[1][0], newECI[2][0]};
			double[] newR3 = {newECI[0][2], newECI[1][2], newECI[2][2]};
			
			newR2[0] = newECI[0][1];
			newR2[1] = newECI[1][1];
			newR2[2] = newECI[2][1];
			System.out.println("Old R2: " + oldR2[0] + " | " + oldR2[1] + " | " + oldR2[2]);
			System.out.println("New R2: " + newECI[0][1] + " | " + newECI[1][1] + " | " + newECI[2][1]);
			System.out.println("C Values: " + cValues[0] + " | " + cValues[1]);
			System.out.println("C Values: " + out[0][0] + " | " + out[0][1]);
			
			pf1 = out[1][0];
			pf3 = out[1][1];
			pg1 = out[1][2];
			pg3 = out[1][3];			
			
			newV2 = MatrixMath.scalMult(MatrixMath.vectAdd(MatrixMath.scalMult(newR1, -pf3), MatrixMath.scalMult(newR3, pf1)), 1/(pf1*pg3 - pf3*pg1));
			
			//System.out.println("New R2: " + newR1[0] + " | " + newR1[1] + " | " + newR1[2]);
			//System.out.println("New R3: " + newR3[0] + " | " + newR3[1] + " | " + newR3[2]);
			System.out.println("New  V2: " + newV2[0] + " | " + newV2[1] + " | " + newV2[2]);
			System.out.println("New  V2: " + MatrixMath.mag(newV2));
			
			d = Math.abs(MatrixMath.mag(oldR2) - MatrixMath.mag(newR2));
			
			oe = OE.run(newR2, newV2, m);
			System.out.println("h: " + oe[0] + " e " + oe[1] + " true anom: " + oe[2] + " raan: " + + oe[3] + " ap: " + oe[4] + " inc: " + oe[5]);
			
			System.out.println("Disc: " + d);
			System.out.println("===================================");
			
			oldR2 = newR2;
			
			if (count > 20) {
				d = 0;
			}
			if (first == true) {
				first = false;
			}
			

			
		}

		System.out.println("Converged obital elements: ");
		System.out.println("Converged R2: " + MatrixMath.mag(newR2));
		System.out.println("Converged V2: " + MatrixMath.mag(newV2));
		System.out.println("h: " + oe[0] + " e " + oe[1] + " true anom: " + oe[2] + " raan: " + oe[3] + " ap: " + oe[4] + " inc: " + oe[5]);
		
		System.out.println("Difference between converged and Gibbs: ");
		System.out.println("Diff R2: " + (MatrixMath.mag(newR2) - r2));
		System.out.println("Diff V2: " + (MatrixMath.mag(newV2) - MatrixMath.mag(v_g)));
		System.out.println("h: " + (oe[0]-oeG[0]) + " e " + (oe[1]-oeG[1]) + " true anom: " + (oe[2]-oeG[2]) + " raan: " + (oe[3]-oeG[3]) + " ap: " + (oe[4]-oeG[4]) + " inc: " + (oe[5]-oeG[5]));
		
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
			
			tau1 = t1 - t2;
			tau3 = t3 - t2;
			tau = tau3 - tau1;
			
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