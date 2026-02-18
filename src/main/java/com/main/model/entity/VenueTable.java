package com.main.model.entity;

import com.main.infrastructure.generic.model.entity.GenericEntity;
import com.main.model.enums.TableStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "venue_tables")
@Getter
@Setter
public class VenueTable extends GenericEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name = "table_number", nullable = false)
    private Integer tableNumber;

    @Column(name = "capacity")
    private Integer capacity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TableStatus status = TableStatus.FREE;

    @Column(name = "is_active", nullable = false)
    private boolean active = true;

    public boolean isAvailable() {
        return TableStatus.FREE.equals(this.status) && this.active;
    }

    public boolean isOccupied() {
        return TableStatus.OCCUPIED.equals(this.status);
    }
}