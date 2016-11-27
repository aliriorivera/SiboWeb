/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package control;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author AnBoCa
 */
public class Pedido implements Serializable{

    private int eventoId;
    private String presentacion;
    private String ubicacion;
    private String metodoPago;
    private int locacionId;
    private int cantBoletas;
    private double precio;
    private double total=0;
    private String tipoCompra;

    /**
     * @return the locacion
     */
    public int getLocacion() {
        return locacionId;
    }

    /**
     * @param locacion the locacion to set
     */
    public void setLocacion(int locacion) {
        this.locacionId = locacion;
    }

    /**
     * @return the cantBoletas
     */
    public int getCantBoletas() {
        return cantBoletas;
    }

    /**
     * @param cantBoletas the cantBoletas to set
     */
    public void setCantBoletas(int cantBoletas) {
        this.cantBoletas = cantBoletas;
    }

    /**
     * @return the tipoCompra
     */
    public String getTipoCompra() {
        return tipoCompra;
    }

    /**
     * @param tipoCompra the tipoCompra to set
     */
    public void setTipoCompra(String tipoCompra) {
        this.tipoCompra = tipoCompra;
    }

    /**
     * @return the evento
     */
    public int getEvento() {
        return eventoId;
    }

    /**
     * @param evento the evento to set
     */
    public void setEvento(int evento) {
        this.eventoId = evento;
    }

    /**
     * @return the presentacion
     */
    public String getPresentacion() {
        return presentacion;
    }

    /**
     * @param presentacion the presentacion to set
     */
    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    /**
     * @return the precio
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * @return the total
     */
    public double getTotal() {
        return total;
    }

    /**
     * @return the metodoPago
     */
    public String getMetodoPago() {
        return metodoPago;
    }

    /**
     * @param metodoPago the metodoPago to set
     */
    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    /**
     * @return the ubicacion
     */
    public String getUbicacion() {
        return ubicacion;
    }

    /**
     * @param ubicacion the ubicacion to set
     */
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void calcularPrecio(){
        if (this.tipoCompra.equals("comprar"))
            this.total=this.cantBoletas*this.getPrecio();
        if (this.tipoCompra.equals("reservar"))
            this.total=this.cantBoletas*this.getPrecio()*25/100;
    }
}
