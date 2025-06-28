/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class HistorialAcciones {
    Stack<String> pilaAcciones = new Stack<>();
    Queue<String> colaPendientes = new LinkedList<>();

    public void registrarAccion(String accion) {
        pilaAcciones.push(accion);
    }

    public void agregarPendiente(String pendiente) {
        colaPendientes.add(pendiente);
    }

    public void imprimirHistorial() {
        System.out.println("Historial de acciones:");
        while (!pilaAcciones.isEmpty()) System.out.println(pilaAcciones.pop());
    }

    public void procesarPendientes() {
        System.out.println("Tareas pendientes:");
        while (!colaPendientes.isEmpty()) System.out.println(colaPendientes.poll());
    }
}
