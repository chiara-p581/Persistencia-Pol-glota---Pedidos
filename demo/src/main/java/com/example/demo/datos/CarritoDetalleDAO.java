package com.example.demo.datos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.modelo.Carrito;
import com.example.demo.modelo.CarritoDetalle;
import com.example.demo.modelo.ListaPrecio;

@Repository
public class CarritoDetalleDAO {

    @Autowired
    private CarritoDetalleRepository carritoDetalleRepository;

    public List<CarritoDetalle> getAll() {
        return carritoDetalleRepository.findAll();
    }

    public CarritoDetalle getByIdDetalle(Integer idDetalle) {
        return carritoDetalleRepository.findByIdDetalle(idDetalle);
    }

    public List<CarritoDetalle> getByCarrito(Carrito carrito) {
        return carritoDetalleRepository.findByIdCarrito(carrito);
    }

    public List<CarritoDetalle> getByProducto(ListaPrecio producto) {
        return carritoDetalleRepository.findByIdProducto(producto);
    }

    public List<CarritoDetalle> getByCantidad(int cantidad) {
        return carritoDetalleRepository.findByCantidad(cantidad);
    }

    public List<CarritoDetalle> getByPrecioUnitario(double precio) {
        return carritoDetalleRepository.findByPrecioUnitario(precio);
    }

    public void save(CarritoDetalle detalle) {
        carritoDetalleRepository.save(detalle);
    }

    public void delete(CarritoDetalle detalle) {
        carritoDetalleRepository.delete(detalle);
    }
}