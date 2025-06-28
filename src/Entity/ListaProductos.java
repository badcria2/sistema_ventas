package Entity;

import java.io.*;
import java.util.*;

/**
 * ListaProductos implementa una lista enlazada personalizada de productos
 * con soporte para operaciones como búsqueda, ordenamiento, eliminación,
 * serialización y funcionalidades avanzadas (recursividad, árbol y grafo).
 */
public class ListaProductos implements Serializable {

    private static final long serialVersionUID = 1L;
    private Nodo inicio;

    // Nodo de la lista enlazada
    private static class Nodo implements Serializable {
        Producto producto;
        Nodo siguiente;

        Nodo(Producto p) {
            this.producto = p;
        }
    }

    // Agrega un producto al inicio de la lista
    public void agregar(Producto p) {
        Nodo nuevo = new Nodo(p);
        nuevo.siguiente = inicio;
        inicio = nuevo;
    }

    // Búsqueda secuencial por código
    public Producto buscarPorCodigo(String codigo) {
        Nodo actual = inicio;
        while (actual != null) {
            if (actual.producto.codigo.equalsIgnoreCase(codigo)) {
                return actual.producto;
            }
            actual = actual.siguiente;
        }
        return null;
    }

    // Devuelve la lista en forma de ArrayList
    public List<Producto> toList() {
        List<Producto> lista = new ArrayList<>();
        Nodo actual = inicio;
        while (actual != null) {
            lista.add(actual.producto);
            actual = actual.siguiente;
        }
        return lista;
    }

    // Guarda la lista enlazada en un archivo
    public void guardarEnArchivo(String ruta) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ruta));
        out.writeObject(this);
        out.close();
    }

    // Carga la lista enlazada desde un archivo
    public static ListaProductos cargarDeArchivo(String ruta) throws IOException, ClassNotFoundException {
        File file = new File(ruta);
        if (!file.exists()) {
            return new ListaProductos();
        }
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(ruta));
        ListaProductos lista = (ListaProductos) in.readObject();
        in.close();
        return lista;
    }

    // Imprime los productos recursivamente
    public void imprimirRecursivo() {
        imprimirRecursivoHelper(inicio);
    }

    private void imprimirRecursivoHelper(Nodo actual) {
        if (actual == null) return;
        System.out.println(actual.producto.nombre);
        imprimirRecursivoHelper(actual.siguiente);
    }

    // Elimina un producto por código
    public boolean eliminarPorCodigo(String codigo) {
        if (inicio == null) return false;

        if (inicio.producto.codigo.equalsIgnoreCase(codigo)) {
            inicio = inicio.siguiente;
            return true;
        }

        Nodo actual = inicio;
        while (actual.siguiente != null) {
            if (actual.siguiente.producto.codigo.equalsIgnoreCase(codigo)) {
                actual.siguiente = actual.siguiente.siguiente;
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    // Ordenamiento por nombre usando Burbuja (aplicado sobre lista enlazada)
    public void ordenarPorNombre() {
        if (inicio == null || inicio.siguiente == null) return;
        boolean cambiado;
        do {
            Nodo actual = inicio;
            Nodo anterior = null;
            Nodo siguiente = inicio.siguiente;
            cambiado = false;

            while (siguiente != null) {
                if (actual.producto.nombre.compareToIgnoreCase(siguiente.producto.nombre) > 0) {
                    cambiado = true;
                    if (anterior != null) {
                        Nodo temp = siguiente.siguiente;
                        anterior.siguiente = siguiente;
                        siguiente.siguiente = actual;
                        actual.siguiente = temp;
                    } else {
                        Nodo temp = siguiente.siguiente;
                        inicio = siguiente;
                        siguiente.siguiente = actual;
                        actual.siguiente = temp;
                    }
                    anterior = siguiente;
                    siguiente = actual.siguiente;
                } else {
                    anterior = actual;
                    actual = siguiente;
                    siguiente = siguiente.siguiente;
                }
            }
        } while (cambiado);
    }

    // Buscar productos por categoría (devuelve lista)
    public List<Producto> buscarPorCategoria(String categoria) {
        List<Producto> encontrados = new ArrayList<>();
        Nodo actual = inicio;
        while (actual != null) {
            if (actual.producto.categoria.equalsIgnoreCase(categoria)) {
                encontrados.add(actual.producto);
            }
            actual = actual.siguiente;
        }
        return encontrados;
    }

    // Convierte la lista en un árbol binario de búsqueda (ordenado por código)
    public ArbolProductos toArbol() {
        ArbolProductos arbol = new ArbolProductos();
        Nodo actual = inicio;
        while (actual != null) {
            arbol.insertar(actual.producto);
            actual = actual.siguiente;
        }
        return arbol;
    }

    // Genera grafo de relaciones (cliente-producto o dependencia entre productos)
    public Map<String, List<String>> generarGrafoRelacional() {
        Map<String, List<String>> grafo = new HashMap<>();
        Nodo actual = inicio;
        while (actual != null) {
            grafo.putIfAbsent(actual.producto.codigo, new ArrayList<>());
            if (actual.siguiente != null) {
                grafo.get(actual.producto.codigo).add(actual.siguiente.producto.codigo);
            }
            actual = actual.siguiente;
        }
        return grafo;
    }
}
