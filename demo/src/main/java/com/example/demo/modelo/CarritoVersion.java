package com.example.demo.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("CarritoVersion")
public class CarritoVersion implements Serializable {

    @Id
    private String versionId; // UUID
    private String idUsuario;
    private Map<String, Integer> productos;
    private LocalDateTime fechaVersion;

    public CarritoVersion() {}

    public CarritoVersion(String idUsuario, Map<String, Integer> productos) {
        this.versionId = UUID.randomUUID().toString();
        this.idUsuario = idUsuario;
        this.productos = productos;
        this.fechaVersion = LocalDateTime.now();
    }

    // Getters y setters

    public String getVersionId() {
        return versionId;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public Map<String, Integer> getProductos() {
        return productos;
    }

    public LocalDateTime getFechaVersion() {
        return fechaVersion;
    }
}

