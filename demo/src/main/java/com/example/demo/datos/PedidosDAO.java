package com.example.demo.datos;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.modelo.Pedidos;
import com.example.demo.modelo.Usuarios;

@Repository
public class PedidosDAO {

    @Autowired
    private PedidosRepository pedidosRepository;

    public List<Pedidos> getAll() {
        return pedidosRepository.findAll();
    }

    public Pedidos getByIdPedido(Integer idPedido) {
        return pedidosRepository.findByIdPedido(idPedido);
    }

    public Pedidos getByIdUsuario(Usuarios idUsuario) {
        return pedidosRepository.findByIdUsuario(idUsuario);
    }

    public Pedidos getByEstado(String estado) {
        return pedidosRepository.findByEstado(estado);
    }

    public Pedidos getByfechaPedido(LocalDateTime fechaPedido) {
        return pedidosRepository.findByFechaPedido(fechaPedido);
    }

    public Pedidos getByTotal(double total) {
        return pedidosRepository.findByTotal(total);
    }

    public void save(Pedidos pedidos) {
        pedidosRepository.save(pedidos);
    }

    public void delete(Pedidos pedidos) {
        pedidosRepository.delete(pedidos);
    }

    public boolean exists(Integer idPedido) {
        return pedidosRepository.existsById(idPedido);
    }

}
