package com.yoropeza.facturacion.controller;

import com.yoropeza.facturacion.domain.cliente.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping
    public ResponseEntity<Page<DatosRespuestaCliente>> listarClientes(@PageableDefault(size = 8, sort = "nombre") Pageable paginacion) {
        Page<DatosRespuestaCliente> clientes = clienteRepository.findAll(paginacion).map(DatosRespuestaCliente::new);
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<DatosRespuestaCliente> buscarClientePorId(@PathVariable Long idCliente) {
        Cliente cliente = clienteRepository.getReferenceById(idCliente);
        return ResponseEntity.ok(new DatosRespuestaCliente(cliente.getId(),cliente.getNombre(), cliente.getApellido(),
                cliente.getDni(), cliente.getTelefono(), cliente.getCreateAt()));
    }

    @DeleteMapping("/{idCliente}")
    @Transactional
    public ResponseEntity eliminarCliente(@PathVariable Long idCliente) {
        Cliente cliente = clienteRepository.getReferenceById(idCliente);
        clienteRepository.delete(cliente);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{idCliente}")
    @Transactional
    public ResponseEntity<DatosRespuestaCliente> actualizarCliente(@PathVariable Long idCliente,
                                                                   @RequestBody @Valid DatosActualizarCliente datosActualizarCliente) {
    Cliente cliente = clienteRepository.getReferenceById(idCliente);
    cliente.actualizarCliente(datosActualizarCliente);
    return ResponseEntity.ok(new DatosRespuestaCliente(cliente.getId(),cliente.getNombre(),cliente.getApellido(),
            cliente.getDni(), cliente.getTelefono(), cliente.getCreateAt()));

    }

}
