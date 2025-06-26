package com.example.demo.datos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modelo.FacturaPago;
import com.example.demo.modelo.FacturaPagoEmbebida;

public interface FacturaPagoEmbebidaRepository extends JpaRepository<FacturaPagoEmbebida, FacturaPago> {
}
