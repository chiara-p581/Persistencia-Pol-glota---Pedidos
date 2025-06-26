package com.example.demo.modelo;
import java.time.Instant;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("eventosUsuario")
public class EventosUsuario {

    @PrimaryKey
    private EventosUsuarioEmbebida key;

    @Column("tipoEvento")
    private String tipoEvento;

    @Column("descripcion")
    private String descripcion;

    @Column("productoId")
    private Integer productoId;

    @Column("monto")
    private Integer monto;

    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    @Column("horaInicio")
    private Instant horaInicio;

    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    @Column("horaFin")
    private Instant horaFin;

    // Constructor por defecto
    public EventosUsuario() {}
    
    // Constructor con parámetros
    public EventosUsuario(EventosUsuarioEmbebida key, String tipoEvento, String descripcion,
                          Integer productoId, Integer monto, Instant horaInicio, Instant horaFin) {
        this.key = key;
        this.tipoEvento = tipoEvento;
        this.descripcion = descripcion;
        this.productoId = productoId;
        this.monto = monto;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public EventosUsuarioEmbebida getKey() { return key; }
    public void setKey(EventosUsuarioEmbebida key) { this.key = key; }
    public String getTipoEvento() { return tipoEvento; }
    public void setTipoEvento(String tipoEvento) { this.tipoEvento = tipoEvento; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Integer getProductoId() { return productoId; }
    public void setProductoId(Integer productoId) { this.productoId = productoId; }
    public Integer getMonto() { return monto; }
    public void setMonto(Integer monto) { this.monto = monto; }
    public Instant getHoraInicio() { return horaInicio; }
    public void setHoraInicio(Instant horaInicio) { this.horaInicio = horaInicio; }
    public Instant getHoraFin() { return horaFin; }
    public void setHoraFin(Instant horaFin) { this.horaFin = horaFin;}
}