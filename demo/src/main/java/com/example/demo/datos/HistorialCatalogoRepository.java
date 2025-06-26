package com.example.demo.datos;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.modelo.HistorialCatalogo;

public interface HistorialCatalogoRepository extends MongoRepository<HistorialCatalogo, ObjectId> {

    // Buscar por id de producto
    List<HistorialCatalogo> findByProductoId(ObjectId producto_id); 

    // Buscar por usuario que modificó
    List<HistorialCatalogo> findByModificadoPor(String modificado_por);

    // Buscar por rango de fechas
    List<HistorialCatalogo> findByFechaBetween(Date desde, Date hasta);
}
