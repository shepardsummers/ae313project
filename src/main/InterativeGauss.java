package main;

public class InterativeGauss {

	public InterativeGauss() {
		// TODO Auto-generated constructor stub
	}
	
	public static double[][] run(double[][] r, double[] v2, double m, double tau1, double tau3, boolean doShow, double pf1, double pf3, double pg1, double pg3, double[][] pHat, double[][] R, Boolean first) {
		// Method for interative gauss process
		
		// Extracting r2 from r
		double[] r2 = {r[0][1], r[1][1], r[2][1]};
		
		double r2Mag = MatrixMath.mag(r2);
		double v2Mag = MatrixMath.mag(v2);
		//System.out.println("r2: " + r2Mag);
		//System.out.println("v2: " + v2Mag);
		
		
		// Inverse of semi-major axis
		double alpha = (2/r2Mag) - (Math.pow(v2Mag, 2)/m);
		//System.out.println("Alpha: " + alpha);
		
		// Radial component of v2
		double v2r = MatrixMath.dot(v2, r2) / r2Mag;
		//System.out.println("v2r: " + v2r);
		
		// Empty matrix to store X values
		double[] x = {0, 0};		
		
		// Initial guess that Chobotov recommends
		double guess = Math.sqrt(m)*alpha*tau1;
		
		// Discrepancy value
		double d = 1000000;
		
		// Count tracker
		int count = 0;
		
		// Define xn
		double xn;
		
		// Newton's method to determine X
		while (Math.abs(d) >= 0.000001) {
			count++;
			// Updates current value to last guess
			xn = guess;
			
			// The funny value that gets thrown into C and S functions
			double zn = alpha*Math.pow(xn, 2);
			
			// Function
			double fnct = ((r2Mag*v2r) / Math.sqrt(m))*Math.pow(xn, 2)*C(zn) + (1 - alpha*r2Mag)*Math.pow(xn, 3)*S(zn) + r2Mag*xn - Math.sqrt(m)*tau1;
			
			// Derivative of function
			double fnctP = ((r2Mag*v2r) / Math.sqrt(m))*xn*(1 - alpha*Math.pow(xn, 2)*S(zn)) + (1 - alpha*r2Mag)*Math.pow(xn, 2)*C(zn) + r2Mag;
			
			
			// Newton's method
			guess = xn - fnct/fnctP;
			
			// Updates discrepancy value
			d = guess - xn;
			
			// Show/hides output
			if (doShow == true) {
				System.out.println("Run " + count + " -+-+-+-+-+-+-+-+-+-+-+-+-");
				System.out.println("  Xn: " + xn);
				System.out.println("  Xn+1: " + guess);
				System.out.println("  Disc: " + d);
			}
			
		}
		x[0] = guess;
		
		// Initial guess that Chobotov recommends
		guess = Math.sqrt(m)*alpha*tau3;
		
		// Discrepancy value
		d = 1000000;
		
		// Count tracker
		count = 0;
		double zn = 0;
		
		// Newton's method to determine X
		while (Math.abs(d) >= 0.000001) {
			count++;
			// Updates current value to last guess
			xn = guess;
			
			// The funny value that gets thrown into C and S functions
			zn = alpha*Math.pow(xn, 2);
			
			// Function
			double fnct = ((r2Mag*v2r) / Math.sqrt(m))*Math.pow(xn, 2)*C(zn) + (1 - alpha*r2Mag)*Math.pow(xn, 3)*S(zn) + r2Mag*xn - Math.sqrt(m)*tau3;
			
			// Derivative of function
			double fnctP = ((r2Mag*v2r) / Math.sqrt(m))*xn*(1 - alpha*Math.pow(xn, 2)*S(zn)) + (1 - alpha*r2Mag)*Math.pow(xn, 2)*C(zn) + r2Mag;
			
			
			// Newton's method
			guess = xn - fnct/fnctP;
			
			// Updates discrepancy value
			d = guess - xn;
			
			// Show/hides output
			if (doShow == true) {
				System.out.println("Run " + count + " -+-+-+-+-+-+-+-+-+-+-+-+-");
				System.out.println("  Xn: " + xn);
				System.out.println("  Xn+1: " + guess);
				System.out.println("  Disc: " + d);
			}
			
		}
		x[1] = guess;
		//System.out.println(zn);
		//System.out.println("S and C: " + C(zn) + " | " + S(zn));
		
		System.out.println("X1: " + x[0] + " | X3: " + x[1]);
		
		// Calculate f1 and g3 values
		double f1 = 1 - (Math.pow(x[0], 2)/r2Mag)*C(alpha*Math.pow(x[0], 2));
		double g1 = tau1 - (1/Math.sqrt(m))*Math.pow(x[0], 3)*S(alpha*Math.pow(x[0], 2));
		
		double f3 = 1 - (Math.pow(x[1], 2)/r2Mag)*C(alpha*Math.pow(x[1], 2));
		double g3 = tau3 - (1/Math.sqrt(m))*Math.pow(x[1], 3)*S(alpha*Math.pow(x[1], 2));
		
		//System.out.println(f1);
		//System.out.println(g1);
		//System.out.println(f3);
		//System.out.println(g3);
		
		
		System.out.println("pF1 : " + pf1 + " | pG1: " + pg1 + " | pF3 : " + pf3 + " | G3: " + pg3);
		
		// Checking if this is the first run of the iterations
		if (first == true) {
			f1 = (2*f1)/2;
			g1 = (2*g1)/2;
			
			f3 = (2*f3)/2;
			g3 = (2*g3)/2;
		} else {
			f1 = (f1 + pf1)/2;
			g1 = (g1 + pg1)/2;
		
			f3 = (f3 + pf3)/2;
			g3 = (g3 + pg3)/2;
		}
		
		System.out.println("F1 : " + f1 + " | G1: " + g1 + " | F3 : " + f3 + " | G3: " + g3);
		
		
		double c1 = g3 / (f1*g3 - f3*g1);
		double c3 = -g1 / (f1*g3 - f3*g1);
		double[] cList = {c1, c3};
		
		double[][] out = {cList, {f1, f3, g1, g3}};
		return out;
	}
	
	private static double C(double z) {
		// C function as defined in textbook
		
		// Initialize output variable
		double out = 0;
		
		if (z > 0) {
			out = (1 - Math.cos(Math.sqrt(z))) / z;
		} else if (z < 0) {
			out = (Math.cosh(Math.sqrt(-z)) - 1) / -z;
		} else if (z == 0) {
			out = 0.5;
		}
		
		
		return out;
	}
	
	private static double S(double z) {
		// S function as defined in the textbook
		
		// Initialize output variable
		double out = 0;
		
		if (z > 0) {
			out = (Math.sqrt(z) - Math.sin(Math.sqrt(z))) / Math.pow(Math.sqrt(z), 3);
		} else if (z < 0) {
			out = (Math.sinh(Math.sqrt(-z)) - Math.sqrt(-z)) / Math.pow(Math.sqrt(z), 3);
		} else if (z == 0) {
			out = 1/6;
		}
		
		return out;
	}
}
