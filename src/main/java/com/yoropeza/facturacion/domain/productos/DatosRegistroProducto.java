package com.yoropeza.facturacion.domain.productos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroProducto(@NotBlank String nombre,
                                    @NotNull double precio,
                                    @NotNull
                                    Long categoria_id) {
}
