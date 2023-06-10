package com.yoropeza.facturacion.domain.cliente;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record DatosRespuestaCliente(Long id, String nombre,
                                    String apellido,
                                    int dni,
                                    String telefono,
                                    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
                                            LocalDateTime createAt) {

    public DatosRespuestaCliente(Cliente cliente) {
        this(cliente.getId(), cliente.getNombre(), cliente.getApellido(), cliente.getDni(), cliente.getTelefono(), cliente.getCreateAt());
    }
}
