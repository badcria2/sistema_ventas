/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

public class RegistroUsuarios implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Map<String, Usuario> usuarios;

    public RegistroUsuarios() {
        this.usuarios = new HashMap<>();
    }

    public void agregarUsuario(Usuario usuario) {
        usuarios.put(usuario.username, usuario);
    }
    
    public boolean validarCredenciales(String username, String password) {
        Usuario usuario = usuarios.get(username);
        if (usuario != null) {
            return usuario.password.equals(password);
        }
        return false;
    }

    public void guardar(String rutaArchivo) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(this);
        }
    }

    public static RegistroUsuarios cargar(String rutaArchivo) {
        File archivo = new File(rutaArchivo);
        if (archivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
                return (RegistroUsuarios) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al cargar usuarios. Se creará un registro nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
                return crearRegistroPorDefecto(rutaArchivo);
            }
        } else {
            // Si el archivo no existe, crea uno con un usuario por defecto
            return crearRegistroPorDefecto(rutaArchivo);
        }
    }

    private static RegistroUsuarios crearRegistroPorDefecto(String rutaArchivo) {
        RegistroUsuarios registro = new RegistroUsuarios();
        // <<< IMPORTANTE: Usuario por defecto para el primer inicio de sesión >>>
        registro.agregarUsuario(new Usuario("admin", "admin123")); 
        try {
            registro.guardar(rutaArchivo);
            JOptionPane.showMessageDialog(null, 
                "No se encontró archivo de usuarios.\nSe ha creado un usuario por defecto:\n\nUsuario: admin\nContraseña: admin123\n", 
                "Primer Inicio", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "No se pudo guardar el registro de usuarios por defecto.", "Error Crítico", JOptionPane.ERROR_MESSAGE);
        }
        return registro;
    }
}