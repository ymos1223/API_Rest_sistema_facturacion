package com.yoropeza.facturacion.controller;

import com.yoropeza.facturacion.domain.categoria.Categoria;
import com.yoropeza.facturacion.domain.categoria.CategoriaRepository;
import com.yoropeza.facturacion.domain.categoria.DatosRespuestaCategoria;
import com.yoropeza.facturacion.domain.productos.*;
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
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoController(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @PostMapping
    public ResponseEntity<DatosRespuestaProducto> registrarProducto(@RequestBody @Valid DatosRegistroProducto datosRegistroProducto, UriComponentsBuilder uriComponentsBuilder) {

        var categoria = categoriaRepository.getReferenceById(datosRegistroProducto.categoria_id());
        var producto = productoRepository.save( new Producto(datosRegistroProducto,categoria));
        var datosRespuestaProducto = new DatosRespuestaProducto(producto.getId(), producto.getNombre(), producto.getPrecio(),
                producto.getCreateAt(), categoria);
        URI url = uriComponentsBuilder.path("/productos").buildAndExpand(producto.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaProducto);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListarProducto>> listarProductos(@PageableDefault(size = 5,sort = "nombre") Pageable paginacion ) {
        Page<DatosListarProducto> productos = productoRepository.findAll(paginacion).map(DatosListarProducto::new);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{idProducto}")
    public ResponseEntity<DatosListarProducto> buscarProductoPorId(@PathVariable Long idProducto) {
        var producto = productoRepository.getReferenceById(idProducto);
        var datosListarProducto = new DatosListarProducto(producto.getId(), producto.getNombre(), producto.getPrecio(),
                producto.getCreateAt(), new DatosRespuestaCategoria(producto.getCategoria().getId(),producto.getCategoria().getNombre()));
        return ResponseEntity.ok(datosListarProducto);
    }


    @DeleteMapping("/{idProducto}")
    @Transactional
    public ResponseEntity eliminarProducto(@PathVariable Long idProducto) {
        var producto = productoRepository.getReferenceById(idProducto);
        productoRepository.delete(producto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{idProducto}")
    @Transactional
    public ResponseEntity<DatosListarProducto> actualizarProducto(@PathVariable Long idProducto,
                                                                  @RequestBody @Valid DatosActualizarProducto datosActualizarProducto
                                                                      ) {

        var producto = productoRepository.getReferenceById(idProducto);
        var categoria = categoriaRepository.getReferenceById(datosActualizarProducto.categoria_id());
        producto.actualizarProducto(datosActualizarProducto, categoria);
        return ResponseEntity.ok(new DatosListarProducto(producto.getId(),producto.getNombre(),producto.getPrecio(),
                producto.getCreateAt(),new DatosRespuestaCategoria(producto.getCategoria().getId(),producto.getCategoria().getNombre())));
    }

}
