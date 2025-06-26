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
@Table(name = "facturas")
public class Facturas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idFactura")
    private Integer idFactura;
    @ManyToOne
    @JoinColumn(name = "idPedido")
    private Pedidos idPedido;
    @Column(name = "fechaEmision")  
    private LocalDateTime fechaEmision;
    @Column(name = "condicionIvaCliente")
    private String condicionIvaCliente;
    @Column(name = "totalNeto")
    private double totalNeto;
    @Column(name = "descuentos")
    private double descuentos;
    @Column(name = "impuestos")
    private double impuestos;
    @Column(name = "totalFinal")
    private double totalFinal; //totalNeto - descuentos + impuestos

    public Facturas() {
    }
    
    public Facturas(Integer idFactura, Pedidos idPedido, LocalDateTime fechaEmision, String condicionIvaCliente, double totalNeto, double descuentos, double impuestos, double totalFinal) {
        this.idFactura = idFactura;
        this.idPedido = idPedido;
        this.fechaEmision = fechaEmision;
        this.condicionIvaCliente = condicionIvaCliente;
        this.totalNeto = totalNeto;
        this.descuentos = descuentos;
        this.impuestos = impuestos;
        this.totalFinal = totalFinal;
    }

    public Integer getIdFactura() {
        return idFactura;
    }
    public void setIdFactura(Integer idFactura) {
        this.idFactura = idFactura;
    }
    public Pedidos getIdPedido() {
        return idPedido;
    }
    public void setIdPedido(Pedidos idPedido) {
        this.idPedido = idPedido;
    }
    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }
    public void setFechaEmision(LocalDateTime fechaEmision) {
        this.fechaEmision = fechaEmision;
    }
    public String getCondicionIvaCliente() {
        return condicionIvaCliente;
    }
    public void setCondicionIvaCliente(String condicionIvaCliente) {
        this.condicionIvaCliente = condicionIvaCliente;
    }
    public double getTotalNeto() {
        return totalNeto;
    }
    public void setTotalNeto(double totalNeto) {
        this.totalNeto = totalNeto;
    }
    public double getDescuentos() {
        return descuentos;
    }
    public void setDescuentos(double descuentos) {
        this.descuentos = descuentos;
    }
    public double getImpuestos() {
        return impuestos;
    }
    public void setImpuestos(double impuestos) {
        this.impuestos = impuestos;
    }
    public double getTotalFinal() {
        return totalFinal;
    }
    public void setTotalFinal(double totalFinal) {
        this.totalFinal = totalFinal;
    } 


}
