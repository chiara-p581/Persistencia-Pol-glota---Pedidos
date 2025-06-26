package com.example.demo.servicio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.datos.SesionUsuarioRepository;
import com.example.demo.modelo.SesionUsuario;

@Service
public class SesionUsuarioService {

    @Autowired
    private SesionUsuarioRepository sesionUsuarioRepository;

    public void iniciarSesion(SesionUsuario sesion) {
        sesionUsuarioRepository.save(sesion);
    }

    public Optional<SesionUsuario> obtenerSesion(String idUsuario) {
        return sesionUsuarioRepository.findById(idUsuario);
    }

    public void cerrarSesion(String idUsuario) {
        sesionUsuarioRepository.deleteById(idUsuario);
    }

    public void actualizarActividad(String idUsuario) {
        sesionUsuarioRepository.findById(idUsuario).ifPresent(s -> {
            s.setUltimaActividad(java.time.LocalDateTime.now());
            sesionUsuarioRepository.save(s);
        });
    }
}
