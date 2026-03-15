package com.main.repository;

import com.main.infrastructure.generic.repository.GenericRepository;
import com.main.model.entity.PrimaryProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PrimaryProductRepository extends GenericRepository<PrimaryProduct> {

    List<PrimaryProduct> findByCompany_Id(UUID companyId);

    @Query("SELECT p FROM PrimaryProduct p JOIN FETCH p.company WHERE p.id = :id")
    Optional<PrimaryProduct> findByIdWithCompany(@Param("id") UUID id);

    long countByUnitAndDeletedAtIsNull(String unit);
}
