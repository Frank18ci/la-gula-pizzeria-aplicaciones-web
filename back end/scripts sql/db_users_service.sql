DROP DATABASE IF EXISTS db_pizzeria_gula_user_service;
CREATE DATABASE db_pizzeria_gula_user_service;
USE db_pizzeria_gula_user_service;

-- =========================================
-- TABLA: roles
-- =========================================
CREATE TABLE IF NOT EXISTS roles (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL UNIQUE, -- Ejemplo: 'admin', 'cliente'
  description VARCHAR(255) NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- =========================================
-- TABLA: users
-- =========================================
CREATE TABLE IF NOT EXISTS users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(255) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  full_name VARCHAR(150) NOT NULL,
  phone VARCHAR(30) NULL,
  status VARCHAR(20) NOT NULL DEFAULT 'active',
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX idx_users_status (status),
  INDEX idx_users_created_at (created_at)
);

-- =========================================
-- TABLA: user_roles (Relación muchos a muchos)
-- =========================================
CREATE TABLE IF NOT EXISTS user_roles (
  user_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL,
  assigned_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (user_id, role_id),
  CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- =========================================
-- DATOS INICIALES
-- =========================================
INSERT INTO roles (name, description) VALUES 
('admin', 'Administrador del sistema'),
('cliente', 'Cliente final');

INSERT INTO USERS (email, password_hash, full_name, phone, status) VALUES 
('admin@example.com', 'hashed_password_admin', 'Admin User', '1234567890', 'active'),
('client@example.com', 'hashed_password_client', 'Client User', '0987654321', 'active'),
('manager@example.com', 'hashed_password_manager', 'Manager User', '1122334455', 'active'),
('juan@gmail.com', 'hashed_password_juan', 'Juan Perez', '5551234567', 'active');

INSERT INTO user_roles (user_id, role_id) VALUES
(1, 1), 
(2, 2), 
(3, 1), 
(4, 2);
