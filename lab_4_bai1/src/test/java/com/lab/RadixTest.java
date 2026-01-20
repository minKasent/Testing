package com.lab;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Radix class using JUnit 5.
 * Exercise 3 - Testing Radix conversion.
 */
class RadixTest {

    // ==================== Negative Number Validation Tests ====================

    @Test
    @DisplayName("convert should throw IllegalArgumentException when number < 0")
    void convert_NegativeNumber_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Radix.convert(-1, 2);
        });
        
        assertEquals("Incorrect Value", exception.getMessage());
    }

    @Test
    @DisplayName("convert should throw IllegalArgumentException with message 'Incorrect Value' for negative number")
    void convert_NegativeNumberMinus5_ThrowsExceptionWithCorrectMessage() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Radix.convert(-5, 10);
        });
        
        assertEquals("Incorrect Value", exception.getMessage());
    }

    @Test
    @DisplayName("convert should throw IllegalArgumentException for large negative number")
    void convert_LargeNegativeNumber_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Radix.convert(-100, 16);
        });
        
        assertEquals("Incorrect Value", exception.getMessage());
    }

    // ==================== Invalid Radix Validation Tests ====================

    @Test
    @DisplayName("convert should throw IllegalArgumentException when radix < 2")
    void convert_RadixLessThanTwo_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Radix.convert(10, 1);
        });
        
        assertEquals("Invalid Radix", exception.getMessage());
    }

    @Test
    @DisplayName("convert should throw IllegalArgumentException when radix = 0")
    void convert_RadixZero_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Radix.convert(10, 0);
        });
        
        assertEquals("Invalid Radix", exception.getMessage());
    }

    @Test
    @DisplayName("convert should throw IllegalArgumentException when radix > 16")
    void convert_RadixGreaterThanSixteen_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Radix.convert(10, 17);
        });
        
        assertEquals("Invalid Radix", exception.getMessage());
    }

    @Test
    @DisplayName("convert should throw IllegalArgumentException when radix = 20")
    void convert_RadixTwenty_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Radix.convert(100, 20);
        });
        
        assertEquals("Invalid Radix", exception.getMessage());
    }

    @Test
    @DisplayName("convert should throw IllegalArgumentException for negative radix")
    void convert_NegativeRadix_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Radix.convert(10, -2);
        });
        
        assertEquals("Invalid Radix", exception.getMessage());
    }

    // ==================== Base 2 (Binary) Conversion Tests ====================

    @Test
    @DisplayName("convert 0 to binary should return '0'")
    void convert_ZeroToBinary_ReturnsZero() {
        assertEquals("0", Radix.convert(0, 2));
    }

    @Test
    @DisplayName("convert 1 to binary should return '1'")
    void convert_OneToBinary_ReturnsOne() {
        assertEquals("1", Radix.convert(1, 2));
    }

    @Test
    @DisplayName("convert 2 to binary should return '10'")
    void convert_TwoToBinary_ReturnsTen() {
        assertEquals("10", Radix.convert(2, 2));
    }

    @Test
    @DisplayName("convert 5 to binary should return '101'")
    void convert_FiveToBinary_ReturnsOneZeroOne() {
        assertEquals("101", Radix.convert(5, 2));
    }

    @Test
    @DisplayName("convert 8 to binary should return '1000'")
    void convert_EightToBinary_ReturnsOneZeroZeroZero() {
        assertEquals("1000", Radix.convert(8, 2));
    }

    @Test
    @DisplayName("convert 10 to binary should return '1010'")
    void convert_TenToBinary_ReturnsOneTenTen() {
        assertEquals("1010", Radix.convert(10, 2));
    }

    @Test
    @DisplayName("convert 255 to binary should return '11111111'")
    void convert_TwoFiftyFiveToBinary_ReturnsAllOnes() {
        assertEquals("11111111", Radix.convert(255, 2));
    }

    // ==================== Base 8 (Octal) Conversion Tests ====================

    @Test
    @DisplayName("convert 0 to octal should return '0'")
    void convert_ZeroToOctal_ReturnsZero() {
        assertEquals("0", Radix.convert(0, 8));
    }

    @Test
    @DisplayName("convert 7 to octal should return '7'")
    void convert_SevenToOctal_ReturnsSeven() {
        assertEquals("7", Radix.convert(7, 8));
    }

    @Test
    @DisplayName("convert 8 to octal should return '10'")
    void convert_EightToOctal_ReturnsTen() {
        assertEquals("10", Radix.convert(8, 8));
    }

    @Test
    @DisplayName("convert 64 to octal should return '100'")
    void convert_SixtyFourToOctal_ReturnsOneHundred() {
        assertEquals("100", Radix.convert(64, 8));
    }

    @Test
    @DisplayName("convert 100 to octal should return '144'")
    void convert_OneHundredToOctal_ReturnsOneFourFour() {
        assertEquals("144", Radix.convert(100, 8));
    }

    @Test
    @DisplayName("convert 255 to octal should return '377'")
    void convert_TwoFiftyFiveToOctal_ReturnsThreeSevenSeven() {
        assertEquals("377", Radix.convert(255, 8));
    }

    // ==================== Base 16 (Hexadecimal) Conversion Tests ====================

    @Test
    @DisplayName("convert 0 to hexadecimal should return '0'")
    void convert_ZeroToHex_ReturnsZero() {
        assertEquals("0", Radix.convert(0, 16));
    }

    @Test
    @DisplayName("convert 9 to hexadecimal should return '9'")
    void convert_NineToHex_ReturnsNine() {
        assertEquals("9", Radix.convert(9, 16));
    }

    @Test
    @DisplayName("convert 10 to hexadecimal should return 'A'")
    void convert_TenToHex_ReturnsA() {
        assertEquals("A", Radix.convert(10, 16));
    }

    @Test
    @DisplayName("convert 15 to hexadecimal should return 'F'")
    void convert_FifteenToHex_ReturnsF() {
        assertEquals("F", Radix.convert(15, 16));
    }

    @Test
    @DisplayName("convert 16 to hexadecimal should return '10'")
    void convert_SixteenToHex_ReturnsTen() {
        assertEquals("10", Radix.convert(16, 16));
    }

    @Test
    @DisplayName("convert 255 to hexadecimal should return 'FF'")
    void convert_TwoFiftyFiveToHex_ReturnsFF() {
        assertEquals("FF", Radix.convert(255, 16));
    }

    @Test
    @DisplayName("convert 256 to hexadecimal should return '100'")
    void convert_TwoFiftySixToHex_ReturnsOneHundred() {
        assertEquals("100", Radix.convert(256, 16));
    }

    @Test
    @DisplayName("convert 2748 to hexadecimal should return 'ABC'")
    void convert_TwoSevenFourEightToHex_ReturnsABC() {
        assertEquals("ABC", Radix.convert(2748, 16));
    }

    @Test
    @DisplayName("convert 65535 to hexadecimal should return 'FFFF'")
    void convert_MaxUnsignedShortToHex_ReturnsFFFF() {
        assertEquals("FFFF", Radix.convert(65535, 16));
    }

    // ==================== Convenience Method Tests ====================

    @Test
    @DisplayName("toBinary should return same result as convert with radix 2")
    void toBinary_SameAsConvertBase2() {
        assertEquals(Radix.convert(42, 2), Radix.toBinary(42));
    }

    @Test
    @DisplayName("toOctal should return same result as convert with radix 8")
    void toOctal_SameAsConvertBase8() {
        assertEquals(Radix.convert(42, 8), Radix.toOctal(42));
    }

    @Test
    @DisplayName("toHexadecimal should return same result as convert with radix 16")
    void toHexadecimal_SameAsConvertBase16() {
        assertEquals(Radix.convert(42, 16), Radix.toHexadecimal(42));
    }

    // ==================== Boundary Value Tests ====================

    @Test
    @DisplayName("convert with radix = 2 (minimum valid radix) should work")
    void convert_MinimumValidRadix_Works() {
        assertEquals("1010", Radix.convert(10, 2));
    }

    @Test
    @DisplayName("convert with radix = 16 (maximum valid radix) should work")
    void convert_MaximumValidRadix_Works() {
        assertEquals("A", Radix.convert(10, 16));
    }

    // ==================== Parameterized Tests ====================

    @ParameterizedTest
    @DisplayName("Binary conversion should return correct results")
    @CsvSource({
        "0, 0",
        "1, 1",
        "2, 10",
        "3, 11",
        "4, 100",
        "5, 101",
        "6, 110",
        "7, 111",
        "8, 1000",
        "15, 1111",
        "16, 10000"
    })
    void convert_ToBinary_ReturnsCorrectResult(int decimal, String expected) {
        assertEquals(expected, Radix.convert(decimal, 2));
    }

    @ParameterizedTest
    @DisplayName("Octal conversion should return correct results")
    @CsvSource({
        "0, 0",
        "1, 1",
        "7, 7",
        "8, 10",
        "15, 17",
        "16, 20",
        "63, 77",
        "64, 100"
    })
    void convert_ToOctal_ReturnsCorrectResult(int decimal, String expected) {
        assertEquals(expected, Radix.convert(decimal, 8));
    }

    @ParameterizedTest
    @DisplayName("Hexadecimal conversion should return correct results")
    @CsvSource({
        "0, 0",
        "9, 9",
        "10, A",
        "11, B",
        "12, C",
        "13, D",
        "14, E",
        "15, F",
        "16, 10",
        "31, 1F",
        "255, FF",
        "256, 100"
    })
    void convert_ToHexadecimal_ReturnsCorrectResult(int decimal, String expected) {
        assertEquals(expected, Radix.convert(decimal, 16));
    }

    // ==================== toDecimal Tests ====================

    @Test
    @DisplayName("toDecimal should convert binary string to decimal")
    void toDecimal_Binary_ReturnsCorrectValue() {
        assertEquals(10, Radix.toDecimal("1010", 2));
    }

    @Test
    @DisplayName("toDecimal should convert octal string to decimal")
    void toDecimal_Octal_ReturnsCorrectValue() {
        assertEquals(64, Radix.toDecimal("100", 8));
    }

    @Test
    @DisplayName("toDecimal should convert hex string to decimal")
    void toDecimal_Hex_ReturnsCorrectValue() {
        assertEquals(255, Radix.toDecimal("FF", 16));
    }

    @Test
    @DisplayName("toDecimal should handle lowercase hex")
    void toDecimal_LowercaseHex_ReturnsCorrectValue() {
        assertEquals(255, Radix.toDecimal("ff", 16));
    }
}
