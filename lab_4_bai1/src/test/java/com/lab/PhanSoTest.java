package com.lab;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.InputMismatchException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for PhanSo (Fraction) class using JUnit 5.
 */
class PhanSoTest {

    private PhanSo fraction1;
    private PhanSo fraction2;

    @BeforeEach
    void setUp() {
        // Initialize test fractions before each test
        fraction1 = new PhanSo(1, 2);  // 1/2
        fraction2 = new PhanSo(2, 3);  // 2/3
    }

    // ==================== Constructor Tests ====================

    @Test
    @DisplayName("Constructor should create fraction with valid numerator and denominator")
    void constructor_ValidInput_CreatesFraction() {
        PhanSo fraction = new PhanSo(3, 4);
        
        assertEquals(3, fraction.getNumerator());
        assertEquals(4, fraction.getDenominator());
    }

    @Test
    @DisplayName("Constructor should throw InputMismatchException when denominator is zero")
    void constructor_DenominatorZero_ThrowsInputMismatchException() {
        assertThrows(InputMismatchException.class, () -> {
            new PhanSo(1, 0);
        });
    }

    @Test
    @DisplayName("Constructor should throw InputMismatchException with message when denominator is zero")
    void constructor_DenominatorZero_ThrowsExceptionWithMessage() {
        InputMismatchException exception = assertThrows(InputMismatchException.class, () -> {
            new PhanSo(5, 0);
        });
        
        assertEquals("Denominator cannot be zero", exception.getMessage());
    }

    @Test
    @DisplayName("Default constructor should create fraction 0/1")
    void defaultConstructor_CreatesZeroFraction() {
        PhanSo fraction = new PhanSo();
        
        assertEquals(0, fraction.getNumerator());
        assertEquals(1, fraction.getDenominator());
    }

    // ==================== Addition Tests ====================

    @Test
    @DisplayName("Adding 1/2 + 2/3 should equal 7/6")
    void add_OneHalfPlusTwoThirds_EqualsSevenSixths() {
        // Arrange
        PhanSo f1 = new PhanSo(1, 2);  // 1/2
        PhanSo f2 = new PhanSo(2, 3);  // 2/3

        // Act
        PhanSo result = f1.add(f2);

        // Assert - verify numerator and denominator separately
        // 1/2 + 2/3 = (1*3 + 2*2) / (2*3) = (3 + 4) / 6 = 7/6
        assertEquals(7, result.getNumerator());
        assertEquals(6, result.getDenominator());
    }

    @Test
    @DisplayName("Adding two fractions should return correct numerator")
    void add_TwoFractions_ReturnsCorrectNumerator() {
        PhanSo result = fraction1.add(fraction2);
        
        // 1/2 + 2/3 = 7/6
        assertEquals(7, result.getNumerator());
    }

    @Test
    @DisplayName("Adding two fractions should return correct denominator")
    void add_TwoFractions_ReturnsCorrectDenominator() {
        PhanSo result = fraction1.add(fraction2);
        
        // 1/2 + 2/3 = 7/6
        assertEquals(6, result.getDenominator());
    }

    @Test
    @DisplayName("Adding fraction with zero numerator should return same fraction")
    void add_WithZeroNumerator_ReturnsSameFraction() {
        PhanSo zero = new PhanSo(0, 1);
        PhanSo result = fraction1.add(zero);
        
        // 1/2 + 0/1 = (1*1 + 2*0) / (2*1) = 1/2
        assertEquals(1, result.getNumerator());
        assertEquals(2, result.getDenominator());
    }

    @Test
    @DisplayName("Adding negative fractions should work correctly")
    void add_NegativeFractions_ReturnsCorrectResult() {
        PhanSo negative = new PhanSo(-1, 2);  // -1/2
        PhanSo result = fraction1.add(negative);
        
        // 1/2 + (-1/2) = (1*2 + 2*(-1)) / (2*2) = 0/4
        assertEquals(0, result.getNumerator());
        assertEquals(4, result.getDenominator());
    }

    // ==================== Setter Tests ====================

    @Test
    @DisplayName("setDenominator should throw InputMismatchException when value is zero")
    void setDenominator_Zero_ThrowsInputMismatchException() {
        PhanSo fraction = new PhanSo(1, 2);
        
        assertThrows(InputMismatchException.class, () -> {
            fraction.setDenominator(0);
        });
    }

    // ==================== Subtraction Tests ====================

    @Test
    @DisplayName("Subtracting 2/3 - 1/2 should equal 1/6")
    void subtract_TwoThirdsMinusOneHalf_EqualsOneSixth() {
        PhanSo result = fraction2.subtract(fraction1);
        
        // 2/3 - 1/2 = (2*2 - 3*1) / (3*2) = (4 - 3) / 6 = 1/6
        assertEquals(1, result.getNumerator());
        assertEquals(6, result.getDenominator());
    }

    // ==================== Multiplication Tests ====================

    @Test
    @DisplayName("Multiplying 1/2 * 2/3 should equal 2/6")
    void multiply_OneHalfTimesTwoThirds_EqualsTwoSixths() {
        PhanSo result = fraction1.multiply(fraction2);
        
        // 1/2 * 2/3 = 2/6
        assertEquals(2, result.getNumerator());
        assertEquals(6, result.getDenominator());
    }

    // ==================== Division Tests ====================

    @Test
    @DisplayName("Dividing 1/2 by 2/3 should equal 3/4")
    void divide_OneHalfByTwoThirds_EqualsThreeFourths() {
        PhanSo result = fraction1.divide(fraction2);
        
        // (1/2) / (2/3) = (1*3) / (2*2) = 3/4
        assertEquals(3, result.getNumerator());
        assertEquals(4, result.getDenominator());
    }

    @Test
    @DisplayName("Dividing by fraction with zero numerator should throw exception")
    void divide_ByZeroNumerator_ThrowsException() {
        PhanSo zero = new PhanSo(0, 1);
        
        assertThrows(InputMismatchException.class, () -> {
            fraction1.divide(zero);
        });
    }

    // ==================== Simplify Tests ====================

    @Test
    @DisplayName("Simplifying 4/8 should equal 1/2")
    void simplify_FourEighths_EqualsOneHalf() {
        PhanSo fraction = new PhanSo(4, 8);
        PhanSo simplified = fraction.simplify();
        
        assertEquals(1, simplified.getNumerator());
        assertEquals(2, simplified.getDenominator());
    }

    @Test
    @DisplayName("Simplifying negative fraction should maintain sign correctly")
    void simplify_NegativeFraction_MaintainsSign() {
        PhanSo fraction = new PhanSo(-4, 8);
        PhanSo simplified = fraction.simplify();
        
        assertEquals(-1, simplified.getNumerator());
        assertEquals(2, simplified.getDenominator());
    }
}
