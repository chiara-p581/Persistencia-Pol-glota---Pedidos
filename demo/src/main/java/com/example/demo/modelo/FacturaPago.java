package com.example.demo.modelo;
import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class FacturaPago implements Serializable {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idFactura")
    private Facturas idFactura;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idPago")
    private Pagos idPago;

    public FacturaPago() {
    }

    public FacturaPago(Facturas idFactura, Pagos idPago) {
        this.idFactura = idFactura;
        this.idPago = idPago;
    }

    public Facturas getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Facturas idFactura) {
        this.idFactura = idFactura;
    }

    public Pagos getIdPago() {
        return idPago;
    }

    public void setIdPago(Pagos idPago) {
        this.idPago = idPago;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idFactura == null) ? 0 : idFactura.hashCode());
        result = prime * result + ((idPago == null) ? 0 : idPago.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FacturaPago other = (FacturaPago) obj;
        if (idFactura == null) {
            if (other.idFactura != null)
                return false;
        } else if (!idFactura.equals(other.idFactura))
            return false;
        if (idPago == null) {
            if (other.idPago != null)
                return false;
        } else if (!idPago.equals(other.idPago))
            return false;
        return true;
    }

    
}
