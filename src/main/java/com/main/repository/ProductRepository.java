package com.main.repository;

import com.main.infrastructure.generic.repository.GenericRepository;
import com.main.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends GenericRepository<Product> {

    List<Product> findByCompany_Id(UUID companyId);

    @Query("SELECT p FROM Product p JOIN FETCH p.primaryProduct WHERE p.company.id = :companyId")
    List<Product> findByCompany_IdWithPrimaryProduct(@Param("companyId") UUID companyId);

    @Query("SELECT p FROM Product p JOIN FETCH p.primaryProduct JOIN FETCH p.company WHERE p.id = :id")
    Optional<Product> findByIdWithPrimaryProduct(@Param("id") UUID id);
}
