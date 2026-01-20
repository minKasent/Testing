package com.lab4.repository;

import com.lab4.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Integer> {

    /**
     * Check if organization name exists (case-insensitive)
     */
    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END FROM Organization o WHERE LOWER(o.orgName) = LOWER(:orgName)")
    boolean existsByOrgNameIgnoreCase(@Param("orgName") String orgName);

    /**
     * Find organization by name (case-insensitive)
     */
    @Query("SELECT o FROM Organization o WHERE LOWER(o.orgName) = LOWER(:orgName)")
    Optional<Organization> findByOrgNameIgnoreCase(@Param("orgName") String orgName);
}
