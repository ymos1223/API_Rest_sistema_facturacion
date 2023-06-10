package com.yoropeza.facturacion.domain.cliente;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.yoropeza.facturacion.domain.factura.Factura;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "clientes")
@Entity(name = "Cliente")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private int dni;
    private String telefono;
    @Column(name = "create_at")
    private LocalDateTime createAt;
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL, mappedBy = "cliente",orphanRemoval = true)
    @JsonManagedReference
    //como la relacion es en ambos sentidos no se usa un JoinColumn
    //automaticamente con mappedBy crea la llave foranea cliente_id en la tabla facturas
    //orphan removal sirve para eliminar registros huerfanos que no estan asociados a ningun cliente
    private List<Factura> facturas = new ArrayList<>();

    public Cliente(DatosRegistroCliente datosRegistroCliente) {
        this.nombre = datosRegistroCliente.nombre();
        this.apellido = datosRegistroCliente.apellido();
        this.dni = datosRegistroCliente.dni();
        this.telefono = datosRegistroCliente.telefono();
        this.createAt = LocalDateTime.now();
    }

    public void actualizarCliente(DatosActualizarCliente datosActualizarCliente) {
        if(datosActualizarCliente.nombre() != null) {
            this.nombre = datosActualizarCliente.nombre();
        }
        if(datosActualizarCliente.apellido() != null) {
            this.apellido = datosActualizarCliente.apellido();
        }
        if(datosActualizarCliente.dni() != 0) {
            this.dni = datosActualizarCliente.dni();
        }
        if(datosActualizarCliente.telefono() != null) {
            this.telefono = datosActualizarCliente.telefono();
        }
    }

}
