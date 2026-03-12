package com.main.repository;

import com.main.infrastructure.generic.repository.GenericRepository;
import com.main.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends GenericRepository<Order> {

    List<Order> findByVenueTable_Id(UUID venueTableId);

    List<Order> findByCompany_Id(UUID companyId);
}
