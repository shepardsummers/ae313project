package main;

public class Gibbs {

	public Gibbs() {
		// TODO Auto-generated constructor stub
	}

	public static double[] run(double[][] r, double m) {
		// Method to do Gibb's method using r1, r2, r3, and m
		
		// Extracting r1, r2, r3 from r
		double[] r1 = {r[0][0], r[1][0], r[2][0]};
		double[] r2 = {r[0][1], r[1][1], r[2][1]};
		double[] r3 = {r[0][2], r[1][2], r[2][2]};
		
		// Magnitude of r1, r2, and r3
		double r1Mag = MatrixMath.mag(r1);
		double r2Mag = MatrixMath.mag(r2);
		double r3Mag = MatrixMath.mag(r3);
		
		// Finding D vector and magnitude
		double[] D = MatrixMath.vectAdd(MatrixMath.cross(r1, r2), MatrixMath.cross(r2, r3), MatrixMath.cross(r3, r1));
		double DMag = MatrixMath.mag(D);
		
		// Finding N vector and magnitude
		double[] N = MatrixMath.vectAdd(MatrixMath.scalMult(MatrixMath.cross(r2, r3), r1Mag), MatrixMath.scalMult(MatrixMath.cross(r3, r1), r2Mag), MatrixMath.scalMult(MatrixMath.cross(r1, r2), r3Mag));
		double NMag = MatrixMath.mag(N);
		
		// Finding S vector
		double[] S = MatrixMath.vectAdd(MatrixMath.scalMult(r1, r2Mag - r3Mag), MatrixMath.scalMult(r2, r3Mag - r1Mag), MatrixMath.scalMult(r3, r1Mag - r2Mag));
		
		// Finding v2
		double f = Math.sqrt(m/(NMag*DMag));
		double[] v2 = MatrixMath.scalMult(MatrixMath.vectAdd(MatrixMath.scalMult(MatrixMath.cross(D, r2), 1/r2Mag), S), f);
				
		return v2;
	}
	
}
