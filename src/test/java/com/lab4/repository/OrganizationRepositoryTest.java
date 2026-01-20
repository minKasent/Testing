package com.lab4.repository;

import com.lab4.entity.Organization;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Repository tests for OrganizationRepository
 * Test Case IDs: TC041 - TC045 (Database Operation Tests)
 */
@DataJpaTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrganizationRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrganizationRepository organizationRepository;

    // ========================================
    // TC041 - TC045: Repository Tests
    // ========================================

    @Test
    @Order(1)
    @DisplayName("TC041: Save organization to database - Success")
    void tc041_saveOrganization_shouldPersistToDatabase() {
        // Arrange
        Organization organization = Organization.builder()
                .orgName("Repository Test Org")
                .address("Test Address")
                .phone("0987654321")
                .email("repo@test.com")
                .build();

        // Act
        Organization saved = organizationRepository.save(organization);
        entityManager.flush();
        entityManager.clear();

        // Assert
        Optional<Organization> found = organizationRepository.findById(saved.getOrgId());
        assertThat(found).isPresent();
        assertThat(found.get().getOrgName()).isEqualTo("Repository Test Org");
        assertThat(found.get().getCreatedDate()).isNotNull();
    }

    @Test
    @Order(2)
    @DisplayName("TC042: Check existsByOrgNameIgnoreCase - Exact match - Returns true")
    void tc042_existsByOrgNameIgnoreCase_exactMatch_shouldReturnTrue() {
        // Arrange
        Organization organization = Organization.builder()
                .orgName("Unique Org Name")
                .build();
        entityManager.persistAndFlush(organization);
        entityManager.clear();

        // Act
        boolean exists = organizationRepository.existsByOrgNameIgnoreCase("Unique Org Name");

        // Assert
        assertThat(exists).isTrue();
    }

    @Test
    @Order(3)
    @DisplayName("TC043: Check existsByOrgNameIgnoreCase - Different case - Returns true")
    void tc043_existsByOrgNameIgnoreCase_differentCase_shouldReturnTrue() {
        // Arrange
        Organization organization = Organization.builder()
                .orgName("Case Test Org")
                .build();
        entityManager.persistAndFlush(organization);
        entityManager.clear();

        // Act
        boolean existsLower = organizationRepository.existsByOrgNameIgnoreCase("case test org");
        boolean existsUpper = organizationRepository.existsByOrgNameIgnoreCase("CASE TEST ORG");
        boolean existsMixed = organizationRepository.existsByOrgNameIgnoreCase("CaSe TeSt OrG");

        // Assert
        assertThat(existsLower).isTrue();
        assertThat(existsUpper).isTrue();
        assertThat(existsMixed).isTrue();
    }

    @Test
    @Order(4)
    @DisplayName("TC044: Check existsByOrgNameIgnoreCase - Non-existent name - Returns false")
    void tc044_existsByOrgNameIgnoreCase_nonExistent_shouldReturnFalse() {
        // Act
        boolean exists = organizationRepository.existsByOrgNameIgnoreCase("Non Existent Org");

        // Assert
        assertThat(exists).isFalse();
    }

    @Test
    @Order(5)
    @DisplayName("TC045: Find organization by name ignore case - Success")
    void tc045_findByOrgNameIgnoreCase_shouldReturnOrganization() {
        // Arrange
        Organization organization = Organization.builder()
                .orgName("Find By Name Org")
                .address("Address")
                .build();
        entityManager.persistAndFlush(organization);
        entityManager.clear();

        // Act
        Optional<Organization> found = organizationRepository.findByOrgNameIgnoreCase("find by name org");

        // Assert
        assertThat(found).isPresent();
        assertThat(found.get().getOrgName()).isEqualTo("Find By Name Org");
    }
}
