package com.example.demo.modelo;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "facturaPago")
public class FacturaPagoEmbebida {
    @EmbeddedId
    private FacturaPago id;

    public FacturaPagoEmbebida() {}

    public FacturaPagoEmbebida(FacturaPago id) {
        this.id = id;
    }

    public FacturaPago getId() {
        return id;
    }

    public void setId(FacturaPago id) {
        this.id = id;
    }


}
