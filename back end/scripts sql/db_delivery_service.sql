DROP DATABASE IF EXISTS db_pizzeria_gula_delivery_service;
CREATE DATABASE db_pizzeria_gula_delivery_service;
USE db_pizzeria_gula_delivery_service;

-- ================================================
-- TABLA: deliveries
-- ================================================
CREATE TABLE IF NOT EXISTS deliveries (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  order_id BIGINT NOT NULL UNIQUE, -- una entrega por orden
  address_id BIGINT NULL, -- puede ser NULL si pickup
  method VARCHAR(20) NOT NULL,  -- valores esperados: 'DELIVERY', 'PICKUP'
  status VARCHAR(30) NOT NULL DEFAULT 'pending', -- valores esperados: pending, assigned, en_route, etc.
  driver_name VARCHAR(120) NULL,
  driver_phone VARCHAR(30) NULL,
  delivered_at DATETIME NULL,
  instructions VARCHAR(300) NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO deliveries (order_id, address_id, method, status, driver_name, driver_phone, instructions) VALUES
(1, 1, 'DELIVERY', 'pending', NULL, NULL, 'Dejar en la puerta'),
(2, NULL, 'PICKUP', 'pending', NULL, NULL, 'Recoger en tienda'),
(3, 3, 'DELIVERY', 'assigned', 'Carlos Lopez', '5559876543', 'Llamar al llegar'),
(4, NULL, 'PICKUP', 'completed', NULL, NULL, 'Recoger en mostrador');