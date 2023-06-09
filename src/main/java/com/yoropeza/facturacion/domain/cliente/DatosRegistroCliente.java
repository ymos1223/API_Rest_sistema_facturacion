package com.yoropeza.facturacion.domain.cliente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroCliente(@NotBlank String nombre,
                                   @NotBlank String apellido,
                                   @NotNull int dni,
                                   @NotBlank String telefono) {
}
