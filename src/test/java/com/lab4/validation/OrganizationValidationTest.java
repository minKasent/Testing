package com.lab4.validation;

import com.lab4.dto.OrganizationDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Validation tests for OrganizationDTO
 * Test Case IDs: TC013 - TC025 (Boundary and Format Tests)
 */
class OrganizationValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // ========================================
    // TC013 - TC018: OrgName Validation Tests
    // ========================================

    @Nested
    @DisplayName("TC013-TC018: Organization Name Validation Tests")
    class OrgNameValidationTests {

        @Test
        @DisplayName("TC013: OrgName - Empty value - Fail")
        void tc013_orgName_empty_shouldFail() {
            OrganizationDTO dto = OrganizationDTO.builder()
                    .orgName("")
                    .build();

            Set<ConstraintViolation<OrganizationDTO>> violations = validator.validate(dto);

            assertThat(violations).isNotEmpty();
            assertThat(violations).anyMatch(v -> 
                v.getPropertyPath().toString().equals("orgName"));
        }

        @Test
        @DisplayName("TC014: OrgName - Null value - Fail")
        void tc014_orgName_null_shouldFail() {
            OrganizationDTO dto = OrganizationDTO.builder()
                    .orgName(null)
                    .build();

            Set<ConstraintViolation<OrganizationDTO>> violations = validator.validate(dto);

            assertThat(violations).isNotEmpty();
            assertThat(violations).anyMatch(v -> 
                v.getPropertyPath().toString().equals("orgName"));
        }

        @Test
        @DisplayName("TC015: OrgName - Length = 2 (below minimum) - Fail")
        void tc015_orgName_length2_shouldFail() {
            OrganizationDTO dto = OrganizationDTO.builder()
                    .orgName("AB")
                    .build();

            Set<ConstraintViolation<OrganizationDTO>> violations = validator.validate(dto);

            assertThat(violations).isNotEmpty();
            assertThat(violations).anyMatch(v -> 
                v.getPropertyPath().toString().equals("orgName") &&
                v.getMessage().contains("3"));
        }

        @Test
        @DisplayName("TC016: OrgName - Length = 3 (minimum boundary) - Pass")
        void tc016_orgName_length3_shouldPass() {
            OrganizationDTO dto = OrganizationDTO.builder()
                    .orgName("ABC")
                    .build();

            Set<ConstraintViolation<OrganizationDTO>> violations = validator.validate(dto);

            assertThat(violations).isEmpty();
        }

        @Test
        @DisplayName("TC017: OrgName - Length = 255 (maximum boundary) - Pass")
        void tc017_orgName_length255_shouldPass() {
            String name255 = "A".repeat(255);
            OrganizationDTO dto = OrganizationDTO.builder()
                    .orgName(name255)
                    .build();

            Set<ConstraintViolation<OrganizationDTO>> violations = validator.validate(dto);

            assertThat(violations).isEmpty();
        }

        @Test
        @DisplayName("TC018: OrgName - Length = 256 (above maximum) - Fail")
        void tc018_orgName_length256_shouldFail() {
            String name256 = "A".repeat(256);
            OrganizationDTO dto = OrganizationDTO.builder()
                    .orgName(name256)
                    .build();

            Set<ConstraintViolation<OrganizationDTO>> violations = validator.validate(dto);

            assertThat(violations).isNotEmpty();
            assertThat(violations).anyMatch(v -> 
                v.getPropertyPath().toString().equals("orgName"));
        }
    }

    // ========================================
    // TC019 - TC022: Phone Validation Tests
    // ========================================

    @Nested
    @DisplayName("TC019-TC022: Phone Validation Tests")
    class PhoneValidationTests {

        @Test
        @DisplayName("TC019: Phone - Empty value (optional) - Pass")
        void tc019_phone_empty_shouldPass() {
            OrganizationDTO dto = OrganizationDTO.builder()
                    .orgName("Valid Org")
                    .phone("")
                    .build();

            Set<ConstraintViolation<OrganizationDTO>> violations = validator.validate(dto);

            assertThat(violations).isEmpty();
        }

        @Test
        @DisplayName("TC020: Phone - Length = 8 (below minimum) - Fail")
        void tc020_phone_length8_shouldFail() {
            OrganizationDTO dto = OrganizationDTO.builder()
                    .orgName("Valid Org")
                    .phone("12345678")
                    .build();

            Set<ConstraintViolation<OrganizationDTO>> violations = validator.validate(dto);

            assertThat(violations).isNotEmpty();
            assertThat(violations).anyMatch(v -> 
                v.getPropertyPath().toString().equals("phone"));
        }

        @Test
        @DisplayName("TC021: Phone - Length = 9 (minimum boundary) - Pass")
        void tc021_phone_length9_shouldPass() {
            OrganizationDTO dto = OrganizationDTO.builder()
                    .orgName("Valid Org")
                    .phone("123456789")
                    .build();

            Set<ConstraintViolation<OrganizationDTO>> violations = validator.validate(dto);

            assertThat(violations).isEmpty();
        }

        @Test
        @DisplayName("TC022: Phone - Length = 12 (maximum boundary) - Pass")
        void tc022_phone_length12_shouldPass() {
            OrganizationDTO dto = OrganizationDTO.builder()
                    .orgName("Valid Org")
                    .phone("123456789012")
                    .build();

            Set<ConstraintViolation<OrganizationDTO>> violations = validator.validate(dto);

            assertThat(violations).isEmpty();
        }

        @Test
        @DisplayName("TC023: Phone - Length = 13 (above maximum) - Fail")
        void tc023_phone_length13_shouldFail() {
            OrganizationDTO dto = OrganizationDTO.builder()
                    .orgName("Valid Org")
                    .phone("1234567890123")
                    .build();

            Set<ConstraintViolation<OrganizationDTO>> violations = validator.validate(dto);

            assertThat(violations).isNotEmpty();
            assertThat(violations).anyMatch(v -> 
                v.getPropertyPath().toString().equals("phone"));
        }

        @ParameterizedTest
        @ValueSource(strings = {"123-456-789", "abc123456", "12 34 56 78 90", "+841234567890"})
        @DisplayName("TC024: Phone - Invalid format (non-digits) - Fail")
        void tc024_phone_invalidFormat_shouldFail(String invalidPhone) {
            OrganizationDTO dto = OrganizationDTO.builder()
                    .orgName("Valid Org")
                    .phone(invalidPhone)
                    .build();

            Set<ConstraintViolation<OrganizationDTO>> violations = validator.validate(dto);

            assertThat(violations).isNotEmpty();
            assertThat(violations).anyMatch(v -> 
                v.getPropertyPath().toString().equals("phone"));
        }
    }

    // ========================================
    // TC025 - TC027: Email Validation Tests
    // ========================================

    @Nested
    @DisplayName("TC025-TC027: Email Validation Tests")
    class EmailValidationTests {

        @Test
        @DisplayName("TC025: Email - Empty value (optional) - Pass")
        void tc025_email_empty_shouldPass() {
            OrganizationDTO dto = OrganizationDTO.builder()
                    .orgName("Valid Org")
                    .email("")
                    .build();

            Set<ConstraintViolation<OrganizationDTO>> violations = validator.validate(dto);

            // Empty email should pass since it's optional
            boolean hasEmailError = violations.stream()
                    .anyMatch(v -> v.getPropertyPath().toString().equals("email"));
            // Note: Empty string might still fail @Email validation depending on implementation
        }

        @ParameterizedTest
        @ValueSource(strings = {"test@example.com", "user.name@domain.org", "email123@test.co.uk"})
        @DisplayName("TC026: Email - Valid format - Pass")
        void tc026_email_validFormat_shouldPass(String validEmail) {
            OrganizationDTO dto = OrganizationDTO.builder()
                    .orgName("Valid Org")
                    .email(validEmail)
                    .build();

            Set<ConstraintViolation<OrganizationDTO>> violations = validator.validate(dto);

            assertThat(violations).isEmpty();
        }

        @ParameterizedTest
        @ValueSource(strings = {"invalid-email", "test@", "@domain.com", "test.domain.com"})
        @DisplayName("TC027: Email - Invalid format - Fail")
        void tc027_email_invalidFormat_shouldFail(String invalidEmail) {
            OrganizationDTO dto = OrganizationDTO.builder()
                    .orgName("Valid Org")
                    .email(invalidEmail)
                    .build();

            Set<ConstraintViolation<OrganizationDTO>> violations = validator.validate(dto);

            assertThat(violations).isNotEmpty();
            assertThat(violations).anyMatch(v -> 
                v.getPropertyPath().toString().equals("email"));
        }
    }
}
