package com.example.demo.datos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.modelo.Facturas;
import com.example.demo.modelo.Pedidos;

@Repository
public class FacturasDAO {

    @Autowired
    private FacturasRepository facturasRepository;

    public List<Facturas> getAll() {
        return facturasRepository.findAll();
    }

    public Facturas getById(Integer idFactura) {
        return facturasRepository.findByIdFactura(idFactura);
    }

    public Facturas getByPedido(Pedidos pedido) {
        return facturasRepository.findByIdPedido(pedido);
    }

    public List<Facturas> getByTotalNeto(double total) {
        return facturasRepository.findByTotalNeto(total);
    }

    public List<Facturas> getByDescuentos(double descuentos) {
        return facturasRepository.findByDescuentos(descuentos);
    }

    public List<Facturas> getByImpuestos(double impuestos) {
        return facturasRepository.findByImpuestos(impuestos);
    }

    public List<Facturas> getByTotalFinal(double totalFinal) {
        return facturasRepository.findByTotalFinal(totalFinal);
    }

    public void save(Facturas factura) {
        facturasRepository.save(factura);
    }

    public void delete(Facturas factura) {
        facturasRepository.delete(factura);
    }

    public boolean exists(Integer idFactura) {
        return facturasRepository.existsById(idFactura);
    }
}
