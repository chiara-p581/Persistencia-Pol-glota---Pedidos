package com.example.demo.modelo;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
public class EventosUsuarioEmbebida implements Serializable {

    @PrimaryKeyColumn(name = "idUsuario", type = PrimaryKeyType.PARTITIONED)
    private UUID idUsuario;

    @PrimaryKeyColumn(name = "timestamp", type = PrimaryKeyType.CLUSTERED)
    private Instant timestamp;

    public EventosUsuarioEmbebida() {}

    public EventosUsuarioEmbebida(UUID idUsuario, Instant timestamp) {
        this.idUsuario = idUsuario;
        this.timestamp = timestamp;
    }

    public UUID getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(UUID idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
