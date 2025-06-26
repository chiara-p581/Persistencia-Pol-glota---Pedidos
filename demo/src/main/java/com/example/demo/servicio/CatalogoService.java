package com.example.demo.servicio;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.datos.ListaPrecioRepository;
import com.example.demo.datos.ProductosRepository;
import com.example.demo.modelo.ListaPrecio;
import com.example.demo.modelo.Productos;

@Service
public class CatalogoService {
    @Autowired
    private ProductosRepository productosRepository;
    @Autowired
    private HistorialCatalogoService historialService;
    @Autowired
    private ListaPrecioRepository listaPrecioRepository; // SQL

    public List<Productos> obtenerTodosLosProductos() {
        return productosRepository.findAll();
    }

    public void agregarProducto(Productos producto, String operador) {
    // 1. Guardar producto en Mongo
    productosRepository.save(producto);

    // 2. Guardar en historial (precio inicial)
    historialService.registrarCambio(
        producto.getId(),
        "precioActual",
        null,
        producto.getPrecioActual(),
        operador
    );

    // 3. Guardar en ListaPrecio (SQL)
    ListaPrecio lista = new ListaPrecio();
    // No setear el ID
    lista.setNombre(producto.getNombre());
    lista.setPrecioActual(producto.getPrecioActual());
    lista.setFechaActualizacion(LocalDateTime.now());

    listaPrecioRepository.save(lista);
}


    public List<ListaPrecio> buscarPorNombre(String nombre) {
        return listaPrecioRepository.findByNombre(nombre);
}

    public void actualizarPrecioProducto(ObjectId idProducto, Double nuevoPrecio, String operador) {
        // 1. Buscar producto en Mongo
        Productos producto = productosRepository.findById(idProducto)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado en catálogo"));

        // 2. Guardar precio anterior para historial
        Double precioAnterior = producto.getPrecioActual();

        // 3. Actualizar producto en Mongo
        producto.setPrecioActual(nuevoPrecio);
        productosRepository.save(producto);

        // 4. Actualizar también en SQL (ListaPrecio)
        ListaPrecio lista = listaPrecioRepository.findByIdProducto(producto.getId().hashCode());
        if (lista == null) {
            lista = new ListaPrecio();
            lista.setIdProducto(producto.getId().hashCode());
        }
        lista.setNombre(producto.getNombre());
        lista.setPrecioActual(nuevoPrecio);
        listaPrecioRepository.save(lista);

        // 5. Registrar en historial
        historialService.registrarCambio(
            producto.getId(),
            "precioActual",
            precioAnterior,
            nuevoPrecio,
            operador
        );
    }
}
