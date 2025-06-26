package com.example.demo.datos;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modelo.Pedidos;
import com.example.demo.modelo.Usuarios;

public interface PedidosRepository extends JpaRepository<Pedidos, Integer> {

    Pedidos findByIdPedido(Integer idPedido);

    Pedidos findByIdUsuario(Usuarios idUsuario);

    Pedidos findByEstado(String estado);

    Pedidos findByFechaPedido(LocalDateTime fechaPedido);

    Pedidos findByTotal(double total);



}
