package com.example.demo.datos;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.modelo.CarritoVersion;

@Repository
public interface CarritoVersionRepository extends CrudRepository<CarritoVersion, String> {
    Iterable<CarritoVersion> findByIdUsuario(String idUsuario);
}
