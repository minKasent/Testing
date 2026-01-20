package com.lab;

import java.util.InputMismatchException;

/**
 * Fraction (PhanSo) class representing a mathematical fraction.
 * Supports basic arithmetic operations on fractions.
 */
public class PhanSo {

    private int numerator;   // Tu so
    private int denominator; // Mau so

    /**
     * Constructor for creating a fraction.
     *
     * @param numerator   the numerator of the fraction
     * @param denominator the denominator of the fraction
     * @throws InputMismatchException if denominator is zero
     */
    public PhanSo(int numerator, int denominator) {
        if (denominator == 0) {
            throw new InputMismatchException("Denominator cannot be zero");
        }
        this.numerator = numerator;
        this.denominator = denominator;
    }

    /**
     * Default constructor creating fraction 0/1.
     */
    public PhanSo() {
        this.numerator = 0;
        this.denominator = 1;
    }

    public int getNumerator() {
        return numerator;
    }

    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    public void setDenominator(int denominator) {
        if (denominator == 0) {
            throw new InputMismatchException("Denominator cannot be zero");
        }
        this.denominator = denominator;
    }

    /**
     * Adds two fractions together.
     * Formula: a/b + c/d = (a*d + b*c) / (b*d)
     *
     * @param other the fraction to add
     * @return a new fraction representing the sum
     */
    public PhanSo add(PhanSo other) {
        int newNumerator = this.numerator * other.denominator + this.denominator * other.numerator;
        int newDenominator = this.denominator * other.denominator;
        return new PhanSo(newNumerator, newDenominator);
    }

    /**
     * Subtracts another fraction from this fraction.
     * Formula: a/b - c/d = (a*d - b*c) / (b*d)
     *
     * @param other the fraction to subtract
     * @return a new fraction representing the difference
     */
    public PhanSo subtract(PhanSo other) {
        int newNumerator = this.numerator * other.denominator - this.denominator * other.numerator;
        int newDenominator = this.denominator * other.denominator;
        return new PhanSo(newNumerator, newDenominator);
    }

    /**
     * Multiplies two fractions.
     * Formula: a/b * c/d = (a*c) / (b*d)
     *
     * @param other the fraction to multiply with
     * @return a new fraction representing the product
     */
    public PhanSo multiply(PhanSo other) {
        int newNumerator = this.numerator * other.numerator;
        int newDenominator = this.denominator * other.denominator;
        return new PhanSo(newNumerator, newDenominator);
    }

    /**
     * Divides this fraction by another fraction.
     * Formula: a/b / c/d = (a*d) / (b*c)
     *
     * @param other the fraction to divide by
     * @return a new fraction representing the quotient
     * @throws InputMismatchException if dividing would result in zero denominator
     */
    public PhanSo divide(PhanSo other) {
        int newNumerator = this.numerator * other.denominator;
        int newDenominator = this.denominator * other.numerator;
        return new PhanSo(newNumerator, newDenominator);
    }

    /**
     * Calculates the Greatest Common Divisor using Euclidean algorithm.
     *
     * @param a first number
     * @param b second number
     * @return the GCD of a and b
     */
    private int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    /**
     * Reduces the fraction to its simplest form.
     *
     * @return a new fraction in simplified form
     */
    public PhanSo simplify() {
        int gcd = gcd(numerator, denominator);
        int newNumerator = numerator / gcd;
        int newDenominator = denominator / gcd;
        
        // Ensure denominator is positive
        if (newDenominator < 0) {
            newNumerator = -newNumerator;
            newDenominator = -newDenominator;
        }
        
        return new PhanSo(newNumerator, newDenominator);
    }

    @Override
    public String toString() {
        return numerator + "/" + denominator;
    }
}
