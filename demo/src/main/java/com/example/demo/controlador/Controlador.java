package com.example.demo.controlador;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;                        
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.modelo.CarritoDetalle;
import com.example.demo.modelo.EventosUsuario;
import com.example.demo.modelo.EventosUsuarioEmbebida;
import com.example.demo.modelo.Facturas;
import com.example.demo.modelo.HistorialCatalogo;
import com.example.demo.modelo.Pagos;
import com.example.demo.modelo.Pedidos;
import com.example.demo.modelo.SesionUsuario;
import com.example.demo.modelo.Usuarios;
import com.example.demo.servicio.CarritoService;
import com.example.demo.servicio.EventosUsuarioService;
import com.example.demo.servicio.FacturasService;
import com.example.demo.servicio.HistorialCatalogoService;
import com.example.demo.servicio.PagosService;
import com.example.demo.servicio.SesionUsuarioService;
import com.example.demo.servicio.UsuarioService;


@RestController
@RequestMapping("/api")
public class Controlador {
    @Autowired private UsuarioService usuarioService;
    @Autowired private SesionUsuarioService sesionUsuarioService;
    @Autowired private EventosUsuarioService eventosUsuarioService;
    @Autowired private CarritoService carritoService;
    @Autowired private FacturasService facturasService;
    @Autowired private PagosService pagosService;
    @Autowired private HistorialCatalogoService historialCatalogoService;
    private final Map<Integer, List<CarritoDetalle>> carritoSnapshots = new HashMap<>();  


    //-----SESIONES------
    @PostMapping("/sesiones/iniciar")
    public ResponseEntity<String> iniciarSesion(@RequestBody SesionUsuario sesion) {
        sesion.setInicioSesion(LocalDateTime.now());                      // ADDED: marca hora inicio
        sesionUsuarioService.iniciarSesion(sesion);

        // ADDED: se registra evento de inicio

        UUID uuid = UUID.fromString(sesion.getIdUsuario());
        EventosUsuarioEmbebida clave = new EventosUsuarioEmbebida(uuid, Instant.now());
        EventosUsuario evento = new EventosUsuario(clave, "Inicio", "Inicio de sesión",
                                                   null, null, Instant.now(), null);
        eventosUsuarioService.registrarEvento(evento);

        return ResponseEntity.ok("Sesión iniciada");
    }

    @PostMapping("/sesiones/cerrar/{idUsuario}")
    public ResponseEntity<String> cerrarSesion(@PathVariable String idUsuario) {
        Optional<SesionUsuario> sesionOpt = sesionUsuarioService.obtenerSesion(idUsuario);
        if (sesionOpt.isEmpty()) return ResponseEntity.badRequest().body("Sesión no encontrada");

        SesionUsuario sesion = sesionOpt.get();
        sesion.setUltimaActividad(LocalDateTime.now());

        UUID uuid = UUID.fromString(sesion.getIdUsuario());
        EventosUsuarioEmbebida clave = new EventosUsuarioEmbebida(uuid, Instant.now());
        EventosUsuario evento = new EventosUsuario(
            clave, "Cierre", "Cierre de sesión",
            null, null,
            sesion.getInicioSesion().atZone(java.time.ZoneId.systemDefault()).toInstant(),
            LocalDateTime.now().atZone(java.time.ZoneId.systemDefault()).toInstant()
        );
        eventosUsuarioService.registrarEvento(evento);

        sesionUsuarioService.cerrarSesion(idUsuario);
        return ResponseEntity.ok("Sesión cerrada");
    }

    @GetMapping("/usuarios/categoria/{idUsuario}")
    public ResponseEntity<String> categoriaUsuario(@PathVariable String idUsuario) {
        // (sin cambios de lógica; se deja igual que el original)
        List<EventosUsuario> eventos = eventosUsuarioService.getByUsuarioId(UUID.fromString(idUsuario));

        long totalMinutos = eventos.stream()
            .filter(e -> e.getHoraInicio() != null && e.getHoraFin() != null)
            .mapToLong(e -> Duration.between(e.getHoraInicio(), e.getHoraFin()).toMinutes())
            .sum();

        String categoria = totalMinutos > 240 ? "TOP"
                        : totalMinutos >= 120 ? "MEDIUM"
                        : "LOW";

        return ResponseEntity.ok("El usuario es categoría: " + categoria + " (" + totalMinutos + " min)");
    }
    
    //-----USUARIOS------
    @PostMapping("/usuarios")
    public ResponseEntity<Usuarios> registrarUsuario(@RequestBody Usuarios usuario) {
        return ResponseEntity.ok(usuarioService.registrarUsuario(usuario));
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuarios>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Usuarios> obtenerUsuario(@PathVariable String id) {
        Usuarios usuario = usuarioService.getUsuario(id);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //-----CARRITO------

    @PostMapping("/carrito/{idCarrito}/agregarProducto")
    public ResponseEntity<String> agregarProducto(@PathVariable Integer idCarrito,
                                                  @RequestBody CarritoDetalle detalle) {
        carritoService.agregarProducto(idCarrito, detalle);
        return ResponseEntity.ok("Producto agregado al carrito");
    }

    @PostMapping("/carrito/{idCarrito}/eliminarProducto")
    public ResponseEntity<String> eliminarProducto(@PathVariable Integer idCarrito,
                                                   @RequestParam Integer idProducto) {
        carritoService.eliminarProducto(idCarrito, idProducto);
        return ResponseEntity.ok("Producto eliminado del carrito");
    }

    @PostMapping("/carrito/{idCarrito}/modificarProducto")
    public ResponseEntity<String> modificarProducto(@PathVariable Integer idCarrito,
                                                    @RequestBody CarritoDetalle detalle) {
        carritoService.modificarProducto(idCarrito, detalle);
        return ResponseEntity.ok("Producto modificado en el carrito");
    }

    @PostMapping("/carrito/{idCarrito}/guardarEstado")
    public ResponseEntity<String> guardarEstado(@PathVariable Integer idCarrito) {
        List<CarritoDetalle> detalles = carritoService.obtenerDetallesCarrito(idCarrito);
        carritoSnapshots.put(idCarrito, new ArrayList<>(detalles));
        return ResponseEntity.ok("Estado del carrito guardado");
    }

    @PostMapping("/carrito/{idCarrito}/restaurarEstado")
    public ResponseEntity<String> restaurarEstado(@PathVariable Integer idCarrito) {
        List<CarritoDetalle> snapshot = carritoSnapshots.get(idCarrito);
        if (snapshot == null) return ResponseEntity.badRequest().body("No hay estado guardado");

        carritoService.restaurarEstadoCarrito(idCarrito, snapshot);
        return ResponseEntity.ok("Estado del carrito restaurado");
    }

    @PostMapping("/carrito/{idCarrito}/convertir")
    public ResponseEntity<Pedidos> convertirCarrito(@PathVariable Integer idCarrito) {
        Optional<SesionUsuario> sesion = sesionUsuarioService.obtenerSesion("usuario-id-desde-algún-lugar");
        if (sesion.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // ADDED: se obtiene la sesión del usuario
        SesionUsuario sesionO = sesion.get();

        Usuarios usuario = usuarioService.buscarPorEmail(sesionO.getDocumento());

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Pedidos pedido = carritoService.convertirCarritoEnPedidoConDescuentos(idCarrito, usuario);
        return ResponseEntity.ok(pedido);
    }

    //-----FACTURAS Y PAGOS------
    @PostMapping("/facturas")
    public ResponseEntity<Facturas> registrarFactura(@RequestBody Facturas factura) {
        return ResponseEntity.ok(facturasService.registrarFactura(factura));
    }

    @GetMapping("/facturas")
    public ResponseEntity<List<Facturas>> listarFacturas() {
        return ResponseEntity.ok(facturasService.listarFacturas());
    }

    @PostMapping("/pagos")
    public ResponseEntity<Pagos> registrarPago(@RequestBody Pagos pago) {
        return ResponseEntity.ok(pagosService.registrarPagos(pago));
    }

    @GetMapping("/pagos")
    public ResponseEntity<List<Pagos>> listarPagos() {
        return ResponseEntity.ok(pagosService.listarPagos());
    }

    @GetMapping("/pagos/usuario/{idUsuario}")
    public ResponseEntity<Pagos> pagoPorUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(pagosService.obtenerPagoPorId(idUsuario));
    }

    //-----CATALOGO E HISTORIAL------
    @PostMapping("/catalogo/cambio")
    public ResponseEntity<HistorialCatalogo> registrarCambioCatalogo(@RequestBody HistorialCatalogo cambio) {
        // ADDED: valor anterior/nuevo + operador deben venir seteados en cambio
        return ResponseEntity.ok(historialCatalogoService.registrarCambio(cambio));
    }

    @GetMapping("/catalogo/cambios")
    public ResponseEntity<List<HistorialCatalogo>> listarCambiosCatalogo() {
        return ResponseEntity.ok(historialCatalogoService.listarCambios());
    }

    @GetMapping("/catalogo/precios")                     // ADDED: lista de precios actuales
    public ResponseEntity<Map<String, Double>> listarPreciosActuales() {
        return ResponseEntity.ok(historialCatalogoService.listarPreciosActuales());
    }
        
        
}
