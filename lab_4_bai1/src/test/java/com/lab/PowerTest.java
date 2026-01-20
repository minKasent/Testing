package com.lab;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Power class using JUnit 5.
 * Exercise 1 - Testing Power function.
 */
class PowerTest {

    private static final double DELTA = 0.0001; // Tolerance for double comparison

    // ==================== Tests for n = 0 ====================

    @Test
    @DisplayName("power(x, 0) should return 1 for any positive x")
    void power_ExponentZero_PositiveBase_ReturnsOne() {
        assertEquals(1.0, Power.power(5.0, 0), DELTA);
    }

    @Test
    @DisplayName("power(x, 0) should return 1 for any negative x")
    void power_ExponentZero_NegativeBase_ReturnsOne() {
        assertEquals(1.0, Power.power(-3.0, 0), DELTA);
    }

    @Test
    @DisplayName("power(0, 0) should return 1 (mathematical convention)")
    void power_ZeroToZero_ReturnsOne() {
        assertEquals(1.0, Power.power(0.0, 0), DELTA);
    }

    @Test
    @DisplayName("power(1, 0) should return 1")
    void power_OneToZero_ReturnsOne() {
        assertEquals(1.0, Power.power(1.0, 0), DELTA);
    }

    // ==================== Tests for n > 0 (Positive Exponent) ====================

    @Test
    @DisplayName("power(2, 3) should return 8")
    void power_TwoToThree_ReturnsEight() {
        assertEquals(8.0, Power.power(2.0, 3), DELTA);
    }

    @Test
    @DisplayName("power(5, 2) should return 25")
    void power_FiveToTwo_ReturnsTwentyFive() {
        assertEquals(25.0, Power.power(5.0, 2), DELTA);
    }

    @Test
    @DisplayName("power(x, 1) should return x")
    void power_ExponentOne_ReturnsBase() {
        assertEquals(7.0, Power.power(7.0, 1), DELTA);
    }

    @Test
    @DisplayName("power(3, 4) should return 81")
    void power_ThreeToFour_ReturnsEightyOne() {
        assertEquals(81.0, Power.power(3.0, 4), DELTA);
    }

    @Test
    @DisplayName("power(-2, 3) should return -8 (odd exponent)")
    void power_NegativeBaseOddExponent_ReturnsNegative() {
        assertEquals(-8.0, Power.power(-2.0, 3), DELTA);
    }

    @Test
    @DisplayName("power(-2, 4) should return 16 (even exponent)")
    void power_NegativeBaseEvenExponent_ReturnsPositive() {
        assertEquals(16.0, Power.power(-2.0, 4), DELTA);
    }

    @Test
    @DisplayName("power(10, 3) should return 1000")
    void power_TenToThree_ReturnsOneThousand() {
        assertEquals(1000.0, Power.power(10.0, 3), DELTA);
    }

    // ==================== Tests for n < 0 (Negative Exponent) ====================

    @Test
    @DisplayName("power(2, -1) should return 0.5")
    void power_TwoToMinusOne_ReturnsHalf() {
        assertEquals(0.5, Power.power(2.0, -1), DELTA);
    }

    @Test
    @DisplayName("power(2, -2) should return 0.25")
    void power_TwoToMinusTwo_ReturnsQuarter() {
        assertEquals(0.25, Power.power(2.0, -2), DELTA);
    }

    @Test
    @DisplayName("power(5, -1) should return 0.2")
    void power_FiveToMinusOne_ReturnsFifth() {
        assertEquals(0.2, Power.power(5.0, -1), DELTA);
    }

    @Test
    @DisplayName("power(10, -2) should return 0.01")
    void power_TenToMinusTwo_ReturnsPointZeroOne() {
        assertEquals(0.01, Power.power(10.0, -2), DELTA);
    }

    @Test
    @DisplayName("power(4, -2) should return 0.0625")
    void power_FourToMinusTwo_ReturnsCorrectResult() {
        assertEquals(0.0625, Power.power(4.0, -2), DELTA);
    }

    // ==================== Boundary Value Tests ====================

    @Test
    @DisplayName("power(1, large n) should return 1")
    void power_BaseOne_LargeExponent_ReturnsOne() {
        assertEquals(1.0, Power.power(1.0, 1000), DELTA);
    }

    @Test
    @DisplayName("power(-1, even) should return 1")
    void power_MinusOne_EvenExponent_ReturnsOne() {
        assertEquals(1.0, Power.power(-1.0, 100), DELTA);
    }

    @Test
    @DisplayName("power(-1, odd) should return -1")
    void power_MinusOne_OddExponent_ReturnsMinusOne() {
        assertEquals(-1.0, Power.power(-1.0, 101), DELTA);
    }

    @Test
    @DisplayName("power(0, positive n) should return 0")
    void power_ZeroBase_PositiveExponent_ReturnsZero() {
        assertEquals(0.0, Power.power(0.0, 5), DELTA);
    }

    @Test
    @DisplayName("power with decimal base should work correctly")
    void power_DecimalBase_ReturnsCorrectResult() {
        // 0.5^2 = 0.25
        assertEquals(0.25, Power.power(0.5, 2), DELTA);
    }

    @Test
    @DisplayName("power with small decimal base and positive exponent")
    void power_SmallDecimal_PositiveExponent() {
        // 0.1^3 = 0.001
        assertEquals(0.001, Power.power(0.1, 3), DELTA);
    }

    // ==================== Parameterized Tests ====================

    @ParameterizedTest
    @DisplayName("power function should return correct results for various inputs")
    @CsvSource({
        "2.0, 0, 1.0",
        "3.0, 0, 1.0",
        "2.0, 1, 2.0",
        "2.0, 2, 4.0",
        "2.0, 3, 8.0",
        "2.0, 10, 1024.0",
        "3.0, 3, 27.0",
        "5.0, 2, 25.0",
        "10.0, 2, 100.0",
        "2.0, -1, 0.5",
        "2.0, -2, 0.25",
        "4.0, -1, 0.25",
        "-2.0, 2, 4.0",
        "-2.0, 3, -8.0"
    })
    void power_VariousInputs_ReturnsExpectedResult(double x, int n, double expected) {
        assertEquals(expected, Power.power(x, n), DELTA);
    }

    // ==================== Tests for Optimized Version ====================

    @Test
    @DisplayName("powerOptimized should return same result as power for positive exponent")
    void powerOptimized_PositiveExponent_SameAsNormal() {
        assertEquals(Power.power(2.0, 10), Power.powerOptimized(2.0, 10), DELTA);
    }

    @Test
    @DisplayName("powerOptimized should return same result as power for negative exponent")
    void powerOptimized_NegativeExponent_SameAsNormal() {
        assertEquals(Power.power(2.0, -3), Power.powerOptimized(2.0, -3), DELTA);
    }

    @Test
    @DisplayName("powerOptimized should return 1 for exponent 0")
    void powerOptimized_ExponentZero_ReturnsOne() {
        assertEquals(1.0, Power.powerOptimized(5.0, 0), DELTA);
    }
}
