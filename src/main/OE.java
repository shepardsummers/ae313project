package main;

public class OE {

	public OE() {
		// TODO Auto-generated constructor stub
	}
	
	public static double[] run(double[] r, double[] v, double m) {
		// Method for determining classical orbital elements
		
		// Unit vectors i and k
		double[] iHat = {1, 0, 0};
		double[] kHat = {0, 0, 1};
		
		// Magnitude of r
		double rMag = MatrixMath.mag(r);
		
		// H vector and magnitude
		double[] h = MatrixMath.cross(r, v);
		double hMag = MatrixMath.mag(h);
		
		// Eccentricity vector and magnitude
		double[] e = MatrixMath.vectAdd(MatrixMath.scalMult(MatrixMath.cross(v, h), 1/m), MatrixMath.scalMult(r, -1/rMag));
		double eMag = MatrixMath.mag(e);
		
		// True anomaly angle 
		double tht = Math.acos(MatrixMath.dot(r, e) / (rMag * eMag));
		double tht_deg = Math.toDegrees(tht);
		
		// Node line vector and magnitude 
		double[] N = MatrixMath.cross(kHat, h); 
		double NMag = MatrixMath.mag(N);
		
		// RAAN 
		double raan = Math.acos(MatrixMath.dot(N, iHat) / Math.abs(NMag));
		if (N[1] < 0) {
			raan = 2*Math.PI - raan;
		}
		double raan_deg = Math.toDegrees(raan);
		
		// Augment of Periapsis
		double AP = Math.acos(MatrixMath.dot(N, e) / (Math.abs(NMag)*eMag));
		if (e[2] < 0) {
			AP = 2*Math.PI - AP;			
		}
		double AP_deg = Math.toDegrees(AP);
		
		// Inclination
		double inc = Math.acos(MatrixMath.dot(h, kHat) / hMag);
		double inc_deg = Math.toDegrees(inc);
		
		// Storing angles in vector
		double[] out = {hMag, eMag, tht_deg, raan_deg, AP_deg, inc_deg};
		
		return out;
	}
	
}
