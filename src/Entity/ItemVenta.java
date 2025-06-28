package Entity;

import java.io.Serializable;

public class ItemVenta implements Serializable {
    private static final long serialVersionUID = 1L;

    public Producto producto;
    public int cantidad;

    public ItemVenta(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

     public double calcularSubtotal() {
        if (producto == null) return 0;
        return producto.precio * cantidad;
    }
}
