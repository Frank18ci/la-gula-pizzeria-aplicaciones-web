DROP DATABASE IF EXISTS db_pizzeria_gula_payment_service;
CREATE DATABASE db_pizzeria_gula_payment_service;
USE db_pizzeria_gula_payment_service;

-- ==============================================================
-- TABLA: orders 
-- ==============================================================
CREATE TABLE IF NOT EXISTS orders (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  customer_name VARCHAR(100) NOT NULL,
  total_amount DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ==============================================================
-- TABLAS DE APOYO
-- ==============================================================
-- Tabla de proveedores de pago
CREATE TABLE IF NOT EXISTS payment_providers (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL UNIQUE -- Ejemplo: 'cash', 'card', 'online'
);

-- Tabla de estados de pago
CREATE TABLE IF NOT EXISTS payment_statuses (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL UNIQUE -- Ejemplo: 'pending', 'authorized', 'captured', etc.
);

-- ==============================================================
-- TABLA PRINCIPAL: payments
-- ==============================================================
CREATE TABLE IF NOT EXISTS payments (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  order_id BIGINT NOT NULL,
  amount DECIMAL(10,2) NOT NULL,
  currency CHAR(3) NOT NULL DEFAULT 'USD',
  provider_id BIGINT NOT NULL,
  status_id BIGINT NOT NULL,
  external_id VARCHAR(100) NULL,
  processed_at DATETIME NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX idx_payments_order (order_id),
  INDEX idx_payments_status (status_id),
  CONSTRAINT fk_payments_order FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_payments_provider FOREIGN KEY (provider_id) REFERENCES payment_providers(id) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_payments_status FOREIGN KEY (status_id) REFERENCES payment_statuses(id) ON DELETE RESTRICT ON UPDATE CASCADE
);

-- ==============================================================
-- DATOS DE REFERENCIA
-- ==============================================================
INSERT INTO payment_providers (name) VALUES 
('cash'), 
('card'), 
('online');

INSERT INTO payment_statuses (name) VALUES 
('pending'), 
('authorized'), 
('captured'),
('failed'), 
('refunded'), 
('canceled');

INSERT INTO orders (customer_name, total_amount) VALUES
('Juan Perez', 45.00),
('Maria Gomez', 30.00),
('Carlos Ruiz', 60.00),
('Ana Torres', 25.00);

INSERT INTO payments (order_id, amount, currency, provider_id, status_id, external_id, processed_at) VALUES
(1, 45.00, 'USD', 2, 3, 'TXN123456', '2024-06-15 14:30:00'),
(2, 30.00, 'USD', 1, 3, NULL, '2024-06-15 15:00:00'),
(3, 60.00, 'USD', 3, 2, 'TXN654321', NULL),
(4, 25.00, 'USD', 2, 1, NULL, NULL);