/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

// Nodo que representa un producto en una lista enlazada
public class NodoProducto {
    public Producto producto;
    public NodoProducto siguiente;

    public NodoProducto(Producto producto) {
        this.producto = producto;
        this.siguiente = null;
    }
}
