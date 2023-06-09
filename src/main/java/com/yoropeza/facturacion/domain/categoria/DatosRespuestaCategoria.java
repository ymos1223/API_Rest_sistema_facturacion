package com.yoropeza.facturacion.domain.categoria;

public record DatosRespuestaCategoria(Long id, String nombre) {

    public DatosRespuestaCategoria(Categoria categoria) {
        this(categoria.getId(), categoria.getNombre());
    }
}
