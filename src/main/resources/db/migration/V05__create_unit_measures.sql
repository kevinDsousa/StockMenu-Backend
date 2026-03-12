CREATE TABLE unit_measures (
    id UUID PRIMARY KEY,
    key VARCHAR(20) NOT NULL UNIQUE,
    label VARCHAR(100) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP
);

INSERT INTO unit_measures (id, key, label, active, created_at, updated_at, deleted_at)
VALUES
    (gen_random_uuid(), 'KG', 'Kg', TRUE, NOW(), NOW(), NULL),
    (gen_random_uuid(), 'G', 'Grama', TRUE, NOW(), NOW(), NULL),
    (gen_random_uuid(), 'L', 'Litro', TRUE, NOW(), NOW(), NULL),
    (gen_random_uuid(), 'ML', 'Mililitro', TRUE, NOW(), NOW(), NULL),
    (gen_random_uuid(), 'UN', 'Unidade', TRUE, NOW(), NOW(), NULL);

CREATE INDEX idx_unit_measures_key ON unit_measures(key);
CREATE INDEX idx_unit_measures_deleted ON unit_measures(deleted_at);
