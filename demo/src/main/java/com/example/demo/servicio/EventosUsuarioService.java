package com.example.demo.servicio;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.datos.EventosUsuarioRepository;
import com.example.demo.modelo.EventosUsuario;
import com.example.demo.modelo.EventosUsuarioEmbebida;
import com.example.demo.modelo.Usuarios;

@Service
public class EventosUsuarioService {
    @Autowired
    private EventosUsuarioRepository eventosUsuarioRepository;

    public EventosUsuario registrarEvento(EventosUsuario evento) {
        return eventosUsuarioRepository.save(evento);
    }

    public List<EventosUsuario> getByUsuarioId(UUID usuarioId) {
        return eventosUsuarioRepository.findByKeyIdUsuario(usuarioId);
    }

    public Duration calcularDuracionConexion(EventosUsuario evento) {
        return Duration.between(evento.getHoraInicio(), evento.getHoraFin());
    }

    public Duration duracionTotalDelDia(UUID usuarioId, LocalDate fecha) {
        List<EventosUsuario> eventos = eventosUsuarioRepository.findByKeyIdUsuario(usuarioId);
        return eventos.stream()
            .filter(e -> e.getKey().getTimestamp().atZone(java.time.ZoneId.systemDefault()).toLocalDate().equals(fecha))
            .map(e -> Duration.between(e.getHoraInicio(), e.getHoraFin()))
            .reduce(Duration.ZERO, Duration::plus);
    }

    public void registrarEventoLogin(Usuarios usuario) {
        UUID idUsuario = usuario.getUuid(); // sin convertir a String
        Instant timestamp = Instant.now();

        EventosUsuarioEmbebida clave = new EventosUsuarioEmbebida(idUsuario, timestamp);

        EventosUsuario evento = new EventosUsuario();
        evento.setKey(clave);
        evento.setTipoEvento("Login");
        evento.setDescripcion("Inicio de sesión");
        evento.setHoraInicio(timestamp);
        evento.setHoraFin(null);

        eventosUsuarioRepository.save(evento);
    }


    public String categoriaUsuario(UUID uuidUsuario) { 
        List<EventosUsuario> eventos = getByUsuarioId(uuidUsuario);
        LocalDate hoy = LocalDate.now();
        long totalMinutos = eventos.stream()
        // Solo eventos tipo "Login"
        .filter(e -> "Login".equalsIgnoreCase(e.getTipoEvento()))
        // Que tengan horaInicio y que el día sea hoy
        .filter(e -> e.getHoraInicio() != null && e.getHoraInicio().atZone(ZoneId.systemDefault()).toLocalDate().equals(hoy))
        .mapToLong(e -> {
            Instant fin = e.getHoraFin();
            if (fin == null) {
                fin = Instant.now(); // sesión abierta, calcular hasta ahora
            }
            if (fin.isAfter(e.getHoraInicio())) {
                return Duration.between(e.getHoraInicio(), fin).toMinutes();
            } else {
                return 0L;
            }
        })
        .sum();
        if (totalMinutos > 240) {
            return "TOP (" + totalMinutos + " min)";
        } else if (totalMinutos >= 120) {
            return "MEDIUM (" + totalMinutos + " min)";
        } else {
            return "LOW (" + totalMinutos + " min)";
        }
    }


    public void registrarEventoLogout(Usuarios usuario) { //agregué esto 11:13
    UUID idUsuario = usuario.getUuid();
    List<EventosUsuario> eventos = eventosUsuarioRepository.findByKeyIdUsuario(idUsuario);

    // Buscamos el último login sin horaFin
    eventos.stream()
        .filter(e -> e.getTipoEvento().equals("Login") && e.getHoraFin() == null)
        .max(Comparator.comparing(e -> e.getKey().getTimestamp()))
        .ifPresent(e -> {
            e.setHoraFin(Instant.now());
            e.setTipoEvento("Logout");
            e.setDescripcion("Cierre de sesión");
            eventosUsuarioRepository.save(e);
        });
}
}
