package com.example.demo.servicio;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.datos.UsuariosRepository;
import com.example.demo.modelo.Usuarios;

@Service
public class UsuarioService {
    @Autowired
    private UsuariosRepository usuariosRepository;

    public Usuarios registrarUsuario(Usuarios usuario) {
        usuario.setUuid(UUID.randomUUID());
        return usuariosRepository.save(usuario);
    }

    public List<Usuarios> listarUsuarios() {
        return usuariosRepository.findAll();
    }

    public Usuarios buscarPorEmail(String email){
        return usuariosRepository.findByEmail(email);
    }

    public Usuarios getUsuario(String email) {
        return usuariosRepository.findByEmail(email);
    }

}
