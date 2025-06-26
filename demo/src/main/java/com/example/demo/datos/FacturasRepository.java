package com.example.demo.datos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modelo.Facturas;
import com.example.demo.modelo.Pedidos;
import com.example.demo.modelo.Usuarios;

public interface FacturasRepository extends JpaRepository<Facturas, Integer> {

    Facturas findByIdFactura(Integer idFactura);

    Facturas findByIdPedido(Pedidos idPedido);

    List<Facturas> findByTotalNeto(double totalNeto);

    List<Facturas> findByDescuentos(double descuentos);

    List<Facturas> findByImpuestos(double impuestos);

    List<Facturas> findByTotalFinal(double totalFinal);

    List<Facturas> findByIdPedido_IdUsuario(Usuarios usuario);

}
