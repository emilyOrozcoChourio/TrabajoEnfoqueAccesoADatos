-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS contratos_andalucia
  CHARACTER SET utf8mb4 
  COLLATE utf8mb4_0900_ai_ci;

-- Usar esta base de datos
USE contratos_andalucia;

-- Crear la tabla donde voy a guardar los contratos
CREATE TABLE IF NOT EXISTS contratos (
  id INT AUTO_INCREMENT PRIMARY KEY,      -- id autoincremental
  expediente VARCHAR(100) NOT NULL,       -- número o código del expediente
  objeto VARCHAR(1000) NOT NULL,          -- descripción del contrato
  organismo VARCHAR(255) NOT NULL,        -- quién adjudica
  adjudicatario VARCHAR(255) NOT NULL,    -- empresa que recibe el contrato
  importe DECIMAL(15,2) NOT NULL,         -- importe del contrato
  fecha_adjudicacion DATE NOT NULL,       -- fecha en la que se adjudica
  tipo_contrato VARCHAR(100),             -- tipo de contrato (opcional)
  procedimiento VARCHAR(100),             -- procedimiento usado (opcional)
  codigo_cpv VARCHAR(20),                 -- código CPV (opcional)
  url_plataforma TEXT,                    -- enlace a la plataforma
  UNIQUE KEY uk_expediente (expediente)   -- evitar expedientes repetidos
);
