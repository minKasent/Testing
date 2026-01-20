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
 * Test Case IDs: TC001 - TC012
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

    @Nested
    @DisplayName("TC001-TC005: Create Organization Tests")
    class CreateOrganizationTests {

        @Test
        @DisplayName("TC001: Create organization with valid data - Success")
        void tc001_createOrganization_withValidData_shouldSucceed() {
            when(organizationRepository.existsByOrgNameIgnoreCase(anyString())).thenReturn(false);
            when(organizationRepository.save(any(Organization.class))).thenReturn(savedOrganization);

            Organization result = organizationService.createOrganization(validDTO);

            assertThat(result).isNotNull();
            assertThat(result.getOrgId()).isEqualTo(1);
            assertThat(result.getOrgName()).isEqualTo("Test Organization");
            
            verify(organizationRepository, times(1)).existsByOrgNameIgnoreCase("Test Organization");
            verify(organizationRepository, times(1)).save(any(Organization.class));
        }

        @Test
        @DisplayName("TC002: Create organization with duplicate name - Fail")
        void tc002_createOrganization_withDuplicateName_shouldThrowException() {
            when(organizationRepository.existsByOrgNameIgnoreCase(anyString())).thenReturn(true);

            assertThatThrownBy(() -> organizationService.createOrganization(validDTO))
                    .isInstanceOf(OrganizationNameExistsException.class)
                    .hasMessageContaining("already exists");

            verify(organizationRepository, never()).save(any(Organization.class));
        }

        @Test
        @DisplayName("TC003: Create organization with duplicate name (case-insensitive) - Fail")
        void tc003_createOrganization_withDuplicateNameCaseInsensitive_shouldThrowException() {
            validDTO.setOrgName("TEST ORGANIZATION");
            when(organizationRepository.existsByOrgNameIgnoreCase("TEST ORGANIZATION")).thenReturn(true);

            assertThatThrownBy(() -> organizationService.createOrganization(validDTO))
                    .isInstanceOf(OrganizationNameExistsException.class);

            verify(organizationRepository, never()).save(any(Organization.class));
        }

        @Test
        @DisplayName("TC004: Create organization with only required fields - Success")
        void tc004_createOrganization_withOnlyRequiredFields_shouldSucceed() {
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

            Organization result = organizationService.createOrganization(minimalDTO);

            assertThat(result).isNotNull();
            assertThat(result.getOrgName()).isEqualTo("Minimal Org");
            assertThat(result.getAddress()).isNull();
        }

        @Test
        @DisplayName("TC005: Create organization with whitespace trimming - Success")
        void tc005_createOrganization_withWhitespaceTrimming_shouldTrimValues() {
            OrganizationDTO dtoWithSpaces = OrganizationDTO.builder()
                    .orgName("  Trimmed Org  ")
                    .build();

            Organization trimmedSaved = Organization.builder()
                    .orgId(3)
                    .orgName("Trimmed Org")
                    .createdDate(LocalDateTime.now())
                    .build();

            when(organizationRepository.existsByOrgNameIgnoreCase("Trimmed Org")).thenReturn(false);
            when(organizationRepository.save(any(Organization.class))).thenReturn(trimmedSaved);

            Organization result = organizationService.createOrganization(dtoWithSpaces);

            assertThat(result.getOrgName()).isEqualTo("Trimmed Org");
        }
    }

    @Nested
    @DisplayName("TC006-TC008: Get Organization Tests")
    class GetOrganizationTests {

        @Test
        @DisplayName("TC006: Get organization by valid ID - Success")
        void tc006_getOrganizationById_withValidId_shouldReturnOrganization() {
            when(organizationRepository.findById(1)).thenReturn(Optional.of(savedOrganization));

            Organization result = organizationService.getOrganizationById(1);

            assertThat(result).isNotNull();
            assertThat(result.getOrgId()).isEqualTo(1);
        }

        @Test
        @DisplayName("TC007: Get organization by invalid ID - Fail")
        void tc007_getOrganizationById_withInvalidId_shouldThrowException() {
            when(organizationRepository.findById(999)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> organizationService.getOrganizationById(999))
                    .isInstanceOf(OrganizationNotFoundException.class)
                    .hasMessageContaining("999");
        }

        @Test
        @DisplayName("TC008: Get all organizations - Success")
        void tc008_getAllOrganizations_shouldReturnList() {
            Organization org2 = Organization.builder()
                    .orgId(2)
                    .orgName("Another Org")
                    .createdDate(LocalDateTime.now())
                    .build();

            when(organizationRepository.findAll()).thenReturn(Arrays.asList(savedOrganization, org2));

            List<Organization> result = organizationService.getAllOrganizations();

            assertThat(result).hasSize(2);
        }
    }

    @Nested
    @DisplayName("TC009-TC011: Check Organization Name Exists Tests")
    class CheckNameExistsTests {

        @Test
        @DisplayName("TC009: Check existing organization name - Returns true")
        void tc009_isOrgNameExists_withExistingName_shouldReturnTrue() {
            when(organizationRepository.existsByOrgNameIgnoreCase("Existing Org")).thenReturn(true);

            boolean result = organizationService.isOrgNameExists("Existing Org");

            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("TC010: Check non-existing organization name - Returns false")
        void tc010_isOrgNameExists_withNonExistingName_shouldReturnFalse() {
            when(organizationRepository.existsByOrgNameIgnoreCase("New Org")).thenReturn(false);

            boolean result = organizationService.isOrgNameExists("New Org");

            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("TC011: Check organization name with whitespace - Trims before check")
        void tc011_isOrgNameExists_withWhitespace_shouldTrimAndCheck() {
            when(organizationRepository.existsByOrgNameIgnoreCase("Trimmed Org")).thenReturn(true);

            boolean result = organizationService.isOrgNameExists("  Trimmed Org  ");

            assertThat(result).isTrue();
            verify(organizationRepository).existsByOrgNameIgnoreCase("Trimmed Org");
        }
    }

    @Test
    @DisplayName("TC012: Convert Organization entity to DTO")
    void tc012_toDTO_shouldConvertEntityToDTO() {
        OrganizationDTO result = organizationService.toDTO(savedOrganization);

        assertThat(result.getOrgId()).isEqualTo(1);
        assertThat(result.getOrgName()).isEqualTo("Test Organization");
        assertThat(result.getAddress()).isEqualTo("123 Test Street");
        assertThat(result.getPhone()).isEqualTo("0123456789");
        assertThat(result.getEmail()).isEqualTo("test@example.com");
    }
}
