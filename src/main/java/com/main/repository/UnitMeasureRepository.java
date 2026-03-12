package com.main.repository;

import com.main.infrastructure.generic.repository.GenericRepository;
import com.main.model.entity.UnitMeasureEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnitMeasureRepository extends GenericRepository<UnitMeasureEntity> {

    List<UnitMeasureEntity> findByDeletedAtIsNullOrderByLabel();

    Optional<UnitMeasureEntity> findByKeyAndDeletedAtIsNull(String key);

    boolean existsByKeyAndDeletedAtIsNull(String key);
}
