package com.lab4.service;

import com.lab4.dto.OrganizationDTO;
import com.lab4.entity.Organization;
import com.lab4.exception.OrganizationNameExistsException;
import com.lab4.exception.OrganizationNotFoundException;
import com.lab4.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    /**
     * Create a new organization
     * @param dto Organization data
     * @return Created organization
     * @throws OrganizationNameExistsException if organization name already exists
     */
    @Transactional
    public Organization createOrganization(OrganizationDTO dto) {
        log.info("Creating organization: {}", dto.getOrgName());
        
        // Check if organization name already exists (case-insensitive)
        if (organizationRepository.existsByOrgNameIgnoreCase(dto.getOrgName().trim())) {
            log.warn("Organization name already exists: {}", dto.getOrgName());
            throw new OrganizationNameExistsException(dto.getOrgName());
        }
        
        Organization organization = Organization.builder()
                .orgName(dto.getOrgName().trim())
                .address(dto.getAddress() != null ? dto.getAddress().trim() : null)
                .phone(dto.getPhone() != null && !dto.getPhone().isBlank() ? dto.getPhone().trim() : null)
                .email(dto.getEmail() != null && !dto.getEmail().isBlank() ? dto.getEmail().trim().toLowerCase() : null)
                .build();
        
        Organization saved = organizationRepository.save(organization);
        log.info("Organization created successfully with ID: {}", saved.getOrgId());
        
        return saved;
    }

    /**
     * Get organization by ID
     * @param orgId Organization ID
     * @return Organization
     * @throws OrganizationNotFoundException if organization not found
     */
    @Transactional(readOnly = true)
    public Organization getOrganizationById(Integer orgId) {
        return organizationRepository.findById(orgId)
                .orElseThrow(() -> new OrganizationNotFoundException(orgId));
    }

    /**
     * Get all organizations
     * @return List of organizations
     */
    @Transactional(readOnly = true)
    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }

    /**
     * Check if organization name exists
     * @param orgName Organization name
     * @return true if exists, false otherwise
     */
    @Transactional(readOnly = true)
    public boolean isOrgNameExists(String orgName) {
        return organizationRepository.existsByOrgNameIgnoreCase(orgName.trim());
    }

    /**
     * Convert entity to DTO
     * @param organization Organization entity
     * @return OrganizationDTO
     */
    public OrganizationDTO toDTO(Organization organization) {
        return OrganizationDTO.builder()
                .orgId(organization.getOrgId())
                .orgName(organization.getOrgName())
                .address(organization.getAddress())
                .phone(organization.getPhone())
                .email(organization.getEmail())
                .build();
    }
}
