package com.main.repository;

import com.main.infrastructure.generic.repository.GenericRepository;
import com.main.model.entity.VenueTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VenueTableRepository extends GenericRepository<VenueTable> {

    List<VenueTable> findByCompany_Id(UUID companyId);
}
