-- Si ya existe la base de datos, la eliminamos y creamos de nuevo
DROP DATABASE IF EXISTS db_payment_service;
CREATE DATABASE db_payment_service;
USE db_payment_service;

-- ==============================================================
-- TABLA: orders (necesaria para la FK de payments)
-- ==============================================================
-- Si aún no existe una tabla de pedidos, la creamos básica para mantener la integridad referencial.
CREATE TABLE IF NOT EXISTS orders (
  id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  customer_name VARCHAR(100) NOT NULL,
  total_amount DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==============================================================
-- TABLAS DE APOYO (reemplazo de ENUMs)
-- ==============================================================
-- Tabla de proveedores de pago
CREATE TABLE IF NOT EXISTS payment_providers (
  id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL UNIQUE -- Ejemplo: 'cash', 'card', 'online'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla de estados de pago
CREATE TABLE IF NOT EXISTS payment_statuses (
  id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL UNIQUE -- Ejemplo: 'pending', 'authorized', 'captured', etc.
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==============================================================
-- TABLA PRINCIPAL: payments
-- ==============================================================
CREATE TABLE IF NOT EXISTS payments (
  id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  order_id BIGINT UNSIGNED NOT NULL,
  amount DECIMAL(10,2) NOT NULL,
  currency CHAR(3) NOT NULL DEFAULT 'USD',
  provider_id BIGINT UNSIGNED NOT NULL,
  status_id BIGINT UNSIGNED NOT NULL,
  external_id VARCHAR(100) NULL,        -- referencia del gateway
  processed_at DATETIME NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  INDEX idx_payments_order (order_id),
  INDEX idx_payments_status (status_id),

  CONSTRAINT fk_payments_order
    FOREIGN KEY (order_id) REFERENCES orders(id)
    ON DELETE CASCADE ON UPDATE CASCADE,

  CONSTRAINT fk_payments_provider
    FOREIGN KEY (provider_id) REFERENCES payment_providers(id)
    ON DELETE RESTRICT ON UPDATE CASCADE,

  CONSTRAINT fk_payments_status
    FOREIGN KEY (status_id) REFERENCES payment_statuses(id)
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==============================================================
-- DATOS DE REFERENCIA
-- ==============================================================
INSERT INTO payment_providers (name) VALUES ('cash'), ('card'), ('online');
INSERT INTO payment_statuses (name) VALUES 
('pending'), ('authorized'), ('captured'),
('failed'), ('refunded'), ('canceled');
