package com.lab;

/**
 * Radix class for converting decimal numbers to different bases.
 * Exercise 3 - Radix conversion implementation.
 * 
 * Supports conversion to bases from 2 to 16.
 */
public class Radix {

    // Characters used for representing digits in bases up to 16
    private static final char[] DIGITS = {
        '0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };

    /**
     * Converts a decimal number to a string representation in the specified base.
     *
     * @param number the decimal number to convert (must be >= 0)
     * @param radix  the target base (must be between 2 and 16 inclusive)
     * @return string representation of the number in the specified base
     * @throws IllegalArgumentException if number < 0 with message "Incorrect Value"
     * @throws IllegalArgumentException if radix < 2 or radix > 16 with message "Invalid Radix"
     */
    public static String convert(int number, int radix) {
        // Validate number
        if (number < 0) {
            throw new IllegalArgumentException("Incorrect Value");
        }
        
        // Validate radix
        if (radix < 2 || radix > 16) {
            throw new IllegalArgumentException("Invalid Radix");
        }
        
        // Handle zero as special case
        if (number == 0) {
            return "0";
        }
        
        StringBuilder result = new StringBuilder();
        int temp = number;
        
        while (temp > 0) {
            int remainder = temp % radix;
            result.insert(0, DIGITS[remainder]);
            temp = temp / radix;
        }
        
        return result.toString();
    }

    /**
     * Converts a decimal number to binary (base 2).
     *
     * @param number the decimal number to convert (must be >= 0)
     * @return binary string representation
     * @throws IllegalArgumentException if number < 0
     */
    public static String toBinary(int number) {
        return convert(number, 2);
    }

    /**
     * Converts a decimal number to octal (base 8).
     *
     * @param number the decimal number to convert (must be >= 0)
     * @return octal string representation
     * @throws IllegalArgumentException if number < 0
     */
    public static String toOctal(int number) {
        return convert(number, 8);
    }

    /**
     * Converts a decimal number to hexadecimal (base 16).
     *
     * @param number the decimal number to convert (must be >= 0)
     * @return hexadecimal string representation
     * @throws IllegalArgumentException if number < 0
     */
    public static String toHexadecimal(int number) {
        return convert(number, 16);
    }

    /**
     * Converts a string representation in a given base back to decimal.
     *
     * @param str   the string representation of the number
     * @param radix the base of the input string (must be between 2 and 16)
     * @return the decimal value
     * @throws IllegalArgumentException if radix is invalid or string contains invalid characters
     */
    public static int toDecimal(String str, int radix) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Incorrect Value");
        }
        
        if (radix < 2 || radix > 16) {
            throw new IllegalArgumentException("Invalid Radix");
        }
        
        int result = 0;
        String upperStr = str.toUpperCase();
        
        for (int i = 0; i < upperStr.length(); i++) {
            char c = upperStr.charAt(i);
            int digit = getDigitValue(c);
            
            if (digit < 0 || digit >= radix) {
                throw new IllegalArgumentException("Incorrect Value");
            }
            
            result = result * radix + digit;
        }
        
        return result;
    }

    /**
     * Gets the numeric value of a digit character.
     *
     * @param c the character
     * @return the numeric value (0-15) or -1 if invalid
     */
    private static int getDigitValue(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        } else if (c >= 'A' && c <= 'F') {
            return c - 'A' + 10;
        } else if (c >= 'a' && c <= 'f') {
            return c - 'a' + 10;
        }
        return -1;
    }
}
