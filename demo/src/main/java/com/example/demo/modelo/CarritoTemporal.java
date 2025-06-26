package com.example.demo.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("CarritoTemporal")
public class CarritoTemporal implements Serializable {

    @Id
    private String idUsuario;
    private Map<String, Integer> productos; // idProducto -> cantidad
    private LocalDateTime ultimaModificacion;

    // Constructor vacío
    public CarritoTemporal() {}

    public CarritoTemporal(String idUsuario, Map<String, Integer> productos) {
        this.idUsuario = idUsuario;
        this.productos = productos;
        this.ultimaModificacion = LocalDateTime.now();
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Map<String, Integer> getProductos() {
        return productos;
    }

    public void setProductos(Map<String, Integer> productos) {
        this.productos = productos;
    }

    public LocalDateTime getUltimaModificacion() {
        return ultimaModificacion;
    }

    public void setUltimaModificacion(LocalDateTime ultimaModificacion) {
        this.ultimaModificacion = ultimaModificacion;
    }

    // Getters y setters
    
}