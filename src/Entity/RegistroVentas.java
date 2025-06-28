package Entity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RegistroVentas implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Venta> ventas = new ArrayList<>();

    public void agregarVenta(Venta venta) {
        ventas.add(venta);
    }

    public void guardar(String ruta) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ruta));
        out.writeObject(this);
        out.close();
    }

    public static RegistroVentas cargar(String ruta) throws IOException, ClassNotFoundException {
        File file = new File(ruta);
        if (!file.exists()) {
            return new RegistroVentas();
        }
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(ruta));
        RegistroVentas rv = (RegistroVentas) in.readObject();
        in.close();
        return rv;
    }

    public List<Venta> getVentas() {
        return ventas;
    }
}
