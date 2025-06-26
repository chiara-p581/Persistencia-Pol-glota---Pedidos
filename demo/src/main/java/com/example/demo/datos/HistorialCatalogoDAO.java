package com.example.demo.datos;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.modelo.HistorialCatalogo;

@Repository
public class HistorialCatalogoDAO {

    @Autowired
    private HistorialCatalogoRepository historialCatalogoRepository;

    public List<HistorialCatalogo> getAll() {
        return historialCatalogoRepository.findAll();
    }

    public HistorialCatalogo getById(ObjectId id) {
        return historialCatalogoRepository.findById(id).orElse(null);
    }

    public List<HistorialCatalogo> getByProducto(ObjectId productoId) {
        return historialCatalogoRepository.findByProductoId(productoId);
    }

    public List<HistorialCatalogo> getByModificadoPor(String usuario) {
        return historialCatalogoRepository.findByModificadoPor(usuario);
    }

    public List<HistorialCatalogo> getByFechaEntre(Date desde, Date hasta) {
        return historialCatalogoRepository.findByFechaBetween(desde, hasta);
    }

    public void save(HistorialCatalogo historial) {
        historialCatalogoRepository.save(historial);
    }

    public void delete(HistorialCatalogo historial) {
        historialCatalogoRepository.delete(historial);
    }
}
