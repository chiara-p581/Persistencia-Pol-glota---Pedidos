package com.example.demo.datos;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.modelo.ListaPrecio;

public interface ListaPrecioRepository extends JpaRepository<ListaPrecio, Integer> {

    ListaPrecio findByIdProducto(Integer idProducto);

    @Query("SELECT lp FROM ListaPrecio lp WHERE LOWER(lp.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<ListaPrecio> findByNombre(@Param("nombre") String nombre);

    ListaPrecio findByPrecioActual(Double precioActual);

    ListaPrecio findByFechaActualizacion(LocalDateTime fechaActualizacion);

}
