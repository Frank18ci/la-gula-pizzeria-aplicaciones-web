DROP DATABASE IF EXISTS db_pizzeria_gula_customer_service;
CREATE DATABASE db_pizzeria_gula_customer_service;
USE db_pizzeria_gula_customer_service;

-- Tabla de clientes
CREATE TABLE IF NOT EXISTS customers (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL UNIQUE,
  loyalty_points INT NOT NULL DEFAULT 0,
  birth_date TIMESTAMP NULL,
  notes VARCHAR(255) NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabla de direcciones
CREATE TABLE IF NOT EXISTS addresses (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  customer_id BIGINT NOT NULL,
  label VARCHAR(50) NULL, -- Ej: Casa, Trabajo
  street VARCHAR(150) NOT NULL,
  external_number VARCHAR(30) NULL,
  internal_number VARCHAR(30) NULL,
  neighborhood VARCHAR(100) NULL,
  city VARCHAR(100) NOT NULL,
  state VARCHAR(100) NULL,
  zip_code VARCHAR(20) NULL,
  country VARCHAR(2) NOT NULL DEFAULT 'PE',
  reference VARCHAR(255) NULL,
  is_default BOOLEAN NOT NULL DEFAULT FALSE,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX idx_addresses_customer (customer_id),
  CONSTRAINT fk_addresses_customer FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO customers (user_id, loyalty_points, birth_date, notes) VALUES
(4, 150, '1990-05-15 00:00:00', 'Cliente frecuente'),
(2, 50, '1985-10-20 00:00:00', 'Cliente nuevo'),
(3, 200, '1978-03-30 00:00:00', 'Cliente VIP'),
(1, 0, NULL, 'Administrador del sistema'),
(5, 50, '1985-10-20 00:00:00', 'Cliente nuevo');

INSERT INTO addresses (customer_id, label, street, external_number, internal_number, neighborhood, city, state, zip_code, country, reference, is_default) VALUES
(1, 'Casa', 'Av. Siempre Viva 123', '123', 'Apt 1', 'Springfield', 'Springfield', 'IL', '62701', 'US', 'Cerca del parque', TRUE),
(1, 'Trabajo', 'Calle Falsa 456', '456', NULL, 'Springfield', 'Springfield', 'IL', '62702', 'US', NULL, FALSE),
(2, 'Casa', 'Av. Principal 789', '789', NULL, 'Metropolis', 'Metropolis', 'NY', '10001', 'US', NULL, TRUE),
(3, 'Casa', 'Calle Secundaria 101', '101', NULL, 'Gotham', 'Gotham', 'NJ', '07097', 'US', NULL, TRUE);
