package Entity;
import java.util.LinkedList;
import java.util.Queue;

/** Cola para ventas en espera */
public class ColaVentas {
    private Queue<String> cola = new LinkedList<>();
    public void encolar(String ventaId) { cola.add(ventaId); }
    public String desencolar() { return cola.poll(); }
    public boolean estaVacia() { return cola.isEmpty(); }
}
