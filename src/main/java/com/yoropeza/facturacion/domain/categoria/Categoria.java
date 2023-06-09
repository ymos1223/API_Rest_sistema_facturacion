package com.yoropeza.facturacion.domain.categoria;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.yoropeza.facturacion.domain.productos.Producto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Table(name = "categorias")
@Entity(name = "Categoria")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    @OneToMany(mappedBy = "categoria",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonManagedReference
    @JsonIgnore
    private List<Producto> productos = new ArrayList<>();

    public Categoria(DatosRegistroCategoria datosRegistroCategoria) {

        this.nombre = datosRegistroCategoria.nombre();
    }

    public void actualizarCategoria(DatosActualizarCategoria datosActualizarCategoria) {
        if (datosActualizarCategoria.nombre() != null) {
            this.nombre = datosActualizarCategoria.nombre();
        }
    }
}
