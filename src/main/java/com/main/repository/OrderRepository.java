package com.main.repository;

import com.main.infrastructure.generic.repository.GenericRepository;
import com.main.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends GenericRepository<Order> {

    List<Order> findByVenueTable_Id(UUID venueTableId);

    List<Order> findByCompany_Id(UUID companyId);

    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.venueTable LEFT JOIN FETCH o.company LEFT JOIN FETCH o.createdByUser WHERE o.company.id = :companyId")
    List<Order> findByCompany_IdWithVenueTable(@Param("companyId") UUID companyId);

    @Query(value = "SELECT DISTINCT o FROM Order o " +
            "LEFT JOIN o.venueTable vt " +
            "LEFT JOIN o.items i " +
            "LEFT JOIN i.product p " +
            "WHERE o.company.id = :companyId " +
            "AND (:tableSearch IS NULL OR :tableSearch = '' OR vt IS NULL OR CAST(vt.tableNumber AS string) LIKE CONCAT('%', :tableSearch, '%')) " +
            "AND (:customerSearch IS NULL OR :customerSearch = '' OR LOWER(COALESCE(o.customerName, '')) LIKE LOWER(CONCAT('%', :customerSearch, '%'))) " +
            "AND (:itemSearch IS NULL OR :itemSearch = '' OR (p IS NOT NULL AND LOWER(p.name) LIKE LOWER(CONCAT('%', :itemSearch, '%'))))",
            countQuery = "SELECT COUNT(DISTINCT o) FROM Order o " +
                    "LEFT JOIN o.venueTable vt " +
                    "LEFT JOIN o.items i " +
                    "LEFT JOIN i.product p " +
                    "WHERE o.company.id = :companyId " +
                    "AND (:tableSearch IS NULL OR :tableSearch = '' OR vt IS NULL OR CAST(vt.tableNumber AS string) LIKE CONCAT('%', :tableSearch, '%')) " +
                    "AND (:customerSearch IS NULL OR :customerSearch = '' OR LOWER(COALESCE(o.customerName, '')) LIKE LOWER(CONCAT('%', :customerSearch, '%'))) " +
                    "AND (:itemSearch IS NULL OR :itemSearch = '' OR (p IS NOT NULL AND LOWER(p.name) LIKE LOWER(CONCAT('%', :itemSearch, '%'))))")
    @EntityGraph(attributePaths = {"venueTable", "company", "createdByUser"})
    Page<Order> findByCompanyIdWithSearch(@Param("companyId") UUID companyId,
                                          @Param("tableSearch") String tableSearch,
                                          @Param("customerSearch") String customerSearch,
                                          @Param("itemSearch") String itemSearch,
                                          Pageable pageable);

    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.venueTable LEFT JOIN FETCH o.company LEFT JOIN FETCH o.createdByUser WHERE o.id = :id")
    Optional<Order> findByIdWithVenueTable(@Param("id") UUID id);
}
