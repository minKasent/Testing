package com.lab;

/**
 * Polynomial class for evaluating polynomial expressions.
 * Exercise 2 - Polynomial implementation.
 * 
 * A polynomial of degree n: P(x) = a0 + a1*x + a2*x^2 + ... + an*x^n
 * where coefficients array contains [a0, a1, a2, ..., an]
 */
public class Polynomial {

    private int degree;           // n - degree of the polynomial
    private double[] coefficients; // array of coefficients [a0, a1, ..., an]

    /**
     * Constructor for creating a polynomial.
     *
     * @param n            the degree of the polynomial (must be >= 0)
     * @param coefficients array of coefficients (must have exactly n + 1 elements)
     * @throws IllegalArgumentException if n < 0 or if number of coefficients != n + 1
     */
    public Polynomial(int n, double[] coefficients) {
        // Validate degree
        if (n < 0) {
            throw new IllegalArgumentException("Invalid Data");
        }
        
        // Validate number of coefficients
        if (coefficients == null || coefficients.length != n + 1) {
            throw new IllegalArgumentException("Invalid Data");
        }
        
        this.degree = n;
        this.coefficients = new double[coefficients.length];
        System.arraycopy(coefficients, 0, this.coefficients, 0, coefficients.length);
    }

    /**
     * Evaluates the polynomial at a given value x.
     * P(x) = a0 + a1*x + a2*x^2 + ... + an*x^n
     *
     * @param x the value at which to evaluate the polynomial
     * @return the result of P(x)
     */
    public double evaluate(double x) {
        double result = 0.0;
        double xPower = 1.0; // x^0 = 1
        
        for (int i = 0; i <= degree; i++) {
            result += coefficients[i] * xPower;
            xPower *= x;
        }
        
        return result;
    }

    /**
     * Evaluates the polynomial using Horner's method (more efficient).
     * P(x) = a0 + x(a1 + x(a2 + ... + x(an-1 + x*an)...))
     *
     * @param x the value at which to evaluate the polynomial
     * @return the result of P(x)
     */
    public double evaluateHorner(double x) {
        double result = coefficients[degree];
        
        for (int i = degree - 1; i >= 0; i--) {
            result = result * x + coefficients[i];
        }
        
        return result;
    }

    /**
     * Gets the degree of the polynomial.
     *
     * @return the degree n
     */
    public int getDegree() {
        return degree;
    }

    /**
     * Gets the coefficient at index i.
     *
     * @param i the index of the coefficient
     * @return the coefficient ai
     */
    public double getCoefficient(int i) {
        if (i < 0 || i > degree) {
            throw new IndexOutOfBoundsException("Invalid coefficient index");
        }
        return coefficients[i];
    }

    /**
     * Gets all coefficients.
     *
     * @return a copy of the coefficients array
     */
    public double[] getCoefficients() {
        double[] copy = new double[coefficients.length];
        System.arraycopy(coefficients, 0, copy, 0, coefficients.length);
        return copy;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = degree; i >= 0; i--) {
            if (coefficients[i] != 0) {
                if (sb.length() > 0 && coefficients[i] > 0) {
                    sb.append(" + ");
                } else if (coefficients[i] < 0) {
                    sb.append(" - ");
                }
                
                double absCoeff = Math.abs(coefficients[i]);
                if (i == 0) {
                    sb.append(absCoeff);
                } else if (i == 1) {
                    sb.append(absCoeff).append("x");
                } else {
                    sb.append(absCoeff).append("x^").append(i);
                }
            }
        }
        return sb.length() > 0 ? sb.toString() : "0";
    }
}
