DROP DATABASE IF EXISTS db_catalog_service;
CREATE DATABASE db_catalog_service;
USE db_catalog_service;

-- =============================
-- TABLA: sizes
-- =============================
CREATE TABLE IF NOT EXISTS sizes (
  id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL UNIQUE,          -- Ej: Personal, Mediana, Familiar
  diameter_cm DECIMAL(5,2) NULL,             -- opcional
  price_multiplier DECIMAL(6,3) NOT NULL DEFAULT 1.000, -- multiplica precio base
  active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =============================
-- TABLA: dough_types
-- =============================
CREATE TABLE IF NOT EXISTS dough_types (
  id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL UNIQUE,          -- Ej: Tradicional, Delgada, Integral
  is_gluten_free BOOLEAN NOT NULL DEFAULT FALSE,
  extra_price DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =============================
-- TABLA: toppings
-- =============================
CREATE TABLE IF NOT EXISTS toppings (
  id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(80) NOT NULL UNIQUE,          -- Ej: Pepperoni, Champiñón
  category VARCHAR(50) NULL,                 -- Ej: Carne, Vegetal, Queso
  is_vegetarian BOOLEAN NOT NULL DEFAULT FALSE,
  base_price DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =============================
-- TABLA: pizzas
-- =============================
CREATE TABLE IF NOT EXISTS pizzas (
  id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(120) NOT NULL UNIQUE,         -- Ej: Margarita, Hawaiana
  description VARCHAR(500) NULL,
  base_price DECIMAL(10,2) NOT NULL,         -- precio base antes de tamaño/masa
  active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =============================
-- TABLA: pizza_default_toppings
-- =============================
CREATE TABLE IF NOT EXISTS pizza_default_toppings (
  pizza_id BIGINT UNSIGNED NOT NULL,
  topping_id BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (pizza_id, topping_id),
  CONSTRAINT fk_pizza_default_toppings_pizza
    FOREIGN KEY (pizza_id) REFERENCES pizzas(id)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_pizza_default_toppings_topping
    FOREIGN KEY (topping_id) REFERENCES toppings(id)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
