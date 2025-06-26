package com.example.demo.modelo;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedidos")
public class Pedidos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPedido")
    private Integer idPedido;
    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuarios idUsuario;
    @Column(name = "estado")
    private String estado;
    @Column(name = "fechaPedido")
    private LocalDateTime fechaPedido;
    @Column(name = "total")
    private Double total;

    public Pedidos() {
    }

    public Pedidos(Integer idPedido, Usuarios idUsuario, String estado, LocalDateTime fechaPedido, Double total) {
        this.idPedido = idPedido;
        this.idUsuario = idUsuario;
        this.estado = estado;
        this.fechaPedido = fechaPedido;
        this.total = total;
    }

    public Integer getIdPedido() {
        return idPedido;
    }
    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }
    public Usuarios getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(Usuarios idUsuario) {
        this.idUsuario = idUsuario;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public LocalDateTime getFechaPedido() {
        return fechaPedido;
    }
    public void setFechaPedido(LocalDateTime fechaPedido) {
        this.fechaPedido = fechaPedido;
    }
    public Double getTotal() {
        return total;
    }
    public void setTotal(Double total) {
        this.total = total;
    }
    
}