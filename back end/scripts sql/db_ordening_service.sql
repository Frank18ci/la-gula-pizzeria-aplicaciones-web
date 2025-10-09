DROP DATABASE IF EXISTS db_pizzeria_gula_ordening_service;
CREATE DATABASE db_pizzeria_gula_ordening_service;
USE db_pizzeria_gula_ordening_service;

-- =======================
-- TABLA: orders
-- =======================
CREATE TABLE IF NOT EXISTS orders (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  order_number VARCHAR(30) NOT NULL UNIQUE,
  customer_id BIGINT NULL, 
  address_id BIGINT NULL, 
  status VARCHAR(30) NOT NULL DEFAULT 'pending',
  delivery_method VARCHAR(30) NOT NULL,
  notes VARCHAR(500) NULL,
  subtotal DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  tax DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  delivery_fee DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  discount_total DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  total DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  payment_status VARCHAR(30) NOT NULL DEFAULT 'unpaid',
  placed_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX idx_orders_status (status)
);

-- =======================
-- TABLA: order_items
-- =======================
CREATE TABLE IF NOT EXISTS order_items (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  order_id BIGINT NOT NULL,
  pizza_id BIGINT NULL,
  size_id BIGINT NULL,
  dough_type_id BIGINT NULL,
  quantity INT NOT NULL DEFAULT 1,
  unit_price DECIMAL(10,2) NOT NULL,
  line_total DECIMAL(10,2) NOT NULL,
  note VARCHAR(255) NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX idx_order_items_order (order_id),
  CONSTRAINT fk_order_items_order FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- =======================
-- TABLA: order_item_toppings
-- =======================
CREATE TABLE IF NOT EXISTS order_item_toppings (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  order_item_id BIGINT NOT NULL,
  topping_id BIGINT NULL, 
  action VARCHAR(30) NOT NULL,
  quantity INT NOT NULL DEFAULT 1,
  price_delta DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY uk_item_topping_action (order_item_id, topping_id, action),
  CONSTRAINT fk_order_item_toppings_item FOREIGN KEY (order_item_id) REFERENCES order_items(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Insertando datos

INSERT INTO orders (order_number, customer_id, address_id, status, delivery_method, notes, subtotal, tax, delivery_fee, discount_total, total, payment_status, placed_at) VALUES
('ORD-1001', 1, 1, 'pending', 'DELIVERY', 'Por favor entregar rápido', 20.00, 1.60, 3.00, 0.00, 24.60, 'unpaid', '2024-06-10 14:30:00'),
('ORD-1002', 2, NULL, 'completed', 'PICKUP', NULL, 15.00, 1.20, 0.00, 0.00, 16.20, 'paid', '2024-06-11 12:15:00'),
('ORD-1003', 1, 2, 'in_progress', 'DELIVERY', 'Sin cebolla por favor', 25.00, 2.00, 3.00, 5.00, 25.00, 'paid', '2024-06-12 18:45:00'),
('ORD-1004', NULL, NULL, 'canceled', 'PICKUP', 'Cancelar este pedido', 30.00, 2.40, 0.00, 0.00, 32.40, 'unpaid', '2024-06-13 20:00:00');

INSERT INTO order_items (order_id, pizza_id, size_id, dough_type_id, quantity, unit_price, line_total, note) VALUES
(1, 1, 2, 1, 1, 20.00, 20.00, 'Extra queso'),
(2, 2, 1, 2, 1, 15.00, 15.00, NULL),
(3, 3, 3, 1, 2, 12.50, 25.00, 'Sin cebolla'),
(4, 4, 2, 2, 1, 30.00, 30.00, 'Cancelar este pedido');

INSERT INTO order_item_toppings (order_item_id, topping_id, action, quantity, price_delta) VALUES
(1, 1, 'add', 1, 2.00),
(1, 2, 'remove', 1, 0.00),
(2, 3, 'add', 2, 3.00),
(3, 4, 'add', 1, 1.50),
(3, 5, 'remove', 1, 0.00);

