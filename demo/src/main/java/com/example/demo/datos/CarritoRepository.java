package com.example.demo.datos;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modelo.Carrito;
import com.example.demo.modelo.Usuarios;

public interface CarritoRepository extends JpaRepository<Carrito, Integer> {

    List<Carrito> findByIdUsuario(Usuarios usuario);

    Carrito findByIdCarrito(Integer idCarrito);

    List<Carrito> findByEstado(String estado);

    List<Carrito> findByFechaCreacion(LocalDateTime fechaCreacion);

    Carrito findByIdUsuarioAndEstado(Usuarios usuario, String estado);

}
