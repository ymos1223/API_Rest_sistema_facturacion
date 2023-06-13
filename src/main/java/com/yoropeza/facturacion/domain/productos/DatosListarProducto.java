package com.yoropeza.facturacion.domain.productos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yoropeza.facturacion.domain.categoria.Categoria;
import com.yoropeza.facturacion.domain.categoria.DatosRespuestaCategoria;

import java.time.LocalDateTime;

public record DatosListarProducto(Long id, String nombre, double precio,
                                  @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
                                  LocalDateTime createAt, DatosRespuestaCategoria categoria) {

    public DatosListarProducto(Producto producto) {
        this(producto.getId(), producto.getNombre(), producto.getPrecio(),producto.getCreateAt(),
                new DatosRespuestaCategoria(producto.getCategoria().getId(),
                        producto.getCategoria().getNombre()));
    }
}
