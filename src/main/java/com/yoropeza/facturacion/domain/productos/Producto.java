package com.yoropeza.facturacion.domain.productos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yoropeza.facturacion.domain.categoria.Categoria;
import com.yoropeza.facturacion.domain.categoria.DatosRegistroCategoria;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "productos")
@Entity(name = "Producto")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private double precio;
    @Column(name = "create_at")
    private LocalDateTime createAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    @JsonBackReference
    private Categoria categoria;

    public Producto(DatosRegistroProducto datosRegistroProducto, Categoria categoria) {
    this.nombre = datosRegistroProducto.nombre();
    this.precio = datosRegistroProducto.precio();
    this.createAt= LocalDateTime.now();
    this.categoria = categoria;
    }

    public void actualizarProducto(DatosActualizarProducto datosActualizarProducto, Categoria categoria) {
        if (datosActualizarProducto.nombre() != null) {
            this.nombre = datosActualizarProducto.nombre();
        }
        if (datosActualizarProducto.precio() != 0) {
            this.precio = datosActualizarProducto.precio();
        }

        if (categoria != null) {
            this.categoria = categoria;
        }

    }



}
