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
@Table(name = "pagos")
public class Pagos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPago")
    private Integer idPago;
    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuarios idUsuario;
    @Column(name = "fechaPago")
    private LocalDateTime fechaPago;
    @Column(name = "formaPago")
    private String formaPago;
    @Column(name = "operador")
    private String operador;
    @Column(name = "montoTotal")
    private Double montoTotal;
    @Column(name = "puntoRetiro")
    private String puntoRetiro;
    

    public Pagos() {
    }

    public Pagos(Integer idPago, Usuarios idUsuario, LocalDateTime fechaPago, String formaPago, String operador,
            Double montoTotal, String puntoRetiro) {
        this.idPago = idPago;
        this.idUsuario = idUsuario;
        this.fechaPago = fechaPago;
        this.formaPago = formaPago;
        this.operador = operador;
        this.montoTotal = montoTotal;
        this.puntoRetiro = puntoRetiro;
    }
    
    public Integer getIdPago() {
        return idPago;
    }
    public void setIdPago(Integer idPago) {
        this.idPago = idPago;
    }
    public Usuarios getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(Usuarios idUsuario) {
        this.idUsuario = idUsuario;
    }
    public LocalDateTime getFechaPago() {
        return fechaPago;
    }
    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }
    public String getFormaPago() {
        return formaPago;
    }
    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }
    public String getOperador() {
        return operador;
    }
    public void setOperador(String operador) {
        this.operador = operador;
    }
    public Double getMontoTotal() {
        return montoTotal;
    }
    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }
    
    public String getPuntoRetiro() {
        return puntoRetiro;
    }

    public void setPuntoRetiro(String puntoRetiro) {
        this.puntoRetiro = puntoRetiro;
    }
    
    
}