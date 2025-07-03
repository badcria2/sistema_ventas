
import Entity.ArbolProductos;
import Entity.GrafoDependencias;
import Entity.ItemVenta;
import Entity.ListaProductos;
// No se usa la PilaDeshacer en esta versión corregida
// import Entity.PilaDeshacer; 
import Entity.Producto;
import Entity.Venta;
import Entity.RegistroVentas;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.ArrayList;

class VentanaVentas extends JFrame {

    // <<< CAMBIO: Se eliminan las estructuras no utilizadas en esta ventana >>>
    private ListaProductos listaProductos = new ListaProductos();

    private JTable tableCarrito;
    private DefaultTableModel modeloCarrito;
    private JTextField txtCodigoProducto, txtCantidad, txtCliente, txtTotal;
    private JLabel lblProductoInfo;

    // <<< CAMBIO: Variable para mantener referencia al producto buscado >>>
    private Producto productoSeleccionado = null;

    public VentanaVentas() {
        cargarProductos();
        initComponents();
        setupWindow();
    }

    private void cargarProductos() {
        try {
            // Se asume que el archivo existe. Idealmente, manejar el caso de que no exista.
            listaProductos = ListaProductos.cargarDeArchivo("productos.dat");
            listaProductos.ordenarPorNombre();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "No se pudieron cargar los productos desde 'productos.dat'.\n" + e.getMessage(),
                    "Error de Carga",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void initComponents() {
        setTitle("Realizar Venta - Tecnoby Store");
        setLayout(new BorderLayout(10, 10)); // Añade espacio entre componentes
        getContentPane().setBackground(Color.WHITE);

        // Paneles principales
        add(DesignUtils.createHeaderPanel("Punto de Venta", "Procesar nueva venta de productos"), BorderLayout.NORTH);
        add(createMainPanel(), BorderLayout.CENTER);
        add(createActionButtonsPanel(), BorderLayout.SOUTH); // <<< CAMBIO: Panel de acciones refactorizado

        // Asignar listeners
        //setupActionListeners();
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        mainPanel.setBackground(Color.WHITE);

        // Panel superior combinado
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(Color.WHITE);
        topPanel.add(createClientePanel(), BorderLayout.NORTH);
        topPanel.add(createAddProductPanel(), BorderLayout.CENTER);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(createCarritoPanel(), BorderLayout.CENTER);

        return mainPanel;
    }

    private JPanel createClientePanel() {
        JPanel clientePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        clientePanel.setBorder(createTitledBorder("Información de la Venta"));
        clientePanel.setBackground(Color.WHITE);
        clientePanel.add(new JLabel("Cliente:"));
        txtCliente = new JTextField(20);
        clientePanel.add(txtCliente);
        clientePanel.add(Box.createHorizontalStrut(20));
        clientePanel.add(new JLabel("Fecha: " + java.time.LocalDate.now()));
        return clientePanel;
    }
    // En VentanaVentas.java

    private JPanel createAddProductPanel() {
        JPanel addProductPanel = new JPanel(new GridBagLayout());
        addProductPanel.setBorder(createTitledBorder("Agregar Producto"));
        addProductPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ... (código para los JTextField y JLabel)
        gbc.gridx = 0;
        gbc.gridy = 0;
        addProductPanel.add(new JLabel("Código Producto:"), gbc);
        gbc.gridx = 1;
        txtCodigoProducto = new JTextField(15);
        addProductPanel.add(txtCodigoProducto, gbc);

        // <<< CAMBIO: Asignar listener al botón "Buscar" >>>
        gbc.gridx = 2;
        JButton btnBuscarProducto = DesignUtils.createStyledButton("Buscar", new Color(52, 152, 219));
        btnBuscarProducto.addActionListener(e -> buscarProducto());
        addProductPanel.add(btnBuscarProducto, gbc);

        // ... (código para lblProductoInfo)
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        lblProductoInfo = new JLabel("Busque un producto por su código...");
        lblProductoInfo.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblProductoInfo.setForeground(new Color(100, 100, 100));
        addProductPanel.add(lblProductoInfo, gbc);

        // ... (código para cantidad)
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        addProductPanel.add(new JLabel("Cantidad:"), gbc);
        gbc.gridx = 1;
        txtCantidad = new JTextField(15);
        addProductPanel.add(txtCantidad, gbc);

        // <<< CAMBIO: Asignar listener al botón "Agregar" >>>
        gbc.gridx = 2;
        JButton btnAgregarCarrito = DesignUtils.createStyledButton("Agregar", new Color(46, 204, 113));
        btnAgregarCarrito.addActionListener(e -> agregarAlCarrito());
        addProductPanel.add(btnAgregarCarrito, gbc);

        return addProductPanel;
    }

    private JPanel createCarritoPanel() {
        JPanel carritoPanel = new JPanel(new BorderLayout(10, 10));
        carritoPanel.setBorder(createTitledBorder("Carrito de Compras"));
        carritoPanel.setBackground(Color.WHITE);

        String[] columnasCarrito = {"Código", "Producto", "Precio Unit.", "Cantidad", "Subtotal"};
        // <<< CAMBIO: Hacer que el modelo de tabla no sea editable por el usuario >>>
        modeloCarrito = new DefaultTableModel(columnasCarrito, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableCarrito = new JTable(modeloCarrito);
        // ... (configuración de la tabla)

        JScrollPane scrollCarrito = new JScrollPane(tableCarrito);
        carritoPanel.add(scrollCarrito, BorderLayout.CENTER);

        // Panel de total
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalPanel.setBackground(Color.WHITE);
        totalPanel.add(new JLabel("TOTAL: S/ "));
        txtTotal = new JTextField("0.00", 10);
        txtTotal.setEditable(false);
        txtTotal.setFont(new Font("Segoe UI", Font.BOLD, 16));
        txtTotal.setHorizontalAlignment(JTextField.RIGHT);
        totalPanel.add(txtTotal);
        carritoPanel.add(totalPanel, BorderLayout.SOUTH);

        return carritoPanel;
    }

    // En VentanaVentas.java
    private JPanel createActionButtonsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)));

        // <<< CAMBIO: Se asigna el ActionListener justo después de crear el botón >>>
        JButton btnQuitar = DesignUtils.createStyledButton("Quitar Item", new Color(231, 76, 60));
        btnQuitar.addActionListener(e -> quitarItemSeleccionado());

        JButton btnLimpiar = DesignUtils.createStyledButton("Limpiar Todo", new Color(149, 165, 166));
        btnLimpiar.addActionListener(e -> limpiarCarrito());

        JButton btnProcesarVenta = DesignUtils.createStyledButton("Procesar Venta", new Color(46, 204, 113));
        btnProcesarVenta.addActionListener(e -> procesarVenta());

        JButton btnVolver = DesignUtils.createStyledButton("Volver", new Color(149, 165, 166));
        btnVolver.addActionListener(e -> this.dispose());

        // Ahora simplemente añadimos los componentes al panel
        panel.add(btnQuitar);
        panel.add(btnLimpiar);
        panel.add(Box.createHorizontalStrut(20)); // El espaciador ya no es un problema
        panel.add(btnProcesarVenta);
        panel.add(btnVolver);

        return panel;
    }

    private void buscarProducto() {
        String codigo = txtCodigoProducto.getText().trim();
        if (codigo.isEmpty()) {
            lblProductoInfo.setText("Por favor, ingrese un código de producto.");
            productoSeleccionado = null;
            return;
        }

        productoSeleccionado = listaProductos.buscarPorCodigo(codigo);

        if (productoSeleccionado != null) {
            lblProductoInfo.setText(String.format("<html><b>%s</b><br>Stock: %d | Precio: S/ %.2f</html>",
                    productoSeleccionado.nombre, productoSeleccionado.stock, productoSeleccionado.precio));
        } else {
            lblProductoInfo.setText("Producto no encontrado con el código: " + codigo);
        }
    }

    private void agregarAlCarrito() {
        if (productoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Primero debe buscar y encontrar un producto válido.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(txtCantidad.getText().trim());
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser un número positivo.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese una cantidad numérica válida.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // <<< CAMBIO: Lógica de stock corregida >>>
        // Solo se verifica el stock, no se descuenta aquí.
        if (cantidad > productoSeleccionado.stock) {
            JOptionPane.showMessageDialog(this,
                    "Stock insuficiente. Stock disponible: " + productoSeleccionado.stock,
                    "Stock Insuficiente",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Añadir al modelo de la tabla
        modeloCarrito.addRow(new Object[]{
            productoSeleccionado.codigo,
            productoSeleccionado.nombre,
            productoSeleccionado.precio,
            cantidad,
            productoSeleccionado.precio * cantidad
        });

        actualizarTotal();
        limpiarCamposProducto();
    }

    private void quitarItemSeleccionado() {
        int selectedRow = tableCarrito.getSelectedRow();
        if (selectedRow >= 0) {
            modeloCarrito.removeRow(selectedRow);
            actualizarTotal();
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un item del carrito para quitar.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void limpiarCarrito() {
        modeloCarrito.setRowCount(0); // Elimina todas las filas
        actualizarTotal();
    }

    private void procesarVenta() {
        if (modeloCarrito.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "El carrito está vacío. Agregue productos antes de procesar.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String cliente = txtCliente.getText().trim();
        if (cliente.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese el nombre del cliente.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            List<ItemVenta> items = new ArrayList<>();
            for (int i = 0; i < modeloCarrito.getRowCount(); i++) {
                String codigo = modeloCarrito.getValueAt(i, 0).toString();
                int cantidad = Integer.parseInt(modeloCarrito.getValueAt(i, 3).toString());

                // <<< CAMBIO: Aquí es donde realmente se actualiza el stock >>>
                Producto productoVendido = listaProductos.buscarPorCodigo(codigo);
                if (productoVendido != null && productoVendido.stock >= cantidad) {
                    productoVendido.stock -= cantidad; // Descuento de stock en el momento correcto
                    items.add(new ItemVenta(productoVendido, cantidad));
                } else {
                    // Este caso es una salvaguarda, no debería ocurrir si la lógica de "agregar" es correcta
                    throw new Exception("Stock inconsistente para el producto: " + codigo);
                }
            }

            double total = Double.parseDouble(txtTotal.getText());
            Venta venta = new Venta(cliente, items, total);

            // Guardado en archivos
            RegistroVentas registro = RegistroVentas.cargar("ventas.dat");
            registro.agregarVenta(venta);
            registro.guardar("ventas.dat");
            listaProductos.guardarEnArchivo("productos.dat"); // Guardar productos con stock actualizado

            JOptionPane.showMessageDialog(this, "Venta procesada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormularioVenta();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al procesar la venta: " + ex.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTotal() {
        double total = 0.0;
        // <<< CAMBIO: Cálculo basado en los datos del modelo, no en el String de la celda >>>
        for (int i = 0; i < modeloCarrito.getRowCount(); i++) {
            // Se puede obtener el subtotal directo de la columna 4
            total += (double) modeloCarrito.getValueAt(i, 4);
        }
        txtTotal.setText(String.format("%.2f", total));
    }

    private void limpiarFormularioVenta() {
        txtCliente.setText("");
        limpiarCarrito();
        limpiarCamposProducto();
    }

    private void limpiarCamposProducto() {
        txtCodigoProducto.setText("");
        txtCantidad.setText("");
        lblProductoInfo.setText("Busque un producto por su código...");
        productoSeleccionado = null;
        txtCodigoProducto.requestFocus();
    }

    private void setupWindow() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 750);
        setLocationRelativeTo(null);
    }

    private javax.swing.border.TitledBorder createTitledBorder(String title) {
        return BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                title, 0, 0, new Font("Segoe UI", Font.BOLD, 14), new Color(52, 73, 94));
    }

    public static void main(String[] args) {
        try {
            // Configurar Look and Feel del sistema
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VentanaVentas().setVisible(true);
            }
        });
    }
}
