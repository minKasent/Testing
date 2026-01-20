package com.lab;

/**
 * Utility class for prime number validation.
 */
public class PrimeValidation {

    /**
     * Checks if a number is prime.
     * A prime number is a natural number greater than 1 that has no positive 
     * divisors other than 1 and itself.
     *
     * @param number the number to check
     * @return true if the number is prime, false otherwise
     */
    public static boolean isPrime(int number) {
        // Numbers less than or equal to 1 are not prime
        if (number <= 1) {
            return false;
        }
        
        // 2 is the only even prime number
        if (number == 2) {
            return true;
        }
        
        // Even numbers greater than 2 are not prime
        if (number % 2 == 0) {
            return false;
        }
        
        // Check for factors from 3 to sqrt(number)
        for (int i = 3; i <= Math.sqrt(number); i += 2) {
            if (number % i == 0) {
                return false;
            }
        }
        
        return true;
    }
}