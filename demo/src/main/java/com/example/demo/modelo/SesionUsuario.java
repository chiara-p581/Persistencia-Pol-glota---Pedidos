package com.example.demo.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("SesionUsuario") // Redis almacenará esto bajo la key "SesionUsuario:{id}"
public class SesionUsuario implements Serializable {

    @Id
    private String idUsuario;

    private String nombre;
    private String documento;
    private String direccion;
    private LocalDateTime inicioSesion;
    private LocalDateTime ultimaActividad;

    public SesionUsuario() {}

    public SesionUsuario(String idUsuario, String nombre, String documento, String direccion) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.documento = documento;
        this.direccion = direccion;
        this.inicioSesion = LocalDateTime.now();
        this.ultimaActividad = LocalDateTime.now();
    }

    public String getIdUsuario() { return idUsuario; }
    public void setIdUsuario(String idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public LocalDateTime getInicioSesion() { return inicioSesion; }
    public void setInicioSesion(LocalDateTime inicioSesion) { this.inicioSesion = inicioSesion; }

    public LocalDateTime getUltimaActividad() { return ultimaActividad; }
    public void setUltimaActividad(LocalDateTime ultimaActividad) { this.ultimaActividad = ultimaActividad; }
}
