package il.ac.tau.cs.sw1.hw6;

import java.util.Arrays;

public class Polynomial {

	public double[] coeff;
	
	/*
	 * Creates the zero-polynomial with p(x) = 0 for all x.
	 */
	public Polynomial() {
		coeff = new double[1];
		coeff[0] = 0;
	}

	/*
	 * Creates a new polynomial with the given coefficients.
	 */
	public Polynomial(double[] coefficients) {
		coeff = new double[coefficients.length];
		System.arraycopy(coefficients, 0, coeff, 0, coefficients.length);
		coeff = reductPoly(coeff);
	}

	/*
	 * Addes this polynomial to the given one
	 *  and retruns the sum as a new polynomial.
	 */
	public Polynomial adds(Polynomial polynomial) {
		int thisLength  = this.coeff.length;
		int polyLength = polynomial.coeff.length;

		int newPolyLength = Math.max(thisLength, polyLength);
		int divergeIndex = Math.min(thisLength, polyLength);

		double[] result = new double[newPolyLength];

		for (int i = 0; i < divergeIndex; i++) {
			result[i] = this.coeff[i] + polynomial.coeff[i];
		}

		if (thisLength > polyLength) {
			System.arraycopy(this.coeff, divergeIndex, result, divergeIndex, newPolyLength - divergeIndex);
		} else {
			System.arraycopy(polynomial.coeff, divergeIndex, result, divergeIndex, newPolyLength - divergeIndex);
		}

		double[] reducedResult = reductPoly(result);

		return new Polynomial(reducedResult);
	}

	private static double[] reductPoly(double[] coefficients) {
		if (coefficients.length < 1) {
			return new double[] {0.0};
		}

		int lastIndex = coefficients.length;
		for (int i = coefficients.length - 1; i >= 0; i--,lastIndex--) {
			if (coefficients[i] != 0.0) {
				break;
			}
		}

		return Arrays.copyOfRange(coefficients, 0, lastIndex);
	}

	/*
	 * Multiplies a to this polynomial and returns 
	 * the result as a new polynomial.
	 */
	public Polynomial multiply(double a) {
		if (a == 0) {
			return new Polynomial();
		}

		double[] result = new double[this.coeff.length];

		for (int i = 0; i < this.coeff.length; i++) {
			result[i] = a * this.coeff[i];
		}

		return new Polynomial(result);
	}

	/*
	 * Returns the degree (the largest exponent) of this polynomial.
	 */
	public int getDegree() {
		return this.coeff.length - 1;
	}

	/*
	 * Returns the coefficient of the variable x 
	 * with degree n in this polynomial.
	 */
	public double getCoefficient(int n) {
		if (n >= this.coeff.length || n < 0) {
			return 0;
		}

		return this.coeff[n];
	}
	
	/*
	 * set the coefficient of the variable x 
	 * with degree n to c in this polynomial.
	 * If the degree of this polynomial < n, it means that that the coefficient of the variable x 
	 * with degree n was 0, and now it will change to c. 
	 */
	public void setCoefficient(int n, double c) {
		if (n < 0) {
			return;
		}

		if (n < this.coeff.length) {
			this.coeff[n] = c;
			return;
		}

		double[] result = new double[n + 1];

		System.arraycopy(this.coeff, 0, result, 0, this.coeff.length);

		result[n] = c;

		this.coeff = result;
	}
	
	/*
	 * Returns the first derivation of this polynomial.
	 *  The first derivation of a polynomal a0x0 + ...  + anxn is defined as 1 * a1x0 + ... + n anxn-1.
	
	 */
	public Polynomial getFirstDerivation() {
		if (this.coeff.length < 2) {
			return new Polynomial();
		}

		double[] result = new double[this.coeff.length - 1];
		for (int i = 0; i < result.length; i++) {
			result[i] = (i + 1) * this.coeff[i + 1];
		}

		return new Polynomial(result);
	}
	
	/*
	 * given an assignment for the variable x,
	 * compute the polynomial value
	 */
	public double computePolynomial(double x) {
		double result = this.coeff[0];
		for (int i = 1; i < this.coeff.length; i++) {
			result += (Math.pow(x, i) * this.coeff[i]);
		}

		return result;
	}
	
	/*
	 * given an assignment for the variable x,
	 * return true iff x is an extrema point (local minimum or local maximum of this polynomial)
	 * x is an extrema point if and only if The value of first derivation of a polynomal at x is 0
	 * and the second derivation of a polynomal value at x is not 0.
	 */
	public boolean isExtrema(double x) {
		Polynomial firstDerivation = this.getFirstDerivation();
		Polynomial secondDerivation = firstDerivation.getFirstDerivation();

		double computeFirstDerivation = firstDerivation.computePolynomial(x);
		double computeSecondDerivation = secondDerivation.computePolynomial(x);

		return (computeFirstDerivation == 0 && computeSecondDerivation != 0);
	}
}
