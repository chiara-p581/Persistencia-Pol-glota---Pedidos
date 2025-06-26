package com.example.demo.datos;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.modelo.ListaPrecio;

@Repository
public class ListaPrecioDAO {
    @Autowired
    private ListaPrecioRepository listaPrecioRepository;

    public ListaPrecio findByIdProducto(Integer idProducto) {
        return listaPrecioRepository.findByIdProducto(idProducto);
    }

    public List<ListaPrecio> findByNombre(String nombre) {
        return listaPrecioRepository.findByNombre(nombre);
    }

    public ListaPrecio findByPrecioActual(Double precioActual) {
        return listaPrecioRepository.findByPrecioActual(precioActual);
    }

    public ListaPrecio findByFechaActualizacion(LocalDateTime fechaActualizacion) {
        return listaPrecioRepository.findByFechaActualizacion(fechaActualizacion);
    }

    public ListaPrecio save(ListaPrecio listaPrecio) {
        return listaPrecioRepository.save(listaPrecio);
    }

    public void delete(ListaPrecio listaPrecio) {
        listaPrecioRepository.delete(listaPrecio);
    }

}

