package com.example.demo.datos;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.modelo.SesionUsuario;

public interface SesionUsuarioRepository extends CrudRepository<SesionUsuario, String> {
}
