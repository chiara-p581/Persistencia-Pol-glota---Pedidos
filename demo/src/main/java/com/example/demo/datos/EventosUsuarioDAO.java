package com.example.demo.datos;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.modelo.EventosUsuario;
import com.example.demo.modelo.EventosUsuarioEmbebida;

@Repository
public class EventosUsuarioDAO {

    @Autowired
    private EventosUsuarioRepository eventosUsuarioRepository;

    public List<EventosUsuario> getAll() {
        return eventosUsuarioRepository.findAll();
    }

    public List<EventosUsuario> getByUsuarioId(UUID usuarioId) {
        return eventosUsuarioRepository.findByKeyIdUsuario(usuarioId);
    }

    public void save(EventosUsuario evento) {
        eventosUsuarioRepository.save(evento);
    }

    public void delete(EventosUsuario evento) {
        eventosUsuarioRepository.delete(evento);
    }

    public void deleteById(EventosUsuarioEmbebida id) {
        eventosUsuarioRepository.deleteById(id);
    }

    public boolean existsById(EventosUsuarioEmbebida id) {
        return eventosUsuarioRepository.existsById(id);
    }

    public EventosUsuario getById(EventosUsuarioEmbebida id) {
        return eventosUsuarioRepository.findById(id).orElse(null);
    }
}
