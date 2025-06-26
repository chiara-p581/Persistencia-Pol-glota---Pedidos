package com.example.demo.datos;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.example.demo.modelo.EventosUsuario;
import com.example.demo.modelo.EventosUsuarioEmbebida;

public interface EventosUsuarioRepository extends CassandraRepository<EventosUsuario, EventosUsuarioEmbebida> {
    //cambié esto 11:58
    //@Query("SELECT * FROM eventosusuario WHERE idusuario = :idUsuario ALLOW FILTERING")
    List<EventosUsuario> findByKeyIdUsuario(UUID idUsuario);

}
