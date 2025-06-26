package com.example.demo.datos;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.modelo.Carrito;
import com.example.demo.modelo.Usuarios;

@Repository
public class CarritoDAO {
    
    @Autowired
    private CarritoRepository carritoRepository;

    public List<Carrito> getAll() {
        return carritoRepository.findAll();
    }

    public Carrito getByIdCarrito(Integer idCarrito) {
        return carritoRepository.findByIdCarrito(idCarrito);
    }

    public List<Carrito> getByUsuario(Usuarios usuario) {
        return carritoRepository.findByIdUsuario(usuario);
    }

    public List<Carrito> getByEstado(String estado) {
        return carritoRepository.findByEstado(estado);
    }

    public List<Carrito> getByFechaCreacion(LocalDateTime fechaCreacion) {
        return carritoRepository.findByFechaCreacion(fechaCreacion);
    }

    public void save(CarritoRepository carritoRepository, Carrito carrito) {
        carritoRepository.save(carrito);
    }
	
	public void delete(CarritoRepository carritoRepository, Carrito carrito) {
		carritoRepository.delete(carrito);;
	}

}