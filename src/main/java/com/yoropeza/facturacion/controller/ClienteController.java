package com.yoropeza.facturacion.controller;

import com.yoropeza.facturacion.domain.cliente.Cliente;
import com.yoropeza.facturacion.domain.cliente.ClienteRepository;
import com.yoropeza.facturacion.domain.cliente.DatosRegistroCliente;
import com.yoropeza.facturacion.domain.cliente.DatosRespuestaCliente;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }


    @PostMapping
    public ResponseEntity<DatosRespuestaCliente> registroCliente(@RequestBody @Valid DatosRegistroCliente datosRegistroCliente,
                                                                 UriComponentsBuilder uriComponentsBuilder) {

        var cliente = clienteRepository.save(new Cliente(datosRegistroCliente));
        var datosRespuestaCliente = new DatosRespuestaCliente(cliente.getId(),cliente.getNombre(),cliente.getApellido(),cliente.getDni(),
                cliente.getTelefono(), cliente.getCreateAt());
        URI url = uriComponentsBuilder.path("/clientes").buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaCliente);
    }


}
