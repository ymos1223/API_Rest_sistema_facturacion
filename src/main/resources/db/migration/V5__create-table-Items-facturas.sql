CREATE TABLE items_facturas (
    id INT NOT NULL AUTO_INCREMENT,
    cantidad INTEGER NOT NULL,
    producto_id INT NOT NULL,
    factura_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (producto_id) REFERENCES productos(id),
    FOREIGN KEY (factura_id) REFERENCES facturas(id)
);