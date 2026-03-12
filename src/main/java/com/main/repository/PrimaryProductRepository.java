package com.main.repository;

import com.main.infrastructure.generic.repository.GenericRepository;
import com.main.model.entity.PrimaryProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PrimaryProductRepository extends GenericRepository<PrimaryProduct> {

    List<PrimaryProduct> findByCompany_Id(UUID companyId);
}
