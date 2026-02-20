package com.main.infrastructure.generic.service;

import java.util.UUID;

public interface GenericSoftDeleteService<RS> {

    RS findByIdIncludingDeleted(UUID id);

    RS restore(UUID id);
}
