package com.example.demo.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("ActividadUsuario") // Redis almacenará esto bajo la key "SesionUsuario:{id}"
public class ActividadUsuario implements Serializable {

    @Id
    private String idUsuario;
    private String tipoActividad; // Ej: "AGREGAR_AL_CARRITO", "INICIO_COMPRA"
    private LocalDateTime timestamp;

    public ActividadUsuario() {}

    public ActividadUsuario(String idUsuario, String tipoActividad) {
        this.idUsuario = idUsuario;
        this.tipoActividad = tipoActividad;
        this.timestamp = LocalDateTime.now();
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTipoActividad() {
        return tipoActividad;
    }

    public void setTipoActividad(String tipoActividad) {
        this.tipoActividad = tipoActividad;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // Getters y setters
    
}
