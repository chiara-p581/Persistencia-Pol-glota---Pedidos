package com.example.demo.datos;
 
import java.util.List;
 
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
 
import com.example.demo.modelo.Productos;
 
@Repository
public class ProductosDAO {
    
    @Autowired
    private ProductosRepository productosRepository;
    
    public List<Productos> getAll() {
        return productosRepository.findAll();
    }
    
    public Productos getById(ObjectId id) {
        return productosRepository.findById(id).orElse(null);
    }
    
    public List<Productos> findByNombre(String nombre) {
        return productosRepository.findByNombre(nombre);
    }
    
    public List<Productos> getByMarca(String marca) {
        return productosRepository.findByMarca(marca);
    }
    
    public List<Productos> getByCategoria(String categoria) {
        return productosRepository.findByCategoria(categoria);
    }
    
    public List<Productos> getByRangoDePrecio(Double actual) {
        return productosRepository.findByPrecioActual(actual);
    }
    
    public void save(Productos producto) {
        productosRepository.save(producto);
    }
    
    public void delete(Productos producto) {
        productosRepository.delete(producto);
    }
    
    public boolean existsById(ObjectId id) {
        return productosRepository.existsById(id);
    }
}