DROP DATABASE IF EXISTS db_ordening_service;
CREATE DATABASE db_ordening_service;
use db_ordening_service;

CREATE TABLE IF NOT EXISTS orders (
  id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  order_number VARCHAR(30) NOT NULL UNIQUE, -- legible para humanos
  customer_id BIGINT UNSIGNED NOT NULL,
  address_id BIGINT UNSIGNED NULL, -- requerido si delivery
  status ENUM('pending','confirmed','preparing','ready','delivered','canceled') NOT NULL DEFAULT 'pending',
  delivery_method ENUM('DELIVERY','PICKUP') NOT NULL,
  notes VARCHAR(500) NULL,
  subtotal DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  tax DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  delivery_fee DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  discount_total DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  total DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  payment_status ENUM('unpaid','paid','refunded','failed') NOT NULL DEFAULT 'unpaid',
  placed_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX idx_orders_customer (customer_id),
  INDEX idx_orders_status (status),
  CONSTRAINT fk_orders_customer
    FOREIGN KEY (customer_id) REFERENCES customers(id)
    ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_orders_address
    FOREIGN KEY (address_id) REFERENCES addresses(id)
    ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS order_items (
  id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  order_id BIGINT UNSIGNED NOT NULL,
  pizza_id BIGINT UNSIGNED NOT NULL,
  size_id BIGINT UNSIGNED NOT NULL,
  dough_type_id BIGINT UNSIGNED NOT NULL,
  quantity INT NOT NULL DEFAULT 1,
  unit_price DECIMAL(10,2) NOT NULL, -- precio por unidad (snapshot)
  line_total DECIMAL(10,2) NOT NULL, -- unit_price * quantity + extras
  note VARCHAR(255) NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX idx_order_items_order (order_id),
  CONSTRAINT fk_order_items_order
    FOREIGN KEY (order_id) REFERENCES orders(id)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_order_items_pizza
    FOREIGN KEY (pizza_id) REFERENCES pizzas(id)
    ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_order_items_size
    FOREIGN KEY (size_id) REFERENCES sizes(id)
    ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_order_items_dough
    FOREIGN KEY (dough_type_id) REFERENCES dough_types(id)
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS order_item_toppings (
  id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  order_item_id BIGINT UNSIGNED NOT NULL,
  topping_id BIGINT UNSIGNED NOT NULL,
  action ENUM('ADD','REMOVE','EXTRA') NOT NULL, -- permite quitar toppings por defecto o añadir extra
  quantity TINYINT UNSIGNED NOT NULL DEFAULT 1, -- para EXTRA/ADD (e.g., doble ingrediente)
  price_delta DECIMAL(10,2) NOT NULL DEFAULT 0.00, -- impacto al precio
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY uk_item_topping_action (order_item_id, topping_id, action),
  CONSTRAINT fk_order_item_toppings_item
    FOREIGN KEY (order_item_id) REFERENCES order_items(id)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_order_item_toppings_topping
    FOREIGN KEY (topping_id) REFERENCES toppings(id)
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
