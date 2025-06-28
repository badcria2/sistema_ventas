/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

public class ArbolProductos {
    private class Nodo {
        Producto producto;
        Nodo izq, der;
        Nodo(Producto p) { producto = p; }
    }

    private Nodo raiz;

    public void insertar(Producto p) {
        raiz = insertarRec(raiz, p);
    }

    private Nodo insertarRec(Nodo actual, Producto p) {
        if (actual == null) return new Nodo(p);
        if (p.nombre.compareToIgnoreCase(actual.producto.nombre) < 0)
            actual.izq = insertarRec(actual.izq, p);
        else
            actual.der = insertarRec(actual.der, p);
        return actual;
    }

    public void inOrden() {
        inOrdenRec(raiz);
    }

    private void inOrdenRec(Nodo actual) {
        if (actual != null) {
            inOrdenRec(actual.izq);
            System.out.println(actual.producto.nombre);
            inOrdenRec(actual.der);
        }
    }
}
