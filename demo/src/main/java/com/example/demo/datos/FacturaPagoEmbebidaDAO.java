package com.example.demo.datos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.modelo.FacturaPago;
import com.example.demo.modelo.FacturaPagoEmbebida;

@Repository
public class FacturaPagoEmbebidaDAO {

    @Autowired
    private FacturaPagoEmbebidaRepository facturaPagoEmbebidaRepository;

    public List<FacturaPagoEmbebida> getAll() {
        return facturaPagoEmbebidaRepository.findAll();
    }

    public FacturaPagoEmbebida getById(FacturaPago id) {
        return facturaPagoEmbebidaRepository.findById(id).orElse(null);
    }

    public void save(FacturaPagoEmbebida entidad) {
        facturaPagoEmbebidaRepository.save(entidad);
    }

    public void delete(FacturaPagoEmbebida entidad) {
        facturaPagoEmbebidaRepository.delete(entidad);
    }

    public boolean exists(FacturaPago id) {
        return facturaPagoEmbebidaRepository.existsById(id);
    }
}
