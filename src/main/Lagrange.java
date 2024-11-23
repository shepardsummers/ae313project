package main;

public class Lagrange {

	public Lagrange() {
		// TODO Auto-generated constructor stub
	}

	public static double[] run(double[][] r, double tau1, double tau3, double m){
		// Method for finding v using Lagrange coefficients 
		
		// Extracting r1, r2, r3 from r
		double[] r1 = {r[0][0], r[1][0], r[2][0]};
		double[] r2 = {r[0][1], r[1][1], r[2][1]};
		double[] r3 = {r[0][2], r[1][2], r[2][2]};
		
		// Magnitude of r2
		double r2Mag = MatrixMath.mag(r2);
		
		double f1 = 1 - (m/(2*Math.pow(r2Mag, 3)))*Math.pow(tau1, 2);
		double g1 = tau1 - (m/(6*Math.pow(r2Mag, 3)))*Math.pow(tau1, 3);
		//System.out.println("1: " + f1 + " | " + g1);
		
		double f3 = 1 - (m/(2*Math.pow(r2Mag, 3)))*Math.pow(tau3, 2);
		double g3 = tau3 - (m/(6*Math.pow(r2Mag, 3)))*Math.pow(tau3, 3);
		//System.out.println("3: " + f3 + " | " + g3);
		
		// Calculation of v2
		double[] v2 = MatrixMath.scalMult(MatrixMath.vectAdd(MatrixMath.scalMult(r1, f3), MatrixMath.scalMult(r3, -f1)), 1/(f3*g1 - f1*g3));		
		
		OE.run(r2, v2, m);
		
		return v2;
	}
	
}
