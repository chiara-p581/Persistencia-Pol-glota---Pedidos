package com.example.demo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.example.demo.modelo.Carrito;
import com.example.demo.modelo.CarritoDetalle;
import com.example.demo.modelo.Facturas;
import com.example.demo.modelo.ListaPrecio;
import com.example.demo.modelo.Pagos;
import com.example.demo.modelo.Pedidos;
import com.example.demo.modelo.Productos;
import com.example.demo.modelo.Usuarios;
import com.example.demo.servicio.CarritoService;
import com.example.demo.servicio.CatalogoService;
import com.example.demo.servicio.EventosUsuarioService;
import com.example.demo.servicio.FacturasService;
import com.example.demo.servicio.HistorialCatalogoService;
import com.example.demo.servicio.PagosService;
import com.example.demo.servicio.UsuarioService;

@SpringBootApplication
public class DemoApplication {
 
    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(DemoApplication.class, args);
 
        UsuarioService usuarioService = context.getBean(UsuarioService.class);
        CarritoService carritoService = context.getBean(CarritoService.class);
        EventosUsuarioService eventosUsuarioService = context.getBean(EventosUsuarioService.class);
        FacturasService facturasService = context.getBean(FacturasService.class);
        HistorialCatalogoService historialCatalogoService = context.getBean(HistorialCatalogoService.class);
        CatalogoService catalogoService = context.getBean(CatalogoService.class);
        PagosService pagosService = context.getBean(PagosService.class);
 
 
        try (Scanner scanner = new Scanner(System.in)) {
            boolean salir = false;
            Usuarios sesionActual = null;
            
            
            while (!salir) {
                System.out.println("=== MENÚ PRINCIPAL ===");
                System.out.println("1. Registrar usuario");
                System.out.println("2. Iniciar sesión");
                System.out.println("3. Agregar producto al carrito");
                System.out.println("4. Ver carrito");
                System.out.println("5. Modificar producto del carrito");
                System.out.println("6. Eliminar producto del carrito");
                System.out.println("7. Ver historial de versiones del carrito");
                System.out.println("8. Convertir carrito en pedido");
                System.out.println("9. Facturar pedido");
                System.out.println("10. Registrar pago");
                System.out.println("11. Ver historial de facturación y pagos");
                System.out.println("12. Ver catálogo de productos");
                System.out.println("13. Ver/cambiar lista de precios");
                System.out.println("14. Ver historial de cambios del catálogo");
                System.out.println("15. Ver/categorizar actividad de usuario");
                System.out.println("16. Agregar nuevo producto (ADMIN)");
                System.out.println("17. Salir");
                System.out.print("Seleccione una opción: ");
                int opcion = scanner.nextInt();
                scanner.nextLine();
                
                switch (opcion) {
                    case 1 -> {
                        //Registrar Usuario
                        System.out.print("Nombre: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Apellido: ");
                        String apellido = scanner.nextLine();
                        System.out.print("Dirección: ");
                        String direccion = scanner.nextLine();
                        System.out.print("Documento: ");
                        int documento = scanner.nextInt();
                        scanner.nextLine(); // LIMPIA EL BUFFER AQUÍ
                        System.out.print("Condición IVA: ");
                        String condicionIVA = scanner.nextLine();
                        System.out.print("Email: ");
                        String email = scanner.nextLine();
                        System.out.print("Contraseña: ");
                        String contrasenia = scanner.nextLine();
                        System.out.print("Tipo de Usuario (Cliente/Administrador): ");
                        String tipoUsuario = scanner.nextLine();
                        
                        Usuarios usuario = new Usuarios();
                        usuario.setNombre(nombre);
                        usuario.setApellido(apellido);
                        usuario.setDireccion(direccion);
                        usuario.setDocumento(documento);
                        usuario.setCondicionIVA(condicionIVA);
                        usuario.setEmail(email);
                        usuario.setContrasenia(contrasenia);
                        usuario.setTipoUsuario(tipoUsuario);
                        usuarioService.registrarUsuario(usuario);
                        System.out.println();
                        System.out.println("Usuario registrado.");
                        System.out.println();
                    }
                    
                    case 2 -> { //cambié esto 11:15, ahora cierra sesión si ya hay un usuario logueado
                        // Verificar si ya hay un usuario logueado
                        // Login e inicio de sesión
                        if (sesionActual != null) {
                            System.out.println("Ya hay un usuario logueado: " + sesionActual.getNombre());
                            System.out.print("¿Deseas cerrar esta sesión y loguearte con otro usuario? (s/n): ");
                            String respuesta = scanner.nextLine().trim().toLowerCase();
                            
                            if (!respuesta.equals("s")) {
                                System.out.println("Operación cancelada. Continúas como " + sesionActual.getNombre() + ".");
                                break;
                            }
                            
                            // Registrar logout del usuario actual
                            eventosUsuarioService.registrarEventoLogout(sesionActual);
                            System.out.println("Sesión cerrada para: " + sesionActual.getNombre());
                            sesionActual = null;
                        }
                        
                        // Solicitar credenciales
                        System.out.print("Email: ");
                        String emailLogin = scanner.nextLine();
                        System.out.print("Contraseña: ");
                        String contraseniaLogin = scanner.nextLine();
                        
                        Usuarios usuarioLogueado = usuarioService.buscarPorEmail(emailLogin);
                        if (usuarioLogueado != null && usuarioLogueado.getContrasenia().equals(contraseniaLogin)) {
                            System.out.println("\n¡Login Exitoso! Bienvenido, " + usuarioLogueado.getNombre() + "\n");
                            eventosUsuarioService.registrarEventoLogin(usuarioLogueado);
                            sesionActual = usuarioLogueado;
                        } else {
                            System.out.println("Email o contraseña incorrectos.");
                        }
                    }
                    
                    case 3 -> {
                        //Agregar producto al carrito
                        if (sesionActual == null) {
                            System.out.println("Debes iniciar sesión primero.");
                            break;
                        }
                        
                        
                        System.out.print("Nombre del producto: ");
                        String nombreProducto = scanner.nextLine().trim();
                        
                        System.out.print("Cantidad: ");
                        int cantidad = scanner.nextInt();
                        scanner.nextLine();
                        
                        try {
                            List<ListaPrecio> productosListaPrecio = catalogoService.buscarPorNombre(nombreProducto);
                            
                            if (productosListaPrecio.isEmpty()) {
                                throw new RuntimeException("Producto no encontrado.");
                            }
                            
                            ListaPrecio producto = productosListaPrecio.get(0);
                            
                            Carrito carrito = carritoService.obtenerOCrearCarritoActivo(sesionActual);
                            CarritoDetalle detalle = new CarritoDetalle();
                            detalle.setIdCarrito(carrito);
                            detalle.setIdProducto(producto);
                            detalle.setCantidad(cantidad);
                            detalle.setPrecioUnitario(producto.getPrecioActual());
                            
                            carritoService.agregarProducto(carrito.getIdCarrito(), detalle);
                            
                            System.out.println("Producto agregado al carrito.");
                            
                            
                            
                        } catch (RuntimeException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                    
                    case 4 -> {
                        //Ver carrito
                        if (sesionActual == null) {
                            System.out.println();
                            System.out.println("Debes iniciar sesión primero.");
                            System.out.println();
                            break;
                        }
                        Carrito carritoVer = carritoService.obtenerOCrearCarritoActivo(sesionActual);
                        List<CarritoDetalle> detalles = carritoService.obtenerDetallesCarrito(carritoVer.getIdCarrito());
                        System.out.println();
                        System.out.println("Carrito actual:");
                        for (CarritoDetalle det : detalles) {
                            System.out.println("Producto: " + det.getIdProducto().getNombre() +
                                    " | Cantidad: " + det.getCantidad() +
                                    " | Precio unitario: " + det.getPrecioUnitario());
                        }
                    }
                    
                    case 5 -> {
                        //Modificar/Eliminar producto del carrito
                        if (sesionActual == null) {
                            System.out.println();
                            System.out.println("Debes iniciar sesión primero.");
                            System.out.println();
                            break;
                        }
                        
                        Carrito carritoMod = carritoService.obtenerOCrearCarritoActivo(sesionActual);
                        
                        System.out.print("Nombre del producto a modificar: ");
                        String nombreProdMod = scanner.nextLine().trim();
                        
                        System.out.print("Nueva cantidad : ");
                        int nuevaCantidad = scanner.nextInt();
                        scanner.nextLine();
                        
                        if (nuevaCantidad <= 0) {
                            System.out.println();
                            System.out.println("La cantidad debe ser mayor a cero para modificar.");
                            System.out.println();
                        } else {
                            List<ListaPrecio> resultados = catalogoService.buscarPorNombre(nombreProdMod);
                            if (resultados.isEmpty()) {
                                System.out.println("Producto no encontrado.");
                                break;
                            }
                            
                            ListaPrecio productoMod = resultados.get(0);
                            
                            CarritoDetalle detalleMod = new CarritoDetalle();
                            detalleMod.setIdProducto(productoMod);
                            detalleMod.setCantidad(nuevaCantidad);
                            detalleMod.setPrecioUnitario(productoMod.getPrecioActual()); //agregué esto
                            
                            carritoService.modificarProducto(carritoMod.getIdCarrito(), detalleMod);
                            System.out.println();
                            System.out.println("Cantidad modificada.");
                            System.out.println();
                        }
                    }
                    
                    case 6 -> {
                        // Eliminar producto del carrito
                        if (sesionActual == null) {
                            System.out.println();
                            System.out.println("Debes iniciar sesión primero.");
                            System.out.println();
                            break;
                        }
                        
                        Carrito carritoEliminar = carritoService.obtenerOCrearCarritoActivo(sesionActual);
                        
                        System.out.print("Nombre del producto a eliminar: ");
                        String nombreProdEliminar = scanner.nextLine().trim();
                        
                        List<ListaPrecio> resultados = catalogoService.buscarPorNombre(nombreProdEliminar);
                        if (resultados.isEmpty()) {
                            System.out.println("Producto no encontrado.");
                            break;
                        }
                        
                        ListaPrecio productoEliminar = resultados.get(0); // Podés mostrar opciones si hay más de uno
                        
                        carritoService.eliminarProducto(carritoEliminar.getIdCarrito(), productoEliminar.getIdProducto());
                        
                        System.out.println();
                        System.out.println("Producto eliminado del carrito.");
                        System.out.println();
                    }
                    
                    case 7 -> {
                        // Ver historial de versiones del carrito (snapshots)
                        if (sesionActual == null) {
                            System.out.println();
                            System.out.println("Debes iniciar sesión primero.");
                            break;
                        }
                        
                        Carrito carritoHist = carritoService.obtenerOCrearCarritoActivo(sesionActual);
                        
                        System.out.print("¿Desea guardar el estado actual del carrito? (s/n): ");
                        String guardar = scanner.nextLine();
                        if (guardar.equalsIgnoreCase("s")) {
                            List<CarritoDetalle> snapshot = carritoService.obtenerDetallesCarrito(carritoHist.getIdCarrito());
                            carritoService.guardarEstadoCarrito(carritoHist.getIdCarrito(), snapshot);
                            System.out.println("Estado del carrito guardado en Redis.");
                        }
                        
                        System.out.print("¿Desea restaurar el último estado guardado? (s/n): ");
                        String resp = scanner.nextLine();
                        if (resp.equalsIgnoreCase("s")) {
                            String versionId = carritoHist.getIdUsuario().getIdUsuario().toString();
                            try {
                                carritoService.restaurarDesdeVersionRedis(versionId, carritoHist.getIdCarrito());
                                System.out.println();
                                System.out.println("Estado del carrito restaurado desde Redis.");
                            } catch (RuntimeException e) {
                                System.out.println();
                                System.out.println("No hay estado guardado en Redis para este carrito.");
                            }
                        }
                    }
                    
                    case 8 -> {
                        // Convertir carrito en pedido
                        if (sesionActual == null) {
                            System.out.println("\nDebes iniciar sesión primero.\n");
                            break;
                        }
                        Carrito carritoPedido = carritoService.obtenerOCrearCarritoActivo(sesionActual);
                        
                        // Obtener detalles antes de convertir
                        List<CarritoDetalle> detalles = carritoService.obtenerDetallesCarrito(carritoPedido.getIdCarrito());
                        
                        // Convertir carrito en pedido (vacía carrito y crea pedido)
                        Pedidos pedido = carritoService.convertirCarritoEnPedidoConDescuentos(carritoPedido.getIdCarrito(), sesionActual);
                        
                        System.out.println("\nPedido generado con ID: " + pedido.getIdPedido());
                        System.out.println("Datos del cliente:");
                        System.out.println("Nombre: " + sesionActual.getNombre() + " " + sesionActual.getApellido());
                        System.out.println("Dirección: " + sesionActual.getDireccion());
                        System.out.println("Condición IVA: " + sesionActual.getCondicionIVA());
                        
                        System.out.println("\nDetalle del pedido:");
                        detalles.forEach(det -> {
                            String producto = det.getIdProducto().getNombre();
                            int cantidad = det.getCantidad();
                            double precioUnit = det.getPrecioUnitario();
                            System.out.printf("Producto: %s | Cantidad: %d | Precio unitario: %.2f\n", producto, cantidad, precioUnit);
                        });
                        
                        double subtotal = detalles.stream()
                                .mapToDouble(d -> d.getCantidad() * d.getPrecioUnitario())
                                .sum();
                        
                        double descuento = detalles.size() > 3 ? subtotal * 0.1 : 0;
                        double totalConDescuento = subtotal - descuento;
                        
                        double ivaRate = switch (sesionActual.getCondicionIVA().toUpperCase()) {
                            case "RESPONSABLE INSCRIPTO" -> 0.21;
                            case "MONOTRIBUTISTA", "EXENTO" -> 0.0;
                            default -> 0.21;
                        };
                        
                        double iva = totalConDescuento * ivaRate;
                        double totalFinal = totalConDescuento + iva;
                        
                        System.out.printf("\nSubtotal: $%.2f\n", subtotal);
                        System.out.printf("Descuento: $%.2f\n", descuento);
                        System.out.printf("IVA (%.0f%%): $%.2f\n", ivaRate * 100, iva);
                        System.out.printf("Total final: $%.2f\n\n", totalFinal);
                    }
                    
                    case 9 -> {
                        //Facturar pedido
                        if (sesionActual == null) {
                            System.out.println();
                            System.out.println("Debes iniciar sesión primero.");
                            System.out.println();
                            break;
                        }
                        System.out.print("ID del pedido a facturar: ");
                        int idPedidoFact = scanner.nextInt();
                        scanner.nextLine();  // LIMPIA EL BUFFER AQUÍ
                        Facturas factura = facturasService.facturarPedido(idPedidoFact);
                        System.out.println();
                        System.out.println("Factura generada con ID: " + factura.getIdFactura());
                        System.out.println();
                    }
                    
                    case 10 -> {
                        if (sesionActual == null) {
                            System.out.println("\nDebes iniciar sesión primero.\n");
                            break;
                        }
                        // NO limpiar buffer acá
                        
                        System.out.print("IDs de facturas a pagar (separadas por coma): ");
                        String linea = scanner.nextLine();
                        
                        List<Integer> idsFacturas;
                        try {
                            idsFacturas = Arrays.stream(linea.split(","))
                                    .map(String::trim)
                                    .filter(s -> !s.isEmpty())
                                    .map(Integer::parseInt)
                                    .toList();
                        } catch (NumberFormatException e) {
                            System.out.println("\nError: Uno de los IDs no es válido.\n");
                            break;
                        }
                        
                        if (idsFacturas.isEmpty()) {
                            System.out.println("\nNo ingresaste ningún ID.\n");
                            break;
                        }
                        
                        System.out.print("Forma de pago: ");
                        String formaPago = scanner.nextLine();
                        
                        System.out.print("Operador (si aplica): ");
                        String operador = scanner.nextLine();
                        
                        System.out.print("Punto de retiro (vacío si no aplica): ");
                        String puntoRetiro = scanner.nextLine();
                        
                        Pagos pago = new Pagos();
                        pago.setIdUsuario(sesionActual);
                        pago.setFormaPago(formaPago);
                        pago.setOperador(operador);
                        pago.setPuntoRetiro(puntoRetiro);
                        pago.setFechaPago(LocalDateTime.now());
                        
                        try {
                            Pagos resultado = pagosService.registrarPagoMultiple(pago, idsFacturas);
                            System.out.println("\nPago registrado con ID " + resultado.getIdPago() +
                                    " — Total $" + resultado.getMontoTotal() + "\n");
                        } catch (IllegalArgumentException e) {
                            System.out.println("\nError: " + e.getMessage() + "\n");
                        }
                    }
                    
                    case 11 -> {
                        //Ver historial de facturación y pagos
                        if (sesionActual == null) {
                            System.out.println();
                            System.out.println("Debes iniciar sesión primero.");
                            System.out.println();
                        }
                        System.out.println("Facturas:");
                        for (Facturas f : facturasService.listarFacturasPorUsuario(sesionActual)) {
                            System.out.println("ID: " + f.getIdFactura() + " | Total: " + f.getTotalFinal());
                        }
                        System.out.println("Pagos:");
                        for (Pagos p : pagosService.pagosPorUsuario(sesionActual)) {
                            System.out.println("ID: " + p.getIdPago() + " | Monto: " + p.getMontoTotal());
                        }
                    }
                    
                    case 12 -> {
                        // Ver catálogo de productos
                        List<Productos> productos = catalogoService.obtenerTodosLosProductos();
                        for (Productos p : productos) {
                            System.out.println("Nombre: " + p.getNombre());
                            System.out.println("Descripción: " + p.getDescripcion());
                            System.out.println("Marca: " + p.getMarca());
                            System.out.println("Categoría: " + p.getCategoria());
                            System.out.println("Precio actual: $" + p.getPrecioActual());
                            
                            System.out.println("Imágenes:");
                            p.getImagenes().forEach(img -> System.out.println(" - " + img));
                            
                            System.out.println("Videos:");
                            p.getVideos().forEach(video -> System.out.println(" - " + video));
                            
                            System.out.println("Comentarios:");
                            p.getComentarios().forEach(c -> System.out.println(" - " + c.getUsuarioId() + ": " + c.getTexto()));
                            
                            System.out.println("---------------------------------------------------");
                        }
                    }
                    
                    case 13 -> {
                        // Ver/Cambiar lista de precios
                        if (sesionActual == null) {
                            System.out.println("\nDebes iniciar sesión primero.\n");
                            break;
                        }
                        
                        System.out.println("Actualizando precios automáticamente con valores aleatorios...");
                        Random random = new Random();
                        
                        List<ListaPrecio> productos = historialCatalogoService.listarProductos();
                        
                        for (ListaPrecio producto : productos) {
                            double precioViejo = producto.getPrecioActual();
                            double nuevoPrecio = 100 + random.nextInt(900); // entre 100 y 1000
                            
                            historialCatalogoService.cambiarPrecio(String.valueOf(producto.getIdProducto()), nuevoPrecio, sesionActual);
                            
                            System.out.printf("ID: %d | Producto: %s | Precio: $%.2f\n",
                                    producto.getIdProducto(), producto.getNombre(), precioViejo);
                        }
                        
                        System.out.println("\nTodos los precios fueron actualizados.\n");
                    }
                    
                    
                    case 14 -> //Ver historial de cambios del catálogo
                        historialCatalogoService.listarCambios().forEach(cambio -> {
                            
                            System.out.println(cambio.getProducto_id().toHexString() + " | " +
                                    cambio.getCampoModificado() + " | " +
                                    cambio.getValorAnterior() + " -> " +
                                    cambio.getValorNuevo() + " | " +
                                    cambio.getModificadoPor() + " | " +
                                    cambio.getFecha());
                        });
                        
                    case 15 -> { //cambié esto 11:30
                        // Ver/Categorizar actividad de usuario
                        if (sesionActual == null) {
                            System.out.println();
                            System.out.println("Debes iniciar sesión primero.");
                            System.out.println();
                            return; // Detiene la ejecución de este case
                        }
                        
                        String categoria = eventosUsuarioService.categoriaUsuario(sesionActual.getUuid());
                        System.out.println("Categoría de usuario: " + categoria);
                    }
                    
                    case 16 -> {
                        //Agregar nuevo producto (ADMIN)
                        if (sesionActual == null || !"Administrador".equalsIgnoreCase(sesionActual.getTipoUsuario())) {
                            System.out.println();
                            System.out.println("Acceso denegado. Solo los administradores pueden agregar productos.");
                            System.out.println();
                            break;
                        }
                        System.out.println("=== Agregar nuevo producto ===");
                        System.out.print("Nombre: ");
                        String nombreProd = scanner.nextLine();
                        System.out.print("Descripción: ");
                        String descripcion = scanner.nextLine();
                        System.out.print("Marca: ");
                        String marca = scanner.nextLine();
                        System.out.print("Categoría: ");
                        String categoria = scanner.nextLine();
                        System.out.print("Precio actual: ");
                        double precio = scanner.nextDouble();
                        scanner.nextLine(); // limpiar buffer
                        List<String> imagenes = new ArrayList<>();
                        System.out.println("Ingresá las URLs de las imágenes (escribí 'fin' para terminar):");
                        while (true) {
                            String img = scanner.nextLine();
                            if (img.equalsIgnoreCase("fin")) break;
                            imagenes.add(img);
                        }
                        List<String> videos = new ArrayList<>();
                        System.out.println("Ingresá las URLs de los videos (escribí 'fin' para terminar):");
                        while (true) {
                            String video = scanner.nextLine();
                            if (video.equalsIgnoreCase("fin")) break;
                            videos.add(video);
                        }
                        Productos nuevo = new Productos();
                        nuevo.setNombre(nombreProd);
                        nuevo.setDescripcion(descripcion);
                        nuevo.setMarca(marca);
                        nuevo.setCategoria(categoria);
                        nuevo.setPrecioActual(precio);
                        nuevo.setImagenes(imagenes);
                        nuevo.setVideos(videos);
                        nuevo.setComentarios(new ArrayList<>());
                        nuevo.setHistorial_precios(new ArrayList<>());
                        catalogoService.agregarProducto(nuevo, sesionActual.getNombre());
                        System.out.println();
                        System.out.println("Producto agregado correctamente.");
                        System.out.println();
                    }
                    
                    case 17 -> { //modifiqué esto 12:11
                        if (sesionActual != null) {
                            eventosUsuarioService.registrarEventoLogout(sesionActual);
                            System.out.println("Sesión cerrada automáticamente para: " + sesionActual.getNombre());
                            sesionActual = null;
                        }
                        salir = true;
                    }
                    
                    default -> {
                        System.out.println();
                        System.out.println("Opción inválida.");
                    }
                }
            }
        }
        System.out.println();
        System.out.println("¡Hasta luego!");
    }
}