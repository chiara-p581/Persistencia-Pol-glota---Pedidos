package com.example.demo.modelo;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "historialCatalogo")
public class HistorialCatalogo {
    @Id
    private ObjectId id;
    private ObjectId productoId;
    private String campoModificado;
    private Double valorAnterior;
    private Double valorNuevo;
    private String modificadoPor;
    private Date fecha;
 
 
    public ObjectId getId() { return id; }
    public void setId(ObjectId id) { this.id = id; }
 
    public ObjectId getProducto_id() { return productoId; }
    public void setProducto_id(ObjectId producto_id) { this.productoId = producto_id; }
 
    public String getCampoModificado() { return campoModificado; }
    public void setCampoModificado(String campo_modificado) { this.campoModificado = campo_modificado; }
 
    public Double getValorAnterior() { return valorAnterior; }
    public void setValorAnterior(Double valor_anterior) { this.valorAnterior = valor_anterior; }
 
    public Double getValorNuevo() { return valorNuevo; }
    public void setValorNuevo(Double valor_nuevo) { this.valorNuevo = valor_nuevo; }
 
    public String getModificadoPor() { return modificadoPor; }
    public void setModificadoPor(String modificado_por) { this.modificadoPor = modificado_por; }
 
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
}
