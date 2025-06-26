package com.example.demo.datos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.modelo.Usuarios;


@Repository
public class UsuariosDAO {

    @Autowired
    private UsuariosRepository usuariosRepository;

    public Usuarios getByIdUsuario(Integer idUsuario) {
        return usuariosRepository.findByIdUsuario(idUsuario);
    }

    public Usuarios getByNombre(String nombre) {
        return usuariosRepository.findByNombre(nombre);
    }

    public Usuarios getByApellido(String apellido) {
        return usuariosRepository.findByApellido(apellido);
    }

    public Usuarios getByEmail(String email) {
        return usuariosRepository.findByEmail(email);
    }

    public Usuarios getByDireccion(String direccion) {
        return usuariosRepository.findByDireccion(direccion);
    }

    public List<Usuarios> getByTipoUsuario(String tipoUsuario) {
        return usuariosRepository.findByTipoUsuario(tipoUsuario);
    }

    public void save(Usuarios usuarios) {
        usuariosRepository.save(usuarios);
    }

    public void delete(Usuarios usuarios) {
        usuariosRepository.delete(usuarios);
    }

    public boolean exists(Integer idUsuario) {
        return usuariosRepository.existsById(idUsuario);
    }


}
