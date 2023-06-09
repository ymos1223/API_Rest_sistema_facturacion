package com.yoropeza.facturacion.domain.factura;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.yoropeza.facturacion.domain.cliente.Cliente;
import com.yoropeza.facturacion.domain.items.ItemFactura;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "facturas")
@Entity(name = "Factura")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descripcion;
    @Column(name = "fecha_facturacion")
    private LocalDateTime fechaFacturacion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    @JsonBackReference
    private Cliente cliente;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "factura_id")
    @JsonManagedReference
    //JoinColumn se usa para generar la llave foranea factura_id en la tabla items_facturas
    //como la relacion no es bidireccional no se crea automaticamente factura_id
    //orphan removal sirve para eliminar registros huerfanos que no estan asociados a ningun cliente
    private List<ItemFactura> items = new ArrayList<>();

    private double getTotal() {
        Double total= 0.0;
        int size = items.size();
        for(int i=0;i<size; i++) {
            total += items.get(i).calcularImporte();
        }
        return total;

    }
}
