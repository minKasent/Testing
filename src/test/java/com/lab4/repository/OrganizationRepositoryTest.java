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
 * Test Case IDs: TC041 - TC045
 */
@DataJpaTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrganizationRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Test
    @Order(1)
    @DisplayName("TC041: Save organization to database - Success")
    void tc041_saveOrganization_shouldPersistToDatabase() {
        Organization organization = Organization.builder()
                .orgName("Repository Test Org")
                .address("Test Address")
                .phone("0987654321")
                .email("repo@test.com")
                .build();

        Organization saved = organizationRepository.save(organization);
        entityManager.flush();
        entityManager.clear();

        Optional<Organization> found = organizationRepository.findById(saved.getOrgId());
        assertThat(found).isPresent();
        assertThat(found.get().getOrgName()).isEqualTo("Repository Test Org");
        assertThat(found.get().getCreatedDate()).isNotNull();
    }

    @Test
    @Order(2)
    @DisplayName("TC042: Check existsByOrgNameIgnoreCase - Exact match - Returns true")
    void tc042_existsByOrgNameIgnoreCase_exactMatch_shouldReturnTrue() {
        Organization organization = Organization.builder().orgName("Unique Org Name").build();
        entityManager.persistAndFlush(organization);
        entityManager.clear();

        boolean exists = organizationRepository.existsByOrgNameIgnoreCase("Unique Org Name");

        assertThat(exists).isTrue();
    }

    @Test
    @Order(3)
    @DisplayName("TC043: Check existsByOrgNameIgnoreCase - Different case - Returns true")
    void tc043_existsByOrgNameIgnoreCase_differentCase_shouldReturnTrue() {
        Organization organization = Organization.builder().orgName("Case Test Org").build();
        entityManager.persistAndFlush(organization);
        entityManager.clear();

        boolean existsLower = organizationRepository.existsByOrgNameIgnoreCase("case test org");
        boolean existsUpper = organizationRepository.existsByOrgNameIgnoreCase("CASE TEST ORG");
        boolean existsMixed = organizationRepository.existsByOrgNameIgnoreCase("CaSe TeSt OrG");

        assertThat(existsLower).isTrue();
        assertThat(existsUpper).isTrue();
        assertThat(existsMixed).isTrue();
    }

    @Test
    @Order(4)
    @DisplayName("TC044: Check existsByOrgNameIgnoreCase - Non-existent name - Returns false")
    void tc044_existsByOrgNameIgnoreCase_nonExistent_shouldReturnFalse() {
        boolean exists = organizationRepository.existsByOrgNameIgnoreCase("Non Existent Org");

        assertThat(exists).isFalse();
    }

    @Test
    @Order(5)
    @DisplayName("TC045: Find organization by name ignore case - Success")
    void tc045_findByOrgNameIgnoreCase_shouldReturnOrganization() {
        Organization organization = Organization.builder()
                .orgName("Find By Name Org")
                .address("Address")
                .build();
        entityManager.persistAndFlush(organization);
        entityManager.clear();

        Optional<Organization> found = organizationRepository.findByOrgNameIgnoreCase("find by name org");

        assertThat(found).isPresent();
        assertThat(found.get().getOrgName()).isEqualTo("Find By Name Org");
    }
}
