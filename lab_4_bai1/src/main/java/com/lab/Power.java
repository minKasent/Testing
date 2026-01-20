package com.lab;

/**
 * Utility class for power/exponentiation calculations.
 * Exercise 1 - Power function implementation.
 */
public class Power {

    /**
     * Calculates x raised to the power of n.
     * Handles positive, negative, and zero exponents.
     *
     * @param x the base value
     * @param n the exponent (can be positive, negative, or zero)
     * @return x^n (x raised to the power of n)
     */
    public static double power(double x, int n) {
        // Any number raised to power 0 equals 1
        if (n == 0) {
            return 1.0;
        }
        
        // Handle negative exponents: x^(-n) = 1 / x^n
        if (n < 0) {
            return 1.0 / power(x, -n);
        }
        
        // Positive exponent: calculate x^n
        double result = 1.0;
        for (int i = 0; i < n; i++) {
            result *= x;
        }
        
        return result;
    }

    /**
     * Optimized power calculation using binary exponentiation.
     * Time complexity: O(log n)
     *
     * @param x the base value
     * @param n the exponent (can be positive, negative, or zero)
     * @return x^n (x raised to the power of n)
     */
    public static double powerOptimized(double x, int n) {
        if (n == 0) {
            return 1.0;
        }
        
        long exponent = n;
        
        // Handle negative exponent
        if (exponent < 0) {
            x = 1.0 / x;
            exponent = -exponent;
        }
        
        double result = 1.0;
        while (exponent > 0) {
            if (exponent % 2 == 1) {
                result *= x;
            }
            x *= x;
            exponent /= 2;
        }
        
        return result;
    }
}
