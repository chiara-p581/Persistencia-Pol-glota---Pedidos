package com.example.demo.modelo;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "listaPrecio")
public class ListaPrecio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProducto")
    private Integer idProducto;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "precioActual")
    private Double precioActual;
    @Column(name = "fechaActualizacion")
    private LocalDateTime fechaActualizacion;

    public ListaPrecio() {
    }

    public ListaPrecio(Integer idProducto, String nombre,
            Double precioActual, LocalDateTime fechaActualizacion) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precioActual = precioActual;
        this.fechaActualizacion = fechaActualizacion;
    }
    
    public Integer getIdProducto() {
        return idProducto;
    }
    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public Double getPrecioActual() {
        return precioActual;
    }
    public void setPrecioActual(Double precioActual) {
        this.precioActual = precioActual;
    }
    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }


    
}