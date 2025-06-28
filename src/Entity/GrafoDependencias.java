package Entity;
import java.util.*;

/** Grafo para dependencias entre productos */
public class GrafoDependencias {
    private Map<String, List<String>> ady = new HashMap<>();

    public void agregarProducto(String codigo) { ady.putIfAbsent(codigo, new ArrayList<>()); }

    public void agregarDep(String desde, String hacia) {
        ady.computeIfAbsent(desde, k->new ArrayList<>()).add(hacia);
    }

    /** Muestra dependencias via DFS recursivo */
    public void mostrarDep(String codigo) {
        dfs(codigo, new HashSet<>());
    }
    private void dfs(String curr, Set<String> v) {
        if (v.contains(curr)) return;
        v.add(curr);
        System.out.println("-> " + curr);
        for (String vecino : ady.getOrDefault(curr, List.of())) {
            dfs(vecino, v);
        }
    }
}
