package com.lab4.repository;

import com.lab4.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Integer> {

    /**
     * Find all directors by organization ID
     */
    List<Director> findByOrganization_OrgId(Integer orgId);
}
