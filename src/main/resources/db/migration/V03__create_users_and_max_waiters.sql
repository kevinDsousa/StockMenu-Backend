ALTER TABLE companies ADD COLUMN max_waiters INTEGER DEFAULT 10;

CREATE TABLE users (
    id UUID PRIMARY KEY,
    company_id UUID REFERENCES companies(id),
    email VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    name VARCHAR(150),
    role VARCHAR(50) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    CONSTRAINT uk_users_email_company UNIQUE (email, company_id)
);

CREATE INDEX idx_users_company ON users(company_id);
CREATE INDEX idx_users_email ON users(email);
