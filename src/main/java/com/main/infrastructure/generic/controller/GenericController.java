package com.main.infrastructure.generic.controller;

import com.main.infrastructure.generic.service.GenericService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public abstract class GenericController<RQ, RS> {

    protected abstract GenericService<RQ, RS> getService();

    @PostMapping
    public RS create(@RequestBody RQ request) {
        return getService().create(request);
    }

    @PutMapping("/{id}")
    public RS update(@PathVariable UUID id, @RequestBody RQ request) {
        return getService().update(id, request);
    }

    @GetMapping("/{id}")
    public RS findById(@PathVariable UUID id) {
        return getService().findById(id);
    }

    @GetMapping
    public List<RS> findAll() {
        return getService().findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        getService().delete(id);
    }
}
