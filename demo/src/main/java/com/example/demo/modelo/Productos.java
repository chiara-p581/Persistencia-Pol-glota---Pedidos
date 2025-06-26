package com.example.demo.modelo;
 
import java.util.Date;
import java.util.List;
 
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
 
@Document(collection = "productos")
public class Productos {
    @Id
    private ObjectId id;
    private String nombre;
    private String descripcion;
    private String marca;
    private String categoria;
    private List<String> imagenes;
    private List<String> videos;
    private List<Comentario> comentarios;
    private Double  precioActual;
    private List<HistorialPrecio> historialPrecios;
 
    public ObjectId getId() { return id; }
    public void setId(ObjectId id) { this.id = id; }
 
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
 
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
 
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
 
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
 
    public List<String> getImagenes() { return imagenes; }
    public void setImagenes(List<String> imagenes) { this.imagenes = imagenes; }
 
    public List<String> getVideos() { return videos; }
    public void setVideos(List<String> videos) { this.videos = videos; }
 
    public List<Comentario> getComentarios() { return comentarios; }
    public void setComentarios(List<Comentario> comentarios) { this.comentarios = comentarios; }
 
    public Double getPrecioActual() { return precioActual; }
    public void setPrecioActual(Double precio_actual) { this.precioActual = precio_actual; }
 
    public List<HistorialPrecio> getHistorial_precios() { return historialPrecios; }
    public void setHistorial_precios(List<HistorialPrecio> historial_precios) { this.historialPrecios = historial_precios; }
 
 
    public static class Comentario {
        private ObjectId usuarioId;
        private String texto;
        private Date fecha;
 
        public ObjectId getUsuarioId() { return usuarioId; }
        public void setUsuarioId(ObjectId usuario_id) { this.usuarioId = usuario_id; }
 
        public String getTexto() { return texto; }
        public void setTexto(String texto) { this.texto = texto; }
 
        public Date getFecha() { return fecha; }
        public void setFecha(Date fecha) { this.fecha = fecha; }
    }
 
    public static class PrecioActual {
        private String moneda;
        private Double valor;
        private Date fechaInicio;
 
        public String getMoneda() { return moneda; }
        public void setMoneda(String moneda) { this.moneda = moneda; }
 
        public Double getValor() { return valor; }
        public void setValor(Double valor) { this.valor = valor; }
 
        public Date getFechaInicio() { return fechaInicio; }
        public void setFechaInicio(Date fecha_inicio) { this.fechaInicio = fecha_inicio; }
    }
 
    public static class HistorialPrecio {
        private Double valorAnterior;
        private Double valorNuevo;
        private Date fecha;
        private String modificadoPor;
 
        public Double getValor_anterior() { return valorAnterior; }
        public void setValor_anterior(Double valor_anterior) { this.valorAnterior = valor_anterior; }
 
        public Double getValorNuevo() { return valorNuevo; }
        public void setValorNuevo(Double valor_nuevo) { this.valorNuevo = valor_nuevo; }
 
        public Date getFecha() { return fecha; }
        public void setFecha(Date fecha) { this.fecha = fecha; }
 
        public String getModificadoPor() { return modificadoPor; }
        public void setModificadoPor(String modificado_por) { this.modificadoPor = modificado_por; }
    }
}
 