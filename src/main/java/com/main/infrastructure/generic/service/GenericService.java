package com.main.infrastructure.generic.service;

import java.util.List;
import java.util.UUID;

public interface GenericService<RQ, RS> {

    RS create(RQ request);

    RS update(UUID id, RQ request);

    RS findById(UUID id);

    List<RS> findAll();

    void delete(UUID id);
}