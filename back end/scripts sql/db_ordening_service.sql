DROP DATABASE IF EXISTS db_ordening_service;
CREATE DATABASE db_ordening_service;
USE db_ordening_service;

-- =======================
-- TABLA: orders
-- =======================
CREATE TABLE IF NOT EXISTS orders (
  id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  order_number VARCHAR(30) NOT NULL UNIQUE, -- legible para humanos
  customer_id BIGINT UNSIGNED NULL, -- eliminado FK ya que customers no existe
  address_id BIGINT UNSIGNED NULL,  -- eliminado FK ya que addresses no existe
  status VARCHAR(30) NOT NULL DEFAULT 'pending', -- antes ENUM
  delivery_method VARCHAR(30) NOT NULL,          -- antes ENUM
  notes VARCHAR(500) NULL,
  subtotal DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  tax DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  delivery_fee DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  discount_total DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  total DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  payment_status VARCHAR(30) NOT NULL DEFAULT 'unpaid', -- antes ENUM
  placed_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX idx_orders_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =======================
-- TABLA: order_items
-- =======================
CREATE TABLE IF NOT EXISTS order_items (
  id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  order_id BIGINT UNSIGNED NOT NULL,
  pizza_id BIGINT UNSIGNED NULL,      -- FK eliminada (tabla pizzas no existe)
  size_id BIGINT UNSIGNED NULL,       -- FK eliminada (tabla sizes no existe)
  dough_type_id BIGINT UNSIGNED NULL, -- FK eliminada (tabla dough_types no existe)
  quantity INT NOT NULL DEFAULT 1,
  unit_price DECIMAL(10,2) NOT NULL,
  line_total DECIMAL(10,2) NOT NULL,
  note VARCHAR(255) NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX idx_order_items_order (order_id),
  CONSTRAINT fk_order_items_order
    FOREIGN KEY (order_id) REFERENCES orders(id)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =======================
-- TABLA: order_item_toppings
-- =======================
CREATE TABLE IF NOT EXISTS order_item_toppings (
  id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  order_item_id BIGINT UNSIGNED NOT NULL,
  topping_id BIGINT UNSIGNED NULL, -- FK eliminada (tabla toppings no existe)
  action VARCHAR(30) NOT NULL,     -- antes ENUM
  quantity INT UNSIGNED NOT NULL DEFAULT 1, -- antes TINYINT
  price_delta DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY uk_item_topping_action (order_item_id, topping_id, action),
  CONSTRAINT fk_order_item_toppings_item
    FOREIGN KEY (order_item_id) REFERENCES order_items(id)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
