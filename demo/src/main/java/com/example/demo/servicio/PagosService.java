package com.example.demo.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.datos.FacturaPagoEmbebidaRepository;
import com.example.demo.datos.FacturasRepository;
import com.example.demo.datos.PagosRepository;
import com.example.demo.modelo.FacturaPago;
import com.example.demo.modelo.FacturaPagoEmbebida;
import com.example.demo.modelo.Facturas;
import com.example.demo.modelo.Pagos;
import com.example.demo.modelo.Usuarios;

import jakarta.transaction.Transactional;

@Service
public class PagosService {
    @Autowired
    private PagosRepository pagosRepository;
    @Autowired 
    private FacturasRepository facturasRepository;
    @Autowired 
    private FacturaPagoEmbebidaRepository facturaPagoEmbebidaRepository;

    public Pagos registrarPagos(Pagos pagos) {
        return pagosRepository.save(pagos);
    }

    public List<Pagos> listarPagos() {
        return pagosRepository.findAll();
    }

    public Pagos obtenerPagoPorId(Integer id) {
        return pagosRepository.findById(id).orElse(null);
    }

    public List<Pagos> pagosPorUsuario(Usuarios idUsuario){
        return pagosRepository.findAllByIdUsuario(idUsuario);
    }

    @Transactional
public Pagos registrarPagoMultiple(Pagos pagoPlantilla, List<Integer> idsFacturas) {
    System.out.println("[DEBUG] 1 - Buscando facturas...");
    List<Facturas> facturas = facturasRepository.findAllById(idsFacturas);
    if (facturas.size() != idsFacturas.size()) {
        throw new IllegalArgumentException("Alguna factura no existe");
    }

    System.out.println("[DEBUG] 2 - Calculando total...");
    double total = facturas.stream()
                           .mapToDouble(Facturas::getTotalFinal)
                           .sum();
    pagoPlantilla.setMontoTotal(total);

    System.out.println("[DEBUG] 3 - Guardando pago...");
    pagosRepository.save(pagoPlantilla);

    System.out.println("[DEBUG] 4 - Generando enlaces...");
    List<FacturaPagoEmbebida> enlaces = facturas.stream().map(f -> {
        FacturaPago pk = new FacturaPago();
        pk.setIdFactura(f);
        pk.setIdPago(pagoPlantilla);

        FacturaPagoEmbebida fp = new FacturaPagoEmbebida();
        fp.setId(pk);
        return fp;
    }).toList();

    System.out.println("[DEBUG] 5 - Guardando enlaces...");
    facturaPagoEmbebidaRepository.saveAll(enlaces);

    System.out.println("[DEBUG] 6 - Pago completo.");
    return pagoPlantilla;
}


}