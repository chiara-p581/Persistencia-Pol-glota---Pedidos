package com.example.demo.datos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modelo.Usuarios;

public interface UsuariosRepository extends JpaRepository<Usuarios, Integer>{
    Usuarios findByIdUsuario(Integer idUsuario);

    Usuarios findByNombre(String nombre);

    Usuarios findByApellido(String apellido);

    Usuarios findByEmail(String email);

    Usuarios findByDireccion(String direccion);

    List<Usuarios> findByTipoUsuario(String tipoUsuario);

    Usuarios findByDocumento(int documento);

    

}
