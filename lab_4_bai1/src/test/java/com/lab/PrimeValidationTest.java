package com.lab;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Parameterized unit tests for PrimeValidation class using JUnit 5.
 */
class PrimeValidationTest {

    // ==================== Parameterized Tests ====================

    @ParameterizedTest
    @DisplayName("isPrime should return expected result for various inputs")
    @CsvSource({
        "1, false",   // 1 is not prime
        "2, true",    // 2 is prime (smallest prime)
        "4, false",   // 4 is not prime (2*2)
        "7, true",    // 7 is prime
        "16, false",  // 16 is not prime (2^4)
        "19, true"    // 19 is prime
    })
    void isPrime_VariousInputs_ReturnsExpectedResult(int number, boolean expected) {
        assertEquals(expected, PrimeValidation.isPrime(number));
    }

    // ==================== Individual Test Cases ====================

    @Test
    @DisplayName("isPrime(1) should return false")
    void isPrime_One_ReturnsFalse() {
        assertFalse(PrimeValidation.isPrime(1));
    }

    @Test
    @DisplayName("isPrime(2) should return true")
    void isPrime_Two_ReturnsTrue() {
        assertTrue(PrimeValidation.isPrime(2));
    }

    @Test
    @DisplayName("isPrime(4) should return false")
    void isPrime_Four_ReturnsFalse() {
        assertFalse(PrimeValidation.isPrime(4));
    }

    @Test
    @DisplayName("isPrime(7) should return true")
    void isPrime_Seven_ReturnsTrue() {
        assertTrue(PrimeValidation.isPrime(7));
    }

    @Test
    @DisplayName("isPrime(16) should return false")
    void isPrime_Sixteen_ReturnsFalse() {
        assertFalse(PrimeValidation.isPrime(16));
    }

    @Test
    @DisplayName("isPrime(19) should return true")
    void isPrime_Nineteen_ReturnsTrue() {
        assertTrue(PrimeValidation.isPrime(19));
    }

    // ==================== Additional Edge Cases ====================

    @Test
    @DisplayName("isPrime(0) should return false")
    void isPrime_Zero_ReturnsFalse() {
        assertFalse(PrimeValidation.isPrime(0));
    }

    @Test
    @DisplayName("isPrime(-5) should return false")
    void isPrime_NegativeNumber_ReturnsFalse() {
        assertFalse(PrimeValidation.isPrime(-5));
    }

    @Test
    @DisplayName("isPrime(3) should return true")
    void isPrime_Three_ReturnsTrue() {
        assertTrue(PrimeValidation.isPrime(3));
    }

    @Test
    @DisplayName("isPrime(9) should return false (9 = 3*3)")
    void isPrime_Nine_ReturnsFalse() {
        assertFalse(PrimeValidation.isPrime(9));
    }

    // ==================== Additional Parameterized Tests ====================

    @ParameterizedTest
    @DisplayName("isPrime should correctly identify composite numbers")
    @CsvSource({
        "4, false",
        "6, false",
        "8, false",
        "9, false",
        "10, false",
        "12, false",
        "15, false",
        "16, false",
        "18, false",
        "20, false"
    })
    void isPrime_CompositeNumbers_ReturnsFalse(int number, boolean expected) {
        assertEquals(expected, PrimeValidation.isPrime(number));
    }

    @ParameterizedTest
    @DisplayName("isPrime should correctly identify prime numbers")
    @CsvSource({
        "2, true",
        "3, true",
        "5, true",
        "7, true",
        "11, true",
        "13, true",
        "17, true",
        "19, true",
        "23, true",
        "29, true"
    })
    void isPrime_PrimeNumbers_ReturnsTrue(int number, boolean expected) {
        assertEquals(expected, PrimeValidation.isPrime(number));
    }
}
