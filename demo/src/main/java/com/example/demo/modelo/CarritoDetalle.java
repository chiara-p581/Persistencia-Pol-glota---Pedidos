package com.example.demo.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "carritoDetalle")
public class CarritoDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDetalle")
    private Integer idDetalle;
    @ManyToOne
    @JoinColumn(name = "idCarrito")
    private Carrito idCarrito;
    @ManyToOne
    @JoinColumn(name = "idProducto")
    private ListaPrecio idProducto;
    @Column(name = "cantidad")
    private int cantidad;
    @Column(name = "precioUnitario")
    private double precioUnitario;

    public CarritoDetalle() {
    }

    public CarritoDetalle(Integer idDetalle, Carrito idCarrito, ListaPrecio idProducto, int cantidad, double precioUnitario) {
        this.idDetalle = idDetalle;
        this.idCarrito = idCarrito;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public Integer getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Carrito getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(Carrito idCarrito) {
        this.idCarrito = idCarrito;
    }

    public ListaPrecio getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(ListaPrecio idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    
}