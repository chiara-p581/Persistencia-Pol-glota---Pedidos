package com.example.demo.datos;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modelo.Pagos;
import com.example.demo.modelo.Usuarios;

public interface PagosRepository extends JpaRepository<Pagos, Integer> {

    Pagos findByIdPago(Integer idPago);

    List<Pagos> findAllByIdUsuario(Usuarios idUsuario);

    Pagos findByMontoTotal(double montoTotal);

    Pagos findByFormaPago(String formaPago);

    Pagos findByFechaPago(LocalDateTime fechaPago);

    Pagos findByPuntoRetiro(String puntoRetiro);
    


}

