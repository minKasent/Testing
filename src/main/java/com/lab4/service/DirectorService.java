package com.lab4.service;

import com.lab4.dto.DirectorDTO;
import com.lab4.entity.Director;
import com.lab4.entity.Organization;
import com.lab4.exception.OrganizationNotFoundException;
import com.lab4.repository.DirectorRepository;
import com.lab4.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DirectorService {

    private final DirectorRepository directorRepository;
    private final OrganizationRepository organizationRepository;

    /**
     * Create a new director for an organization
     * @param dto Director data
     * @return Created director
     */
    @Transactional
    public Director createDirector(DirectorDTO dto) {
        log.info("Creating director: {} for organization ID: {}", dto.getDirectorName(), dto.getOrgId());
        
        Organization organization = organizationRepository.findById(dto.getOrgId())
                .orElseThrow(() -> new OrganizationNotFoundException(dto.getOrgId()));
        
        Director director = Director.builder()
                .directorName(dto.getDirectorName().trim())
                .email(dto.getEmail() != null && !dto.getEmail().isBlank() ? dto.getEmail().trim().toLowerCase() : null)
                .phone(dto.getPhone() != null && !dto.getPhone().isBlank() ? dto.getPhone().trim() : null)
                .organization(organization)
                .build();
        
        Director saved = directorRepository.save(director);
        log.info("Director created successfully with ID: {}", saved.getDirectorId());
        
        return saved;
    }

    /**
     * Get all directors for an organization
     * @param orgId Organization ID
     * @return List of directors
     */
    @Transactional(readOnly = true)
    public List<Director> getDirectorsByOrganization(Integer orgId) {
        return directorRepository.findByOrganization_OrgId(orgId);
    }

    /**
     * Convert entity to DTO
     * @param director Director entity
     * @return DirectorDTO
     */
    public DirectorDTO toDTO(Director director) {
        return DirectorDTO.builder()
                .directorId(director.getDirectorId())
                .directorName(director.getDirectorName())
                .email(director.getEmail())
                .phone(director.getPhone())
                .orgId(director.getOrganization().getOrgId())
                .build();
    }
}
