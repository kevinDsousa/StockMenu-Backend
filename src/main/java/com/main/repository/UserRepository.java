package com.main.repository;

import com.main.infrastructure.generic.repository.GenericRepository;
import com.main.model.entity.User;
import com.main.model.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends GenericRepository<User> {

    List<User> findByCompany_Id(UUID companyId);

    @Query("SELECT COUNT(u) FROM User u WHERE u.company.id = :companyId AND u.role = :role AND u.deletedAt IS NULL AND u.active = true")
    long countActiveWaitersByCompany(@Param("companyId") UUID companyId, @Param("role") UserRole role);

    Optional<User> findByEmailAndCompany_Id(String email, UUID companyId);

    Optional<User> findFirstByEmail(String email);
}
