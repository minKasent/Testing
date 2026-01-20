package com.lab4.service;

import com.lab4.dto.OrganizationDTO;
import com.lab4.entity.Organization;
import com.lab4.exception.OrganizationNameExistsException;
import com.lab4.exception.OrganizationNotFoundException;
import com.lab4.repository.OrganizationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit tests for OrganizationService
 * Test Case IDs: TC001 - TC015
 */
@ExtendWith(MockitoExtension.class)
class OrganizationServiceTest {

    @Mock
    private OrganizationRepository organizationRepository;

    @InjectMocks
    private OrganizationService organizationService;

    private OrganizationDTO validDTO;
    private Organization savedOrganization;

    @BeforeEach
    void setUp() {
        validDTO = OrganizationDTO.builder()
                .orgName("Test Organization")
                .address("123 Test Street")
                .phone("0123456789")
                .email("test@example.com")
                .build();

        savedOrganization = Organization.builder()
                .orgId(1)
                .orgName("Test Organization")
                .address("123 Test Street")
                .phone("0123456789")
                .email("test@example.com")
                .createdDate(LocalDateTime.now())
                .build();
    }

    // ========================================
    // TC001 - TC005: Create Organization Tests
    // ========================================

    @Nested
    @DisplayName("TC001-TC005: Create Organization Tests")
    class CreateOrganizationTests {

        @Test
        @DisplayName("TC001: Create organization with valid data - Success")
        void tc001_createOrganization_withValidData_shouldSucceed() {
            // Arrange
            when(organizationRepository.existsByOrgNameIgnoreCase(anyString())).thenReturn(false);
            when(organizationRepository.save(any(Organization.class))).thenReturn(savedOrganization);

            // Act
            Organization result = organizationService.createOrganization(validDTO);

            // Assert
            assertThat(result).isNotNull();
            assertThat(result.getOrgId()).isEqualTo(1);
            assertThat(result.getOrgName()).isEqualTo("Test Organization");
            assertThat(result.getEmail()).isEqualTo("test@example.com");
            
            verify(organizationRepository, times(1)).existsByOrgNameIgnoreCase("Test Organization");
            verify(organizationRepository, times(1)).save(any(Organization.class));
        }

        @Test
        @DisplayName("TC002: Create organization with duplicate name - Fail")
        void tc002_createOrganization_withDuplicateName_shouldThrowException() {
            // Arrange
            when(organizationRepository.existsByOrgNameIgnoreCase(anyString())).thenReturn(true);

            // Act & Assert
            assertThatThrownBy(() -> organizationService.createOrganization(validDTO))
                    .isInstanceOf(OrganizationNameExistsException.class)
                    .hasMessageContaining("already exists");

            verify(organizationRepository, never()).save(any(Organization.class));
        }

        @Test
        @DisplayName("TC003: Create organization with duplicate name (case-insensitive) - Fail")
        void tc003_createOrganization_withDuplicateNameCaseInsensitive_shouldThrowException() {
            // Arrange
            validDTO.setOrgName("TEST ORGANIZATION"); // Different case
            when(organizationRepository.existsByOrgNameIgnoreCase("TEST ORGANIZATION")).thenReturn(true);

            // Act & Assert
            assertThatThrownBy(() -> organizationService.createOrganization(validDTO))
                    .isInstanceOf(OrganizationNameExistsException.class);

            verify(organizationRepository, never()).save(any(Organization.class));
        }

        @Test
        @DisplayName("TC004: Create organization with only required fields - Success")
        void tc004_createOrganization_withOnlyRequiredFields_shouldSucceed() {
            // Arrange
            OrganizationDTO minimalDTO = OrganizationDTO.builder()
                    .orgName("Minimal Org")
                    .build();
            
            Organization minimalSaved = Organization.builder()
                    .orgId(2)
                    .orgName("Minimal Org")
                    .createdDate(LocalDateTime.now())
                    .build();

            when(organizationRepository.existsByOrgNameIgnoreCase(anyString())).thenReturn(false);
            when(organizationRepository.save(any(Organization.class))).thenReturn(minimalSaved);

            // Act
            Organization result = organizationService.createOrganization(minimalDTO);

            // Assert
            assertThat(result).isNotNull();
            assertThat(result.getOrgName()).isEqualTo("Minimal Org");
            assertThat(result.getAddress()).isNull();
            assertThat(result.getPhone()).isNull();
            assertThat(result.getEmail()).isNull();
        }

        @Test
        @DisplayName("TC005: Create organization with whitespace trimming - Success")
        void tc005_createOrganization_withWhitespaceTrimming_shouldTrimValues() {
            // Arrange
            OrganizationDTO dtoWithSpaces = OrganizationDTO.builder()
                    .orgName("  Trimmed Org  ")
                    .address("  Address  ")
                    .phone("  0123456789  ")
                    .email("  test@test.com  ")
                    .build();

            Organization trimmedSaved = Organization.builder()
                    .orgId(3)
                    .orgName("Trimmed Org")
                    .address("Address")
                    .phone("0123456789")
                    .email("test@test.com")
                    .createdDate(LocalDateTime.now())
                    .build();

            when(organizationRepository.existsByOrgNameIgnoreCase("Trimmed Org")).thenReturn(false);
            when(organizationRepository.save(any(Organization.class))).thenReturn(trimmedSaved);

            // Act
            Organization result = organizationService.createOrganization(dtoWithSpaces);

            // Assert
            assertThat(result.getOrgName()).isEqualTo("Trimmed Org");
        }
    }

    // ========================================
    // TC006 - TC008: Get Organization Tests
    // ========================================

    @Nested
    @DisplayName("TC006-TC008: Get Organization Tests")
    class GetOrganizationTests {

        @Test
        @DisplayName("TC006: Get organization by valid ID - Success")
        void tc006_getOrganizationById_withValidId_shouldReturnOrganization() {
            // Arrange
            when(organizationRepository.findById(1)).thenReturn(Optional.of(savedOrganization));

            // Act
            Organization result = organizationService.getOrganizationById(1);

            // Assert
            assertThat(result).isNotNull();
            assertThat(result.getOrgId()).isEqualTo(1);
            assertThat(result.getOrgName()).isEqualTo("Test Organization");
        }

        @Test
        @DisplayName("TC007: Get organization by invalid ID - Fail")
        void tc007_getOrganizationById_withInvalidId_shouldThrowException() {
            // Arrange
            when(organizationRepository.findById(999)).thenReturn(Optional.empty());

            // Act & Assert
            assertThatThrownBy(() -> organizationService.getOrganizationById(999))
                    .isInstanceOf(OrganizationNotFoundException.class)
                    .hasMessageContaining("999");
        }

        @Test
        @DisplayName("TC008: Get all organizations - Success")
        void tc008_getAllOrganizations_shouldReturnList() {
            // Arrange
            Organization org2 = Organization.builder()
                    .orgId(2)
                    .orgName("Another Org")
                    .createdDate(LocalDateTime.now())
                    .build();

            when(organizationRepository.findAll()).thenReturn(Arrays.asList(savedOrganization, org2));

            // Act
            List<Organization> result = organizationService.getAllOrganizations();

            // Assert
            assertThat(result).hasSize(2);
            assertThat(result.get(0).getOrgName()).isEqualTo("Test Organization");
            assertThat(result.get(1).getOrgName()).isEqualTo("Another Org");
        }
    }

    // ========================================
    // TC009 - TC011: Check Name Exists Tests
    // ========================================

    @Nested
    @DisplayName("TC009-TC011: Check Organization Name Exists Tests")
    class CheckNameExistsTests {

        @Test
        @DisplayName("TC009: Check existing organization name - Returns true")
        void tc009_isOrgNameExists_withExistingName_shouldReturnTrue() {
            // Arrange
            when(organizationRepository.existsByOrgNameIgnoreCase("Existing Org")).thenReturn(true);

            // Act
            boolean result = organizationService.isOrgNameExists("Existing Org");

            // Assert
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("TC010: Check non-existing organization name - Returns false")
        void tc010_isOrgNameExists_withNonExistingName_shouldReturnFalse() {
            // Arrange
            when(organizationRepository.existsByOrgNameIgnoreCase("New Org")).thenReturn(false);

            // Act
            boolean result = organizationService.isOrgNameExists("New Org");

            // Assert
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("TC011: Check organization name with whitespace - Trims before check")
        void tc011_isOrgNameExists_withWhitespace_shouldTrimAndCheck() {
            // Arrange
            when(organizationRepository.existsByOrgNameIgnoreCase("Trimmed Org")).thenReturn(true);

            // Act
            boolean result = organizationService.isOrgNameExists("  Trimmed Org  ");

            // Assert
            assertThat(result).isTrue();
            verify(organizationRepository).existsByOrgNameIgnoreCase("Trimmed Org");
        }
    }

    // ========================================
    // TC012: DTO Conversion Test
    // ========================================

    @Test
    @DisplayName("TC012: Convert Organization entity to DTO")
    void tc012_toDTO_shouldConvertEntityToDTO() {
        // Act
        OrganizationDTO result = organizationService.toDTO(savedOrganization);

        // Assert
        assertThat(result.getOrgId()).isEqualTo(1);
        assertThat(result.getOrgName()).isEqualTo("Test Organization");
        assertThat(result.getAddress()).isEqualTo("123 Test Street");
        assertThat(result.getPhone()).isEqualTo("0123456789");
        assertThat(result.getEmail()).isEqualTo("test@example.com");
    }
}
