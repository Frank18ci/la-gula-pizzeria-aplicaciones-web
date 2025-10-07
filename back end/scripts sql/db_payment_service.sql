DROP DATABASE IF EXISTS db_payment_service;
CREATE DATABASE db_payment_service;
use db_payment_service;

CREATE TABLE IF NOT EXISTS payments (
  id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  order_id BIGINT UNSIGNED NOT NULL,
  amount DECIMAL(10,2) NOT NULL,
  currency CHAR(3) NOT NULL DEFAULT 'USD',
  provider ENUM('cash','card','online') NOT NULL,
  status ENUM('pending','authorized','captured','failed','refunded','canceled') NOT NULL DEFAULT 'pending',
  external_id VARCHAR(100) NULL, -- referencia del gateway
  processed_at DATETIME NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX idx_payments_order (order_id),
  INDEX idx_payments_status (status),
  CONSTRAINT fk_payments_order
    FOREIGN KEY (order_id) REFERENCES orders(id)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
