package Entity;

import java.io.Serializable;

public class Producto implements Serializable {
private static final long serialVersionUID = 7034503953926703649L;

    public String codigo;
    public String nombre;
    public double precio;
    public int stock;
    public String categoria;
    public String descripcion;

    public Producto(String codigo, String nombre, double precio, int stock, String categoria, String descripcion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
        this.descripcion = descripcion;
    }

    public String[] toTableRow() {
        return new String[]{codigo, nombre, String.valueOf(precio), String.valueOf(stock), categoria, descripcion};
    }

    @Override
    public String toString() {
        return "<html><b>" + nombre + "</b><br/>"
                + "Código: " + codigo + "<br/>"
                + "Precio: S/ " + String.format("%.2f", precio) + "<br/>"
                + "Stock: " + stock + "<br/>"
                + "Categoría: " + categoria + "</html>";
    }

}
