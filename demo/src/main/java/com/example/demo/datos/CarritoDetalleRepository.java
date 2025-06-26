package com.example.demo.datos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modelo.Carrito;
import com.example.demo.modelo.CarritoDetalle;
import com.example.demo.modelo.ListaPrecio;

public interface CarritoDetalleRepository extends JpaRepository<CarritoDetalle, Integer> {

    List<CarritoDetalle> findByIdCarrito(Carrito carrito);

    CarritoDetalle findByIdDetalle(Integer idDetalle);

    List<CarritoDetalle> findByIdProducto(ListaPrecio idProducto);

    List<CarritoDetalle> findByCantidad(int cantidad);

    List<CarritoDetalle> findByPrecioUnitario(double precioUnitario);

}
