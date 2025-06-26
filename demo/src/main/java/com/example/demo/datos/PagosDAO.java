package com.example.demo.datos;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.modelo.Pagos;
import com.example.demo.modelo.Usuarios;

@Repository
public class PagosDAO {

    @Autowired
    private PagosRepository pagosRepository;

    public Pagos findByIdPago(Integer idPago) {
        return pagosRepository.findByIdPago(idPago);
    }

    public List<Pagos> findAllByIdUsuario(Usuarios idUsuario) {
        return pagosRepository.findAllByIdUsuario(idUsuario);
    }

    public Pagos findByMontoTotal(double montoTotal) {
        return pagosRepository.findByMontoTotal(montoTotal);
    }

    public Pagos findByFormaPago(String formaPago) {
        return pagosRepository.findByFormaPago(formaPago);
    }

    public Pagos findByFechaPago(LocalDateTime fechaPago) {
        return pagosRepository.findByFechaPago(fechaPago);
    }

    public Pagos findByPuntoRetiro(String puntoRetiro) {
        return pagosRepository.findByPuntoRetiro(puntoRetiro);
    }

    public void save(Pagos pagos) {
        pagosRepository.save(pagos);
    }

    public void delete(Pagos pagos) {
        pagosRepository.delete(pagos);
    }

}
