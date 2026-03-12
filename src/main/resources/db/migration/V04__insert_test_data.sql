INSERT INTO companies (id, trade_name, corporate_name, cnpj, whatsapp, active, max_waiters, created_at, updated_at, deleted_at)
VALUES (
    'a0000000-0000-0000-0000-000000000001',
    'Empresa Teste',
    'Empresa Teste Ltda',
    '12345678000199',
    '11999999999',
    TRUE,
    10,
    NOW(),
    NOW(),
    NULL
);

INSERT INTO subscriptions (id, company_id, start_date, end_date, status, amount_paid, created_at, updated_at, deleted_at)
VALUES (
    'b0000000-0000-0000-0000-000000000001',
    'a0000000-0000-0000-0000-000000000001',
    CURRENT_DATE,
    CURRENT_DATE + INTERVAL '1 year',
    'ACTIVE',
    0,
    NOW(),
    NOW(),
    NULL
);

INSERT INTO users (id, company_id, email, password_hash, name, role, active, created_at, updated_at, deleted_at)
VALUES (
    'c0000000-0000-0000-0000-000000000001',
    'a0000000-0000-0000-0000-000000000001',
    'admin@teste.com',
    '$2b$12$KIXIDzJ2Rf2zXSpwURZrYe0djyyu3E4yFczVqTx3FWSMsvjrdIhJy',
    'Admin Teste',
    'COMPANY_ADMIN',
    TRUE,
    NOW(),
    NOW(),
    NULL
);
