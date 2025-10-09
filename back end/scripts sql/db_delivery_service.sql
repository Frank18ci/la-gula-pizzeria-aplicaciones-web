DROP DATABASE IF EXISTS db_delivery_service;
CREATE DATABASE db_delivery_service;
USE db_delivery_service;

-- ================================================
-- TABLA: deliveries
-- ================================================
CREATE TABLE IF NOT EXISTS deliveries (
  id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  order_id BIGINT UNSIGNED NOT NULL UNIQUE, -- una entrega por orden
  address_id BIGINT UNSIGNED NULL, -- puede ser NULL si pickup

  -- Eliminamos ENUMs y los reemplazamos por VARCHAR
  method VARCHAR(20) NOT NULL,  -- valores esperados: 'DELIVERY', 'PICKUP'
  status VARCHAR(30) NOT NULL DEFAULT 'pending', -- valores esperados: pending, assigned, en_route, etc.

  driver_name VARCHAR(120) NULL,
  driver_phone VARCHAR(30) NULL,
  eta DATETIME NULL,
  delivered_at DATETIME NULL,
  instructions VARCHAR(300) NULL,

  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP

  -- Foreign keys (solo si existen las tablas)
  -- ,CONSTRAINT fk_deliveries_order
  --   FOREIGN KEY (order_id) REFERENCES orders(id)
  --   ON DELETE CASCADE ON UPDATE CASCADE,
  -- CONSTRAINT fk_deliveries_address
  --   FOREIGN KEY (address_id) REFERENCES addresses(id)
  --   ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
