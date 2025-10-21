DROP DATABASE IF EXISTS db_pizzeria_gula_catalog_service;
CREATE DATABASE db_pizzeria_gula_catalog_service;
USE db_pizzeria_gula_catalog_service;

-- =============================
-- TABLA: sizes
-- =============================
CREATE TABLE IF NOT EXISTS sizes (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL UNIQUE,          -- Ej: Personal, Mediana, Familiar
  diameter_cm DECIMAL(5,2) NULL,
  price_multiplier DECIMAL(6,3) NOT NULL DEFAULT 1.000, -- multiplica precio base
  active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- =============================
-- TABLA: dough_types
-- =============================
CREATE TABLE IF NOT EXISTS dough_types (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL UNIQUE,          -- Ej: Tradicional, Delgada, Integral
  is_gluten_free BOOLEAN NOT NULL DEFAULT FALSE,
  extra_price DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- =============================
-- TABLA: toppings
-- =============================
CREATE TABLE IF NOT EXISTS toppings (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(80) NOT NULL UNIQUE,          -- Ej: Pepperoni, Champiñón
  category VARCHAR(50) NULL,                 -- Ej: Carne, Vegetal, Queso
  is_vegetarian BOOLEAN NOT NULL DEFAULT FALSE,
  base_price DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  image VARCHAR(255) NULL,                  -- URL o path de imagen
  active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- =============================
-- TABLA: pizzas
-- =============================
CREATE TABLE IF NOT EXISTS pizzas (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(120) NOT NULL UNIQUE,         -- Ej: Margarita, Hawaiana
  description VARCHAR(500) NULL,
  base_price DECIMAL(10,2) NOT NULL,         -- precio base antes de tamaño/masa
  image VARCHAR(255) NULL,                  -- URL o path de imagen
  active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- =============================
-- TABLA: pizza_default_toppings
-- =============================
CREATE TABLE IF NOT EXISTS pizza_default_toppings (
  pizza_id BIGINT NOT NULL,
  topping_id BIGINT NOT NULL,
  PRIMARY KEY (pizza_id, topping_id),
  CONSTRAINT fk_pizza_default_toppings_pizza FOREIGN KEY (pizza_id) REFERENCES pizzas(id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_pizza_default_toppings_topping FOREIGN KEY (topping_id) REFERENCES toppings(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- INSETANDO VALORES 

INSERT INTO sizes (name, diameter_cm, price_multiplier) VALUES 
('Personal', 20.00, 1.000),
('Mediana', 30.00, 1.500),
('Familiar', 40.00, 2.000);

INSERT INTO dough_types (name, is_gluten_free, extra_price) VALUES 
('Tradicional', FALSE, 0.00),
('Delgada', FALSE, 1.00),
('Integral', TRUE, 1.50);

INSERT INTO toppings (name, category, is_vegetarian, base_price) VALUES 
('Pepperoni', 'Carne', FALSE, 2.00),
('Champiñón', 'Vegetal', TRUE, 1.50),
('Cebolla', 'Vegetal', TRUE, 1.00),
('Jalapeño', 'Vegetal', TRUE, 1.50),
('Queso Extra', 'Queso', TRUE, 2.50);

INSERT INTO pizzas (name, description, base_price) VALUES 
('Margarita', 'Salsa de tomate, mozzarella y albahaca fresca', 8.00),
('Hawaiana', 'Salsa de tomate, mozzarella, jamón y piña', 10.00),
('Pepperoni', 'Salsa de tomate, mozzarella y pepperoni', 9.00),
('Vegetariana', 'Salsa de tomate, mozzarella y variedad de vegetales', 11.00);

INSERT INTO pizza_default_toppings (pizza_id, topping_id) VALUES 
(1, 5),  -- Margarita tiene Queso Extra
(2, 5),  -- Hawaiana tiene Queso Extra
(2, 3),  -- Hawaiana tiene Cebolla
(3, 1),  -- Pepperoni tiene Pepperoni
(3, 5),  -- Pepperoni tiene Queso Extra
(4, 2),  -- Vegetariana tiene Champiñón
(4, 3),  -- Vegetariana tiene Cebolla
(4, 4),  -- Vegetariana tiene Jalapeño
(4, 5);  -- Vegetariana tiene Queso Extra

