package com.example.demo.modelo;
 
import java.util.List;
 
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
 
@Document(collection = "catalogo")
public class Catalogo {
 
    @Id
    private String idProducto;
    private String nombre;
    @TextIndexed // para búsqueda de texto completo
    private String descripcion;
    private List<String> imagenes; // rutas o base64
    private List<String> videos;
    private List<String> comentarios;
 
    public Catalogo() {
    }
 
    public Catalogo(String idProducto, String nombre, String descripcion, List<String> imagenes, List<String> videos,
            List<String> comentarios) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagenes = imagenes;
        this.videos = videos;
        this.comentarios = comentarios;
    }
 
    public String getIdProducto() {
        return idProducto;
    }
 
    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }
 
    public String getNombre() {
        return nombre;
    }
 
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
 
    public String getDescripcion() {
        return descripcion;
    }
 
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
 
    public List<String> getImagenes() {
        return imagenes;
    }
 
    public void setImagenes(List<String> imagenes) {
        this.imagenes = imagenes;
    }
 
    public List<String> getVideos() {
        return videos;
    }
 
    public void setVideos(List<String> videos) {
        this.videos = videos;
    }
 
    public List<String> getComentarios() {
        return comentarios;
    }
 
    public void setComentarios(List<String> comentarios) {
        this.comentarios = comentarios;
    }
 
   
}
