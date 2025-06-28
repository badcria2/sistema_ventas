/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;
 

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Venta implements Serializable {
    private static final long serialVersionUID = 1L;

    public String cliente;
    public LocalDate fecha;
    public List<ItemVenta> items;
    public double total;

    public Venta(String cliente, List<ItemVenta> items, double total) {
        this.cliente = cliente;
        this.fecha = LocalDate.now();
        this.items = items;
        this.total = total;
    }

    @Override
    public String toString() {
        return "Cliente: " + cliente + ", Fecha: " + fecha + ", Total: S/ " + total;
    }
}
