package com.yoropeza.facturacion.domain.items;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.yoropeza.facturacion.domain.productos.Producto;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "items_facturas")
public class ItemFactura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer cantidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id")
    @JsonBackReference
    // Por defecto al mapear producto va a crear producto_id en la tabla items_factura pero igual se puede
    //mostrar de manera explicita con el join column
    private Producto producto;

    public double calcularImporte() {
        return cantidad.doubleValue() * producto.getPrecio();
    }
}
