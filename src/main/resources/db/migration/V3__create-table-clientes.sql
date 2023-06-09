CREATE TABLE clientes (
    id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    dni INT UNIQUE NOT NULL,
    telefono VARCHAR(16) NOT NULL,
    create_at DATETIME NOT NULL,
    PRIMARY KEY (id)
);