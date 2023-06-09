package com.yoropeza.facturacion.controller;


import com.yoropeza.facturacion.domain.categoria.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;

    public CategoriaController(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @PostMapping
    public ResponseEntity<DatosRespuestaCategoria> registrarCategoria(@RequestBody @Valid DatosRegistroCategoria datosRegistroCategoria, UriComponentsBuilder uriComponentsBuilder) {

        var categoria = categoriaRepository.save(new Categoria(datosRegistroCategoria));
        var datosRespuestaCategoria = new DatosRespuestaCategoria(categoria.getId(), categoria.getNombre());

        URI url = uriComponentsBuilder.path("/categorias").buildAndExpand(categoria.getId()).toUri();

        return ResponseEntity.created(url).body(datosRespuestaCategoria);
    }

    @GetMapping
    public ResponseEntity<Page<DatosRespuestaCategoria>> listarCategorias(@PageableDefault (sort = "nombre", size = 8) Pageable paginacion) {
        Page<DatosRespuestaCategoria> categorias = categoriaRepository.findAll(paginacion).map(DatosRespuestaCategoria::new);
        return ResponseEntity.ok(categorias);
    }

    @DeleteMapping("/{idCategoria}")
    @Transactional
    public ResponseEntity eliminarCategoria(@PathVariable Long idCategoria) {
       Categoria categoria = categoriaRepository.getReferenceById(idCategoria);
       categoriaRepository.delete(categoria);
       return ResponseEntity.noContent().build();
    }

    @GetMapping("/{idCategoria}")
    public ResponseEntity<DatosRespuestaCategoria> buscarCategoriaPorId(@PathVariable Long idCategoria) {
        Categoria categoria = categoriaRepository.getReferenceById(idCategoria);
        var datosRespuestaCategoria = new DatosRespuestaCategoria(categoria.getId(), categoria.getNombre());
        return ResponseEntity.ok(datosRespuestaCategoria);
    }

    @PutMapping("/{idCategoria}")
    @Transactional
    public ResponseEntity<DatosRespuestaCategoria> actualizarCategoria(@RequestBody @Valid DatosActualizarCategoria datosActualizarCategoria,
                                                                        @PathVariable Long idCategoria) {
        Categoria categoria = categoriaRepository.getReferenceById(idCategoria);
        categoria.actualizarCategoria(datosActualizarCategoria);
        return ResponseEntity.ok(new DatosRespuestaCategoria(categoria.getId(),categoria.getNombre()));
    }

}
