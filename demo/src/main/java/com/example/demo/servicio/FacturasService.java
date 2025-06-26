package com.example.demo.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.datos.FacturasRepository;
import com.example.demo.modelo.Facturas;
import com.example.demo.modelo.Pedidos;
import com.example.demo.modelo.Usuarios;

@Service
public class FacturasService {
    @Autowired
    private FacturasRepository facturasRepository;

    @Autowired
    private PedidosService pedidosService;

    public Facturas registrarFactura(Facturas facturas) {
        return facturasRepository.save(facturas);
    }

    public Facturas obtenerFacturaPorId(int idFactura) {
        return facturasRepository.findById(idFactura).orElse(null);
    }

    public List<Facturas> listarFacturas() {
        return facturasRepository.findAll();
    }

    public Facturas facturarPedido(int idPedido) {
        Pedidos pedido = pedidosService.buscarPorId(idPedido);
        if (pedido == null) {
            throw new RuntimeException("Pedido no encontrado");
        }

        Facturas factura = new Facturas();
        factura.setIdPedido(pedido);
        factura.setFechaEmision(java.time.LocalDateTime.now());
        factura.setCondicionIvaCliente(pedido.getIdUsuario().getCondicionIVA());

        factura.setTotalNeto(pedido.getTotal());
        factura.setDescuentos(0); 
        factura.setImpuestos(0); 
        factura.setTotalFinal(pedido.getTotal()); // totalNeto - descuentos + impuestos

        return facturasRepository.save(factura);
    }

    public List<Facturas> listarFacturasPorUsuario(Usuarios usuario) {
        return facturasRepository.findByIdPedido_IdUsuario(usuario);
    }

}
