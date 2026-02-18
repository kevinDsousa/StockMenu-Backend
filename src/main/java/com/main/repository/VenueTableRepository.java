package com.main.repository;

import com.main.infrastructure.generic.repository.GenericRepository;
import com.main.model.entity.VenueTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VenueTableRepository extends GenericRepository<VenueTable> {
}
