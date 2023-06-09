CREATE TABLE facturas (
    id INT NOT NULL AUTO_INCREMENT,
    descripcion VARCHAR(100) NOT NULL,
     fecha_facturacion DATETIME NOT NULL,
   cliente_id INT NOT NULL,
    PRIMARY KEY (id),
     FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);