package com.yoropeza.facturacion.domain.productos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yoropeza.facturacion.domain.categoria.Categoria;

import java.time.LocalDateTime;

public record DatosRespuestaProducto(Long id, String nombre, double precio,
                                     @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
                                     LocalDateTime createAt, Categoria categoria) {
}
