package com.main.model.entity;

import com.main.infrastructure.generic.model.entity.GenericEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "unit_measures")
@Getter
@Setter
public class UnitMeasureEntity extends GenericEntity {

    @Column(nullable = false, unique = true, length = 20)
    private String key;

    @Column(nullable = false, length = 100)
    private String label;

    @Column(nullable = false)
    private boolean active = true;
}
