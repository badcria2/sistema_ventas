// Ubicación: <TuPaquete>/VentanaProductos.java

import Entity.ListaProductos;
import Entity.Producto;
import Entity.ValidadorCampo;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;

public class VentanaProductos extends JFrame {

    private JTable tableProductos;
    private DefaultTableModel modeloTabla;
    private JTextField txtCodigo, txtNombre, txtPrecio, txtStock, txtCategoria, txtBuscar;
    private JTextArea txtDescripcion;
    private ListaProductos listaProductos = new ListaProductos();

    public VentanaProductos() {
        cargarDatos();
        initComponents();
        Image icon = new ImageIcon(getClass().getResource("/resources/logo.png")).getImage();
            this.setIconImage(icon);
    }

    private void cargarDatos() {
        try {
            listaProductos = ListaProductos.cargarDeArchivo("productos.dat");
        } catch (Exception ex) {
            System.out.println("No se encontró archivo 'productos.dat', se creará uno nuevo al guardar.");
            listaProductos = new ListaProductos();
        }
    }

    private void initComponents() {
        DesignUtils.configureWindow(this, "Gestión de Productos - Tecnoby Store", 1200, 800, true, true);
        setLayout(new BorderLayout());

        JPanel headerPanel = DesignUtils.createHeaderPanel("Gestión de Productos", "Administrar inventario y catálogo de productos");

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, createLeftPanel(), createRightPanel());
        splitPane.setDividerLocation(700);
        splitPane.setResizeWeight(0.6);
        splitPane.setBorder(BorderFactory.createEmptyBorder()); // Borde más limpio

        add(headerPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);

        actualizarTabla();
        agregarValidacionEnTiempoReal();

    }

    private void agregarValidacionEnTiempoReal() {
        // Código: solo números
        txtCodigo.getDocument().addDocumentListener(new ValidadorCampo(txtCodigo, "\\d*", "Solo números"));

        // Nombre: solo letras y espacios
        txtNombre.getDocument().addDocumentListener(new ValidadorCampo(txtNombre, "[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*", "Solo letras"));

        // Precio: número con hasta 2 decimales
        txtPrecio.getDocument().addDocumentListener(new ValidadorCampo(txtPrecio, "\\d*(\\.\\d{0,2})?", "Ej: 12.50"));

        // Stock: solo números
        txtStock.getDocument().addDocumentListener(new ValidadorCampo(txtStock, "\\d*", "Solo números"));

        // Categoría: sin caracteres especiales
        txtCategoria.getDocument().addDocumentListener(new ValidadorCampo(txtCategoria, "[\\w\\sáéíóúÁÉÍÓÚñÑ]*", "Sin caracteres especiales"));

        // Descripción: caracteres comunes y signos válidos
        txtDescripcion.getDocument().addDocumentListener(new ValidadorCampo(txtDescripcion, "[\\w\\sáéíóúÁÉÍÓÚñÑ.,()¡!¿?\\-]*", "Sin símbolos extraños"));
    }

    private JPanel createLeftPanel() {
        // <<< CAMBIO: Se usa el panel con título de DesignUtils >>>
        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.setBackground(DesignUtils.BACKGROUND_WHITE);
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 10),
                DesignUtils.createTitledBorder("Catálogo de Productos") // Título estilizado
        ));

        String[] columnas = {"Código", "Nombre", "Precio", "Stock", "Categoría"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableProductos = new JTable(modeloTabla);

        DesignUtils.configureTable(tableProductos, DesignUtils.PRIMARY_BLUE);

        tableProductos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                mostrarProductoSeleccionado();
            }
        });

        JScrollPane scrollTable = DesignUtils.createTableScrollPane(tableProductos);

        leftPanel.add(createSearchPanel(), BorderLayout.NORTH);
        leftPanel.add(scrollTable, BorderLayout.CENTER);

        return leftPanel;
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 15, 5));

        JLabel lblBuscar = new JLabel("Buscar por Código:");
        lblBuscar.setFont(DesignUtils.SUBHEADER_FONT);
        searchPanel.add(lblBuscar, BorderLayout.WEST);

        txtBuscar = DesignUtils.createStyledTextField(20);
        searchPanel.add(txtBuscar, BorderLayout.CENTER);

        // <<< CAMBIO: Se usa el nuevo createIconButton para que el texto no se corte >>>
        JButton btnBuscar = DesignUtils.createIconButton("Buscar", new ImageIcon(getClass().getResource("/resources/search.png")), DesignUtils.PRIMARY_BLUE);
        btnBuscar.addActionListener(e -> buscarProducto());

        searchPanel.add(btnBuscar, BorderLayout.EAST);
        return searchPanel;
    }

    private JPanel createRightPanel() {
        // <<< CAMBIO: Se usa el panel con título de DesignUtils >>>
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(DesignUtils.BACKGROUND_WHITE);
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(15, 10, 15, 15),
                DesignUtils.createTitledBorder("Información del Producto") // Título estilizado
        ));

        GridBagConstraints gbc = new GridBagConstraints();

        txtCodigo = DesignUtils.createStyledTextField(25);
        txtNombre = DesignUtils.createStyledTextField(25);
        txtPrecio = DesignUtils.createStyledTextField(25);
        txtStock = DesignUtils.createStyledTextField(25);
        txtCategoria = DesignUtils.createStyledTextField(25);
        txtDescripcion = DesignUtils.createStyledTextArea(5, 25);

        DesignUtils.addFormField(rightPanel, "Código:", txtCodigo, gbc, 0);
        DesignUtils.addFormField(rightPanel, "Nombre:", txtNombre, gbc, 1);
        DesignUtils.addFormField(rightPanel, "Precio (S/):", txtPrecio, gbc, 2);
        DesignUtils.addFormField(rightPanel, "Stock:", txtStock, gbc, 3);
        DesignUtils.addFormField(rightPanel, "Categoría:", txtCategoria, gbc, 4);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(8, 0, 8, 15);
        JLabel lblDesc = new JLabel("Descripción:");
        lblDesc.setFont(DesignUtils.SUBHEADER_FONT);
        rightPanel.add(lblDesc, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(8, 0, 8, 0);
        rightPanel.add(new JScrollPane(txtDescripcion), gbc);

        return rightPanel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15)); // Más espaciado
        panel.setBackground(DesignUtils.BACKGROUND_LIGHT);
        panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, DesignUtils.BORDER_LIGHT));

        // <<< CAMBIO: Todos los botones usan el nuevo createIconButton para mostrar texto completo >>>
        JButton btnLimpiar = DesignUtils.createIconButton("Limpiar", new ImageIcon(getClass().getResource("/resources/clear.png")), DesignUtils.ACCENT_CYAN);
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        JButton btnGuardar = DesignUtils.createIconButton("Guardar", new ImageIcon(getClass().getResource("/resources/save.png")), DesignUtils.SUCCESS_GREEN);
        btnGuardar.addActionListener(e -> guardarProducto());

        JButton btnEditar = DesignUtils.createIconButton("Editar", new ImageIcon(getClass().getResource("/resources/edit.png")), DesignUtils.WARNING_ORANGE);
        btnEditar.addActionListener(e -> editarProducto());

        JButton btnEliminar = DesignUtils.createIconButton("Eliminar", new ImageIcon(getClass().getResource("/resources/delete.png")), DesignUtils.DANGER_RED);
        btnEliminar.addActionListener(e -> eliminarProducto());

        JButton btnVolver = DesignUtils.createIconButton("Volver", new ImageIcon(getClass().getResource("/resources/back.png")), DesignUtils.TEXT_MUTED);
        btnVolver.addActionListener(e -> this.dispose());

        panel.add(btnLimpiar);
        panel.add(btnGuardar);
        panel.add(btnEditar);
        panel.add(btnEliminar);
        panel.add(DesignUtils.createHorizontalStrut(30)); // Más espacio antes de "Volver"
        panel.add(btnVolver);

        return panel;
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        if (listaProductos != null) {
            for (Producto p : listaProductos.toList()) {
                modeloTabla.addRow(new Object[]{p.codigo, p.nombre, p.precio, p.stock, p.categoria});
            }
        }
    }

    private void mostrarProductoSeleccionado() {
        int fila = tableProductos.getSelectedRow();
        if (fila >= 0) {
            String codigo = (String) modeloTabla.getValueAt(fila, 0);
            Producto p = listaProductos.buscarPorCodigo(codigo);
            if (p != null) {
                txtCodigo.setText(p.codigo);
                txtNombre.setText(p.nombre);
                txtPrecio.setText(String.format("%.2f", p.precio));
                txtStock.setText(String.valueOf(p.stock));
                txtCategoria.setText(p.categoria);
                txtDescripcion.setText(p.descripcion);
                txtCodigo.setEditable(false);
            }
        }
    }

    private void buscarProducto() {
        String codigoBusqueda = txtBuscar.getText().trim();
        if (codigoBusqueda.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un código para buscar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int rowIndex = -1;
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            if (modeloTabla.getValueAt(i, 0).equals(codigoBusqueda)) {
                rowIndex = i;
                break;
            }
        }

        if (rowIndex != -1) {
            tableProductos.setRowSelectionInterval(rowIndex, rowIndex);
            tableProductos.scrollRectToVisible(tableProductos.getCellRect(rowIndex, 0, true));
            mostrarProductoSeleccionado();
        } else {
            JOptionPane.showMessageDialog(this, "Producto no encontrado.", "Búsqueda", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
        txtStock.setText("");
        txtCategoria.setText("");
        txtDescripcion.setText("");
        txtBuscar.setText("");
        tableProductos.clearSelection();
        txtCodigo.setEditable(true);
        txtCodigo.requestFocus();
    }

    private void guardarProducto() {
        if (!validarCampos()) {
            return;
        }

        String codigo = txtCodigo.getText().trim();
        if (listaProductos.buscarPorCodigo(codigo) != null) {
            JOptionPane.showMessageDialog(this, "El código de producto ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Producto p = new Producto(
                codigo,
                txtNombre.getText().trim(),
                Double.parseDouble(txtPrecio.getText().replace(",", ".")),
                Integer.parseInt(txtStock.getText()),
                txtCategoria.getText().trim(),
                txtDescripcion.getText().trim()
        );

        listaProductos.agregar(p);
        guardarYActualizar("Producto guardado correctamente.");
    }

    private void editarProducto() {
        int fila = tableProductos.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto de la tabla para editar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!validarCampos()) {
            return;
        }

        String codigoOriginal = (String) modeloTabla.getValueAt(fila, 0);
        Producto productoExistente = listaProductos.buscarPorCodigo(codigoOriginal);

        if (productoExistente != null) {
            productoExistente.nombre = txtNombre.getText().trim();
            productoExistente.precio = Double.parseDouble(txtPrecio.getText().replace(",", "."));
            productoExistente.stock = Integer.parseInt(txtStock.getText());
            productoExistente.categoria = txtCategoria.getText().trim();
            productoExistente.descripcion = txtDescripcion.getText().trim();

            guardarYActualizar("Producto editado correctamente.");
        }
    }

    private void eliminarProducto() {
        int fila = tableProductos.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto de la tabla para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String codigo = (String) modeloTabla.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que deseas eliminar el producto '" + modeloTabla.getValueAt(fila, 1) + "'?",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            listaProductos.eliminarPorCodigo(codigo);
            guardarYActualizar("Producto eliminado correctamente.");
        }
    }

    private void guardarYActualizar(String mensaje) {
        try {
            listaProductos.guardarEnArchivo("productos.dat");
            actualizarTabla();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar los datos en el archivo.", "Error de Guardado", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validarCampos() {
        if (txtCodigo.getText().trim().isEmpty()
                || txtNombre.getText().trim().isEmpty()
                || txtPrecio.getText().trim().isEmpty()
                || txtStock.getText().trim().isEmpty()
                || txtCategoria.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos (excepto descripción) son obligatorios.", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Double.parseDouble(txtPrecio.getText().replace(",", "."));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El precio debe ser un número válido.", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Integer.parseInt(txtStock.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El stock debe ser un número entero.", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new VentanaProductos().setVisible(true));
    }
}
