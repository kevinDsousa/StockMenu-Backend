package com.main.repository;

import com.main.infrastructure.generic.repository.GenericRepository;
import com.main.model.entity.Company;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompanyRepository extends GenericRepository<Company> {

    @Query("SELECT c FROM Company c LEFT JOIN FETCH c.subscriptions WHERE c.id = :id")
    Optional<Company> findByIdWithSubscriptions(@Param("id") UUID id);
}
