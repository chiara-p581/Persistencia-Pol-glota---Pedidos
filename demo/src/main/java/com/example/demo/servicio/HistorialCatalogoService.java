package com.example.demo.servicio;
 
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
 
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.example.demo.datos.HistorialCatalogoRepository;
import com.example.demo.datos.ListaPrecioRepository;
import com.example.demo.modelo.HistorialCatalogo;
import com.example.demo.modelo.ListaPrecio;
import com.example.demo.modelo.Usuarios;
 
@Service
public class HistorialCatalogoService {
    @Autowired
    private HistorialCatalogoRepository historialCatalogoRepository;
 
    @Autowired
    private ListaPrecioRepository listaPrecioRepository;
 
    public HistorialCatalogo registrarCambio(HistorialCatalogo cambio) {
        return historialCatalogoRepository.save(cambio);
    }
 
    public List<HistorialCatalogo> listarCambios() {
        return historialCatalogoRepository.findAll();
    }
 
    public Map<String, Double> listarPreciosActuales() {
    List<HistorialCatalogo> cambios = historialCatalogoRepository.findAll();
 
    return cambios.stream()
        .collect(Collectors.groupingBy(
            c -> c.getProducto_id().toHexString(), // convertimos ObjectId a String
            Collectors.collectingAndThen(
                Collectors.maxBy(Comparator.comparing(HistorialCatalogo::getFecha)),
                opt -> opt.map(HistorialCatalogo::getValorNuevo).orElse(0.0)
            )
        ));
    }
 
    public List<ListaPrecio> listarProductos() {
        return listaPrecioRepository.findAll();
    }
 
 
    public void cambiarPrecio(String nombre, double nuevoPrecio, Usuarios operador){
       
    }
 
    public void registrarCambio(ObjectId productoId, String campo, Double valorAnterior, Double valorNuevo, String operador) {
        HistorialCatalogo cambio = new HistorialCatalogo();
        cambio.setProducto_id(productoId);
        cambio.setCampoModificado(campo);
        cambio.setValorAnterior(valorAnterior);
        cambio.setValorNuevo(valorNuevo);
        cambio.setModificadoPor(operador);
        cambio.setFecha(new Date());
 
        historialCatalogoRepository.save(cambio);
    }

    @Override
    public String toString() {
        return "HistorialCatalogoService [historialCatalogoRepository=" + historialCatalogoRepository
                + ", listaPrecioRepository=" + listaPrecioRepository + ", listarCambios()=" + listarCambios()
                + ", listarPreciosActuales()=" + listarPreciosActuales() + ", getClass()=" + getClass()
                + ", listarProductos()=" + listarProductos() + ", hashCode()=" + hashCode() + ", toString()="
                + super.toString() + "]";
    }

    
 
 
}