CREATE TABLE IF NOT EXISTS creditos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_cliente INT NOT NULL,
    saldo DOUBLE NOT NULL,
    estado VARCHAR(20) NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES clientes(id)
);

INSERT INTO creditos (id_cliente, saldo, estado)
VALUES 
(1, 250000, 'PENDIENTE'),
(2, 120000, 'PENDIENTE');

