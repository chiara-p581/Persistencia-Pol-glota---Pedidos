package com.example.demo.datos;
 
import java.util.List;
 
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
 
import com.example.demo.modelo.Productos;
 
public interface ProductosRepository extends MongoRepository<Productos, ObjectId> {
 
    // Buscar por nombre
    List<Productos> findByNombre(String nombre);
 
    // Buscar por marca
    List<Productos> findByMarca(String marca);
 
    // Buscar por categoría
    List<Productos> findByCategoria(String categoria);
 
    // Buscar por rango de precios
    List<Productos> findByPrecioActual(Double precioActual);
 
    // Buscar todos los productos
    //List<Productos> findAll();
    //Ya está implementado por MongoRepository
}