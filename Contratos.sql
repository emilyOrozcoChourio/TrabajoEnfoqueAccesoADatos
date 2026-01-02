CREATE DATABASE contratos_andalucia
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_0900_ai_ci;

USE contratos_andalucia;

CREATE TABLE contratos (
  id INT AUTO_INCREMENT PRIMARY KEY,
  numero_expediente VARCHAR(100) NOT NULL,
  adjudicatario VARCHAR(255),
  importe DECIMAL(15,2),
  fecha_adjudicacion DATE,
  organo_contratacion VARCHAR(255),
  tipo_contrato VARCHAR(100),
  objeto VARCHAR(1000),
  procedimiento VARCHAR(255),
  observaciones TEXT
);