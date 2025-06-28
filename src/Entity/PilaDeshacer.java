package Entity;
import java.util.Stack;

/** Historial de acciones (undo) usando pila */
public class PilaDeshacer {
    private Stack<String> pila = new Stack<>();
    public void push(String accion) { pila.push(accion); }
    public String pop() { return pila.isEmpty()?null:pila.pop(); }
    public boolean estaVacia() { return pila.isEmpty(); }
}
