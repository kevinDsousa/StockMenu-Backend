-- 1. Tabela de Empresas (Tenant Principal)
CREATE TABLE companies (
                           id UUID PRIMARY KEY,
                           trade_name VARCHAR(150) NOT NULL,
                           corporate_name VARCHAR(150),
                           cnpj VARCHAR(14) NOT NULL UNIQUE,
                           whatsapp VARCHAR(20),
                           active BOOLEAN NOT NULL DEFAULT TRUE,
                           created_at TIMESTAMP NOT NULL,
                           updated_at TIMESTAMP,
                           deleted_at TIMESTAMP
);

-- 2. Tabela de Assinaturas
CREATE TABLE subscriptions (
                               id UUID PRIMARY KEY,
                               company_id UUID NOT NULL REFERENCES companies(id),
                               start_date DATE NOT NULL,
                               end_date DATE NOT NULL,
                               status VARCHAR(50) NOT NULL,
                               amount_paid DECIMAL(10, 2) NOT NULL,
                               created_at TIMESTAMP NOT NULL,
                               updated_at TIMESTAMP,
                               deleted_at TIMESTAMP
);

-- 3. Tabela de Insumos (Estoque Bruto)
CREATE TABLE primary_products (
                                  id UUID PRIMARY KEY,
                                  company_id UUID NOT NULL REFERENCES companies(id),
                                  name VARCHAR(100) NOT NULL,
                                  current_stock DECIMAL(12, 3) NOT NULL DEFAULT 0,
                                  unit VARCHAR(20) NOT NULL,
                                  low_stock_alert DECIMAL(12, 3),
                                  expiration_date DATE NOT NULL,
                                  product_type VARCHAR(50),
                                  created_at TIMESTAMP NOT NULL,
                                  updated_at TIMESTAMP,
                                  deleted_at TIMESTAMP
);

-- 4. Tabela de Produtos (Venda/Cardápio)
CREATE TABLE products (
                          id UUID PRIMARY KEY,
                          company_id UUID NOT NULL REFERENCES companies(id),
                          primary_product_id UUID NOT NULL REFERENCES primary_products(id),
                          name VARCHAR(100) NOT NULL,
                          price DECIMAL(10, 2) NOT NULL,
                          sell_unit VARCHAR(20) NOT NULL,
                          is_fractional BOOLEAN NOT NULL DEFAULT FALSE,
                          custom_expiration_date DATE,
                          active BOOLEAN NOT NULL DEFAULT TRUE,
                          created_at TIMESTAMP NOT NULL,
                          updated_at TIMESTAMP,
                          deleted_at TIMESTAMP
);

-- 5. Tabela de Mesas
CREATE TABLE venue_tables (
                              id UUID PRIMARY KEY,
                              company_id UUID NOT NULL REFERENCES companies(id),
                              table_number INTEGER NOT NULL,
                              capacity INTEGER,
                              status VARCHAR(50) NOT NULL DEFAULT 'AVAILABLE',
                              is_active BOOLEAN NOT NULL DEFAULT TRUE,
                              created_at TIMESTAMP NOT NULL,
                              updated_at TIMESTAMP,
                              deleted_at TIMESTAMP
);

-- 6. Métodos de Pagamento
CREATE TABLE payment_methods (
                                 id UUID PRIMARY KEY,
                                 company_id UUID NOT NULL REFERENCES companies(id),
                                 name VARCHAR(50) NOT NULL,
                                 active BOOLEAN NOT NULL DEFAULT TRUE,
                                 allows_delivery BOOLEAN NOT NULL DEFAULT TRUE,
                                 is_online_payment BOOLEAN NOT NULL DEFAULT FALSE,
                                 created_at TIMESTAMP NOT NULL,
                                 updated_at TIMESTAMP,
                                 deleted_at TIMESTAMP
);

-- 7. Cabeçalho do Pedido
CREATE TABLE orders (
                        id UUID PRIMARY KEY,
                        company_id UUID NOT NULL REFERENCES companies(id),
                        table_id UUID REFERENCES venue_tables(id),
                        payment_method_id UUID REFERENCES payment_methods(id),
                        type VARCHAR(50) NOT NULL,
                        customer_name VARCHAR(100),
                        delivery_address VARCHAR(255),
                        total_amount DECIMAL(10, 2) NOT NULL DEFAULT 0,
                        invoiced BOOLEAN NOT NULL DEFAULT FALSE,
                        created_at TIMESTAMP NOT NULL,
                        updated_at TIMESTAMP,
                        deleted_at TIMESTAMP
);

-- 8. Itens do Pedido
CREATE TABLE order_items (
                             id UUID PRIMARY KEY,
                             order_id UUID NOT NULL REFERENCES orders(id),
                             product_id UUID NOT NULL REFERENCES products(id),
                             quantity DECIMAL(10, 3) NOT NULL,
                             unit_price DECIMAL(10, 2) NOT NULL,
                             total_price DECIMAL(10, 2) NOT NULL,
                             customer_name VARCHAR(50),
                             observation VARCHAR(255),
                             status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
                             created_at TIMESTAMP NOT NULL,
                             updated_at TIMESTAMP,
                             deleted_at TIMESTAMP
);

-- Índices para performance (essencial para Multi-tenancy)
CREATE INDEX idx_products_company ON products(company_id);
CREATE INDEX idx_orders_company ON orders(company_id);
CREATE INDEX idx_primary_products_company ON primary_products(company_id);