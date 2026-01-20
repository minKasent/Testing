package com.lab;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Polynomial class using JUnit 5.
 * Exercise 2 - Testing Polynomial class.
 */
class PolynomialTest {

    private static final double DELTA = 0.0001; // Tolerance for double comparison

    // ==================== Constructor Validation Tests ====================

    @Test
    @DisplayName("Constructor should throw IllegalArgumentException when n < 0")
    void constructor_NegativeDegree_ThrowsIllegalArgumentException() {
        double[] coefficients = {1.0, 2.0};
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Polynomial(-1, coefficients);
        });
        
        assertEquals("Invalid Data", exception.getMessage());
    }

    @Test
    @DisplayName("Constructor should throw IllegalArgumentException when n = -5")
    void constructor_NegativeDegreeMinusFive_ThrowsIllegalArgumentException() {
        double[] coefficients = {1.0};
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Polynomial(-5, coefficients);
        });
        
        assertEquals("Invalid Data", exception.getMessage());
    }

    @Test
    @DisplayName("Constructor should throw IllegalArgumentException when coefficients count != n + 1 (too few)")
    void constructor_TooFewCoefficients_ThrowsIllegalArgumentException() {
        // n = 2 requires 3 coefficients, but only providing 2
        double[] coefficients = {1.0, 2.0};
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Polynomial(2, coefficients);
        });
        
        assertEquals("Invalid Data", exception.getMessage());
    }

    @Test
    @DisplayName("Constructor should throw IllegalArgumentException when coefficients count != n + 1 (too many)")
    void constructor_TooManyCoefficients_ThrowsIllegalArgumentException() {
        // n = 1 requires 2 coefficients, but providing 4
        double[] coefficients = {1.0, 2.0, 3.0, 4.0};
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Polynomial(1, coefficients);
        });
        
        assertEquals("Invalid Data", exception.getMessage());
    }

    @Test
    @DisplayName("Constructor should throw IllegalArgumentException when coefficients array is null")
    void constructor_NullCoefficients_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Polynomial(2, null);
        });
        
        assertEquals("Invalid Data", exception.getMessage());
    }

    @Test
    @DisplayName("Constructor should throw IllegalArgumentException when coefficients array is empty for n > 0")
    void constructor_EmptyCoefficientsForPositiveDegree_ThrowsIllegalArgumentException() {
        double[] coefficients = {};
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Polynomial(1, coefficients);
        });
        
        assertEquals("Invalid Data", exception.getMessage());
    }

    // ==================== Valid Constructor Tests ====================

    @Test
    @DisplayName("Constructor should create polynomial with valid inputs")
    void constructor_ValidInputs_CreatesPolynomial() {
        // P(x) = 1 + 2x + 3x^2 (degree 2, 3 coefficients)
        double[] coefficients = {1.0, 2.0, 3.0};
        
        Polynomial poly = new Polynomial(2, coefficients);
        
        assertEquals(2, poly.getDegree());
        assertEquals(1.0, poly.getCoefficient(0), DELTA);
        assertEquals(2.0, poly.getCoefficient(1), DELTA);
        assertEquals(3.0, poly.getCoefficient(2), DELTA);
    }

    @Test
    @DisplayName("Constructor should create constant polynomial (degree 0)")
    void constructor_DegreeZero_CreatesConstantPolynomial() {
        double[] coefficients = {5.0};
        
        Polynomial poly = new Polynomial(0, coefficients);
        
        assertEquals(0, poly.getDegree());
        assertEquals(5.0, poly.getCoefficient(0), DELTA);
    }

    @Test
    @DisplayName("Constructor should create linear polynomial (degree 1)")
    void constructor_DegreeOne_CreatesLinearPolynomial() {
        // P(x) = 3 + 2x
        double[] coefficients = {3.0, 2.0};
        
        Polynomial poly = new Polynomial(1, coefficients);
        
        assertEquals(1, poly.getDegree());
    }

    // ==================== Polynomial Evaluation Tests ====================

    @Test
    @DisplayName("evaluate constant polynomial should return constant value")
    void evaluate_ConstantPolynomial_ReturnsConstant() {
        // P(x) = 5
        double[] coefficients = {5.0};
        Polynomial poly = new Polynomial(0, coefficients);
        
        assertEquals(5.0, poly.evaluate(0), DELTA);
        assertEquals(5.0, poly.evaluate(10), DELTA);
        assertEquals(5.0, poly.evaluate(-5), DELTA);
    }

    @Test
    @DisplayName("evaluate linear polynomial should return correct value")
    void evaluate_LinearPolynomial_ReturnsCorrectValue() {
        // P(x) = 1 + 2x
        double[] coefficients = {1.0, 2.0};
        Polynomial poly = new Polynomial(1, coefficients);
        
        // P(3) = 1 + 2*3 = 7
        assertEquals(7.0, poly.evaluate(3), DELTA);
    }

    @Test
    @DisplayName("evaluate quadratic polynomial should return correct value")
    void evaluate_QuadraticPolynomial_ReturnsCorrectValue() {
        // P(x) = 1 + 2x + 3x^2
        double[] coefficients = {1.0, 2.0, 3.0};
        Polynomial poly = new Polynomial(2, coefficients);
        
        // P(2) = 1 + 2*2 + 3*4 = 1 + 4 + 12 = 17
        assertEquals(17.0, poly.evaluate(2), DELTA);
    }

    @Test
    @DisplayName("evaluate polynomial at x = 0 should return a0")
    void evaluate_AtZero_ReturnsFirstCoefficient() {
        // P(x) = 5 + 3x + 2x^2
        double[] coefficients = {5.0, 3.0, 2.0};
        Polynomial poly = new Polynomial(2, coefficients);
        
        // P(0) = 5
        assertEquals(5.0, poly.evaluate(0), DELTA);
    }

    @Test
    @DisplayName("evaluate polynomial at x = 1 should return sum of coefficients")
    void evaluate_AtOne_ReturnsSumOfCoefficients() {
        // P(x) = 1 + 2x + 3x^2
        double[] coefficients = {1.0, 2.0, 3.0};
        Polynomial poly = new Polynomial(2, coefficients);
        
        // P(1) = 1 + 2 + 3 = 6
        assertEquals(6.0, poly.evaluate(1), DELTA);
    }

    @Test
    @DisplayName("evaluate polynomial with negative x should return correct value")
    void evaluate_NegativeX_ReturnsCorrectValue() {
        // P(x) = 2 + 3x + x^2
        double[] coefficients = {2.0, 3.0, 1.0};
        Polynomial poly = new Polynomial(2, coefficients);
        
        // P(-1) = 2 + 3*(-1) + (-1)^2 = 2 - 3 + 1 = 0
        assertEquals(0.0, poly.evaluate(-1), DELTA);
    }

    @Test
    @DisplayName("evaluate cubic polynomial should return correct value")
    void evaluate_CubicPolynomial_ReturnsCorrectValue() {
        // P(x) = 1 + x + x^2 + x^3
        double[] coefficients = {1.0, 1.0, 1.0, 1.0};
        Polynomial poly = new Polynomial(3, coefficients);
        
        // P(2) = 1 + 2 + 4 + 8 = 15
        assertEquals(15.0, poly.evaluate(2), DELTA);
    }

    @Test
    @DisplayName("evaluate polynomial with decimal coefficients")
    void evaluate_DecimalCoefficients_ReturnsCorrectValue() {
        // P(x) = 0.5 + 1.5x
        double[] coefficients = {0.5, 1.5};
        Polynomial poly = new Polynomial(1, coefficients);
        
        // P(2) = 0.5 + 1.5*2 = 0.5 + 3 = 3.5
        assertEquals(3.5, poly.evaluate(2), DELTA);
    }

    // ==================== Horner's Method Tests ====================

    @Test
    @DisplayName("evaluateHorner should return same result as evaluate")
    void evaluateHorner_SameAsEvaluate() {
        double[] coefficients = {1.0, 2.0, 3.0, 4.0};
        Polynomial poly = new Polynomial(3, coefficients);
        
        double x = 2.5;
        assertEquals(poly.evaluate(x), poly.evaluateHorner(x), DELTA);
    }

    // ==================== Parameterized Tests ====================

    @ParameterizedTest
    @DisplayName("evaluate polynomial P(x) = 1 + 2x at various x values")
    @CsvSource({
        "0, 1.0",
        "1, 3.0",
        "2, 5.0",
        "-1, -1.0",
        "5, 11.0"
    })
    void evaluate_LinearPolynomial_VariousX(double x, double expected) {
        // P(x) = 1 + 2x
        double[] coefficients = {1.0, 2.0};
        Polynomial poly = new Polynomial(1, coefficients);
        
        assertEquals(expected, poly.evaluate(x), DELTA);
    }

    @ParameterizedTest
    @DisplayName("evaluate polynomial P(x) = x^2 at various x values")
    @CsvSource({
        "0, 0.0",
        "1, 1.0",
        "2, 4.0",
        "3, 9.0",
        "-2, 4.0",
        "-3, 9.0"
    })
    void evaluate_XSquared_VariousX(double x, double expected) {
        // P(x) = x^2 = 0 + 0x + 1x^2
        double[] coefficients = {0.0, 0.0, 1.0};
        Polynomial poly = new Polynomial(2, coefficients);
        
        assertEquals(expected, poly.evaluate(x), DELTA);
    }

    // ==================== Edge Cases ====================

    @Test
    @DisplayName("evaluate polynomial with all zero coefficients should return 0")
    void evaluate_AllZeroCoefficients_ReturnsZero() {
        double[] coefficients = {0.0, 0.0, 0.0};
        Polynomial poly = new Polynomial(2, coefficients);
        
        assertEquals(0.0, poly.evaluate(100), DELTA);
    }

    @Test
    @DisplayName("getCoefficients should return copy of array")
    void getCoefficients_ReturnsCopy() {
        double[] original = {1.0, 2.0, 3.0};
        Polynomial poly = new Polynomial(2, original);
        
        double[] retrieved = poly.getCoefficients();
        retrieved[0] = 999.0; // Modify the retrieved array
        
        // Original in polynomial should be unchanged
        assertEquals(1.0, poly.getCoefficient(0), DELTA);
    }
}
