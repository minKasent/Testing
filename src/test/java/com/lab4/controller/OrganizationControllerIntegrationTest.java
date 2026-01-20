package com.lab4.controller;

import com.lab4.entity.Organization;
import com.lab4.repository.OrganizationRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for OrganizationController
 * Test Case IDs: TC028 - TC040
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrganizationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrganizationRepository organizationRepository;

    @BeforeEach
    void setUp() {
        organizationRepository.deleteAll();
    }

    @Test
    @Order(1)
    @DisplayName("TC028: Access organization list page - Success")
    void tc028_accessOrganizationList_shouldReturnSuccess() throws Exception {
        mockMvc.perform(get("/organizations"))
                .andExpect(status().isOk())
                .andExpect(view().name("organization/list"))
                .andExpect(model().attributeExists("organizations"));
    }

    @Test
    @Order(2)
    @DisplayName("TC029: Access new organization form - Success")
    void tc029_accessNewOrganizationForm_shouldReturnSuccess() throws Exception {
        mockMvc.perform(get("/organizations/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("organization/form"))
                .andExpect(model().attributeExists("organizationDTO"));
    }

    @Test
    @Order(3)
    @DisplayName("TC030: Access home page redirects to organizations - Success")
    void tc030_accessHomePage_shouldRedirectToOrganizations() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/organizations"));
    }

    @Test
    @Order(4)
    @DisplayName("TC031: Save organization with valid data - Success")
    void tc031_saveOrganization_withValidData_shouldSucceed() throws Exception {
        mockMvc.perform(post("/organizations/save")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("orgName", "Test Organization")
                        .param("address", "123 Test Street")
                        .param("phone", "0123456789")
                        .param("email", "test@example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/organizations/success/*"));

        assertThat(organizationRepository.existsByOrgNameIgnoreCase("Test Organization")).isTrue();
    }

    @Test
    @Order(5)
    @DisplayName("TC032: Save organization with empty name - Fail")
    void tc032_saveOrganization_withEmptyName_shouldFail() throws Exception {
        mockMvc.perform(post("/organizations/save")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("orgName", "")
                        .param("address", "123 Test Street"))
                .andExpect(status().isOk())
                .andExpect(view().name("organization/form"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("organizationDTO", "orgName"));

        assertThat(organizationRepository.count()).isEqualTo(0);
    }

    @Test
    @Order(6)
    @DisplayName("TC033: Save organization with short name (2 chars) - Fail")
    void tc033_saveOrganization_withShortName_shouldFail() throws Exception {
        mockMvc.perform(post("/organizations/save")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("orgName", "AB"))
                .andExpect(status().isOk())
                .andExpect(view().name("organization/form"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("organizationDTO", "orgName"));
    }

    @Test
    @Order(7)
    @DisplayName("TC034: Save organization with invalid phone - Fail")
    void tc034_saveOrganization_withInvalidPhone_shouldFail() throws Exception {
        mockMvc.perform(post("/organizations/save")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("orgName", "Valid Organization")
                        .param("phone", "invalid"))
                .andExpect(status().isOk())
                .andExpect(view().name("organization/form"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("organizationDTO", "phone"));
    }

    @Test
    @Order(8)
    @DisplayName("TC035: Save organization with invalid email - Fail")
    void tc035_saveOrganization_withInvalidEmail_shouldFail() throws Exception {
        mockMvc.perform(post("/organizations/save")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("orgName", "Valid Organization")
                        .param("email", "invalid-email"))
                .andExpect(status().isOk())
                .andExpect(view().name("organization/form"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("organizationDTO", "email"));
    }

    @Test
    @Order(9)
    @DisplayName("TC036: Save organization with duplicate name - Fail")
    void tc036_saveOrganization_withDuplicateName_shouldFail() throws Exception {
        Organization existing = Organization.builder().orgName("Existing Organization").build();
        organizationRepository.save(existing);

        mockMvc.perform(post("/organizations/save")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("orgName", "Existing Organization"))
                .andExpect(status().isOk())
                .andExpect(view().name("organization/form"))
                .andExpect(model().hasErrors());

        assertThat(organizationRepository.count()).isEqualTo(1);
    }

    @Test
    @Order(10)
    @DisplayName("TC037: Save organization with duplicate name (different case) - Fail")
    void tc037_saveOrganization_withDuplicateNameDifferentCase_shouldFail() throws Exception {
        Organization existing = Organization.builder().orgName("Test Organization").build();
        organizationRepository.save(existing);

        mockMvc.perform(post("/organizations/save")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("orgName", "TEST ORGANIZATION"))
                .andExpect(status().isOk())
                .andExpect(view().name("organization/form"))
                .andExpect(model().hasErrors());
    }

    @Test
    @Order(11)
    @DisplayName("TC038: Access success page after save - Director button enabled")
    void tc038_accessSuccessPage_shouldShowDirectorButton() throws Exception {
        Organization saved = Organization.builder().orgName("New Organization").build();
        saved = organizationRepository.save(saved);

        mockMvc.perform(get("/organizations/success/" + saved.getOrgId()))
                .andExpect(status().isOk())
                .andExpect(view().name("organization/form"))
                .andExpect(model().attribute("savedOrgId", saved.getOrgId()))
                .andExpect(model().attribute("successMessage", "Save successfully"));
    }

    @Test
    @Order(12)
    @DisplayName("TC039: Access director page for saved organization - Success")
    void tc039_accessDirectorPage_forSavedOrganization_shouldSucceed() throws Exception {
        Organization saved = Organization.builder().orgName("Organization with Directors").build();
        saved = organizationRepository.save(saved);

        mockMvc.perform(get("/directors/organization/" + saved.getOrgId()))
                .andExpect(status().isOk())
                .andExpect(view().name("director/form"))
                .andExpect(model().attributeExists("organization"))
                .andExpect(model().attributeExists("directorDTO"))
                .andExpect(model().attributeExists("directors"));
    }

    @Test
    @Order(13)
    @DisplayName("TC040: Access director page for non-existent organization - Shows 404 page")
    void tc040_accessDirectorPage_forNonExistentOrganization_shouldShow404() throws Exception {
        mockMvc.perform(get("/directors/organization/9999"))
                .andExpect(status().isOk())
                .andExpect(view().name("error/404"))
                .andExpect(model().attributeExists("error"));
    }
}
