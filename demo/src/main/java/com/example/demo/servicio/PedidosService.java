package com.example.demo.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.datos.PedidosRepository;
import com.example.demo.modelo.Pedidos;

@Service
public class PedidosService {
    @Autowired
    private PedidosRepository pedidosRepository;

    public Pedidos registrarPedido(Pedidos pedido) {
        return pedidosRepository.save(pedido);
    }

    public List<Pedidos> listarPedidos() {
        return pedidosRepository.findAll();
    }

    public Pedidos buscarPorId(int idPedido) {
        Optional<Pedidos> pedidoOpt = pedidosRepository.findById(idPedido);
        return pedidoOpt.orElse(null);
    }

}
