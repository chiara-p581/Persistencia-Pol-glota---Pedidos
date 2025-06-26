package com.example.demo.servicio;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.datos.CarritoDetalleRepository;
import com.example.demo.datos.CarritoRepository;
import com.example.demo.datos.CarritoVersionRepository;
import com.example.demo.datos.ListaPrecioRepository;
import com.example.demo.datos.PedidosRepository;
import com.example.demo.modelo.Carrito;
import com.example.demo.modelo.CarritoDetalle;
import com.example.demo.modelo.CarritoVersion;
import com.example.demo.modelo.ListaPrecio;
import com.example.demo.modelo.Pedidos;
import com.example.demo.modelo.Usuarios;


@Service
public class CarritoService {
    @Autowired
    private CarritoRepository carritoRepository;
    @Autowired
    private CarritoDetalleRepository carritoDetalleRepository;
    @Autowired
    private PedidosRepository pedidosRepository;
    @Autowired
    private CarritoVersionRepository carritoVersionRepository;
    @Autowired
    private ListaPrecioRepository listaPrecioRepository;

    public Pedidos convertirCarritoEnPedido(Integer idCarrito) {
        //Buscar el carrito y sus detalles
        Carrito carrito = carritoRepository.findById(idCarrito)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        List<CarritoDetalle> detalles = carritoDetalleRepository.findAll()
                .stream()
                .filter(det -> det.getIdCarrito().getIdCarrito().equals(idCarrito))
                .toList();

        //Calcular el total del pedido
        double total = detalles.stream()
                .mapToDouble(det -> det.getCantidad() * det.getPrecioUnitario())
                .sum();

        //Crear el pedido
        Pedidos pedido = new Pedidos();
        pedido.setIdUsuario(carrito.getIdUsuario());
        pedido.setEstado("Pendiente");
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setTotal(total);

        //Guardar el pedido
        pedido = pedidosRepository.save(pedido);

        //Cambiar el estado del carrito a "Convertido"
        carrito.setEstado("Convertido");
        carritoRepository.save(carrito);

        //Eliminar los detalles del carrito 
        for (CarritoDetalle detalle : detalles) {
             carritoDetalleRepository.delete(detalle);
        }

        return pedido;
    }

    public void agregarProducto(Integer idCarrito, CarritoDetalle detalle) {
        // Guardar estado actual antes de agregar
        List<CarritoDetalle> estadoActual = obtenerDetallesCarrito(idCarrito);
        guardarEstadoCarrito(idCarrito, estadoActual);

        Carrito carrito = carritoRepository.findById(idCarrito)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        detalle.setIdCarrito(carrito);
        carritoDetalleRepository.save(detalle);

        // Guardar estado actualizado después de agregar
        List<CarritoDetalle> estadoActualizado = obtenerDetallesCarrito(idCarrito);
        guardarEstadoCarrito(idCarrito, estadoActualizado);
    }

    public void eliminarProducto(Integer idCarrito, Integer idProducto) {
        // Guardar estado actual antes de eliminar
        List<CarritoDetalle> estadoActual = obtenerDetallesCarrito(idCarrito);
        guardarEstadoCarrito(idCarrito, estadoActual);

        List<CarritoDetalle> detalles = carritoDetalleRepository.findAll()
                .stream()
                .filter(det -> det.getIdCarrito().getIdCarrito().equals(idCarrito)
                            && det.getIdProducto().getIdProducto().equals(idProducto))
                .toList();

        detalles.forEach(carritoDetalleRepository::delete);

        // Guardar estado actualizado después de eliminar
        List<CarritoDetalle> estadoActualizado = obtenerDetallesCarrito(idCarrito);
        guardarEstadoCarrito(idCarrito, estadoActualizado);
    }

    public void modificarProducto(Integer idCarrito, CarritoDetalle nuevoDetalle) {
        // Guardar estado actual antes de modificar
        List<CarritoDetalle> estadoActual = obtenerDetallesCarrito(idCarrito);
        guardarEstadoCarrito(idCarrito, estadoActual);

        List<CarritoDetalle> detalles = carritoDetalleRepository.findAll()
                .stream()
                .filter(det -> det.getIdCarrito().getIdCarrito().equals(idCarrito)
                            && det.getIdProducto().getIdProducto().equals(nuevoDetalle.getIdProducto().getIdProducto()))
                .toList();

        for (CarritoDetalle det : detalles) {
            det.setCantidad(nuevoDetalle.getCantidad());
            det.setPrecioUnitario(nuevoDetalle.getPrecioUnitario());
            carritoDetalleRepository.save(det);
        }

        // Guardar estado actualizado después de modificar
        List<CarritoDetalle> estadoActualizado = obtenerDetallesCarrito(idCarrito);
        guardarEstadoCarrito(idCarrito, estadoActualizado);
    }

    public List<CarritoDetalle> obtenerDetallesCarrito(Integer idCarrito) {
        return carritoDetalleRepository.findAll()
                .stream()
                .filter(det -> det.getIdCarrito().getIdCarrito().equals(idCarrito))
                .toList();
    }

    public void restaurarEstadoCarrito(Integer idCarrito, List<CarritoDetalle> snapshot) {
        Carrito carrito = carritoRepository.findById(idCarrito)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        List<CarritoDetalle> actuales = obtenerDetallesCarrito(idCarrito);
        actuales.forEach(carritoDetalleRepository::delete);

        for (CarritoDetalle viejo : snapshot) {
            CarritoDetalle nuevo = new CarritoDetalle();
            nuevo.setIdCarrito(carrito);
            nuevo.setIdProducto(viejo.getIdProducto());
            nuevo.setCantidad(viejo.getCantidad());
            nuevo.setPrecioUnitario(viejo.getPrecioUnitario());

            carritoDetalleRepository.save(nuevo);
        }
    }

    public Pedidos convertirCarritoEnPedidoConDescuentos(Integer idCarrito, Usuarios usuario) {
        Carrito carrito = carritoRepository.findById(idCarrito)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        List<CarritoDetalle> detalles = obtenerDetallesCarrito(idCarrito);

        double subtotal = detalles.stream()
                .mapToDouble(det -> det.getCantidad() * det.getPrecioUnitario())
                .sum();

        double descuento = detalles.size() > 3 ? subtotal * 0.1 : 0;
        double totalConDescuento = subtotal - descuento;

        double ivaRate = switch (usuario.getCondicionIVA().toUpperCase()) {
            case "RESPONSABLE INSCRIPTO" -> 0.21;
            case "MONOTRIBUTISTA", "EXENTO" -> 0.0;
            default -> 0.21;
        };

        double impuestoIVA = totalConDescuento * ivaRate;
        double totalFinal = totalConDescuento + impuestoIVA;

        Pedidos pedido = new Pedidos();
        pedido.setIdUsuario(usuario);
        pedido.setEstado("Pendiente");
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setTotal(totalFinal); // <--- ¡Ahora guarda el monto final correcto!

        pedido = pedidosRepository.save(pedido);

        carrito.setEstado("Convertido");
        carritoRepository.save(carrito);

        detalles.forEach(carritoDetalleRepository::delete);

        return pedido;
    }

    public Carrito obtenerOCrearCarritoActivo(Usuarios usuario) {
        // Busca un carrito activo para el usuario
        Carrito carrito = carritoRepository.findByIdUsuarioAndEstado(usuario, "Activo");
        if (carrito == null) {
            carrito = new Carrito();
            carrito.setIdUsuario(usuario);
            carrito.setEstado("Activo");
            carrito = carritoRepository.save(carrito);
        }
        return carrito;
    }   

    public void guardarEstadoCarrito(Integer idCarrito, List<CarritoDetalle> detalles) {
        Carrito carrito = carritoRepository.findById(idCarrito)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        String idUsuario = carrito.getIdUsuario().getIdUsuario().toString();

        // Convertir los detalles del carrito a Map<String, Integer>
        Map<String, Integer> productos = detalles.stream()
            .collect(Collectors.toMap(
                det -> det.getIdProducto().getIdProducto().toString(),
                CarritoDetalle::getCantidad,
                Integer::sum // Si hay claves duplicadas, suma cantidades
            ));

        // Guardar versión en Redis
        CarritoVersion version = new CarritoVersion(idUsuario, productos);
        carritoVersionRepository.save(version);
    }

    public void restaurarDesdeVersionRedis(String versionId, Integer idCarrito) {
        CarritoVersion version = carritoVersionRepository.findById(versionId)
                .orElseThrow(() -> new RuntimeException("Versión no encontrada"));

        Carrito carrito = carritoRepository.findById(idCarrito)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        // ✅ Eliminar todos los detalles actuales
        List<CarritoDetalle> actuales = obtenerDetallesCarrito(idCarrito);
        actuales.forEach(carritoDetalleRepository::delete); // <- este cambio es importante

        // ✅ Restaurar los productos del snapshot
        for (Map.Entry<String, Integer> entry : version.getProductos().entrySet()) {
            Integer idProducto = Integer.valueOf(entry.getKey());
            Integer cantidad = entry.getValue();

            ListaPrecio producto = listaPrecioRepository.findById(idProducto)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            CarritoDetalle nuevo = new CarritoDetalle();
            nuevo.setIdCarrito(carrito);
            nuevo.setIdProducto(producto);
            nuevo.setCantidad(cantidad);
            nuevo.setPrecioUnitario(producto.getPrecioActual());

            carritoDetalleRepository.save(nuevo);
        }
    }

}