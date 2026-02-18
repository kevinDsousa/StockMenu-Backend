package com.main.infrastructure.generic.model.mapper;

import java.util.List;

public interface GenericMapper<E, RQ, RS> {

    E toEntity(RQ request);

    RS toResponse(E entity);

    void updateEntity(RQ request, E entity);

    List<RS> toResponseList(List<E> entities);
}
