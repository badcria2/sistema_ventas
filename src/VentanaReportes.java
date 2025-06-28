import Entity.ItemVenta;
import Entity.RegistroVentas;
import Entity.Venta;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

class VentanaReportes extends JFrame {

    private JTable tableReportes;
    private DefaultTableModel modeloReportes;
    private JComboBox<String> cmbPeriodo, cmbCategoria, cmbTipoReporte;
    private JTextField txtFechaInicio, txtFechaFin;
     
    private JLabel lblTotalVentasValue, lblProductosVendidosValue, lblNumVentasValue, lblPromedioVentaValue;
    
    private RegistroVentas registroVentas;

    public VentanaReportes() {
        cargarVentas();
        initComponents();
        setupWindow(); 
        actualizarEstadoFiltros(); 
    }

    private void cargarVentas() {
        try {
            registroVentas = RegistroVentas.cargar("ventas.dat");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No se pudo cargar el archivo de ventas 'ventas.dat'.\nSe iniciará con un registro vacío.", "Error de Carga", JOptionPane.ERROR_MESSAGE);
            registroVentas = new RegistroVentas(); // Iniciar con un registro vacío para evitar NullPointerException
        }
    }

    private void initComponents() {
        // ... (el código para crear los paneles es en su mayoría el mismo) ...
        // <<< CAMBIO: Se han extraído los métodos de creación de paneles para mayor claridad >>>
        setTitle("Reportes de Ventas - TechStore Pro");
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        add(DesignUtils.createHeaderPanel("Reportes de Ventas", "Análisis y estadísticas de rendimiento"), BorderLayout.NORTH);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel filtrosPanel = createFiltrosPanel();
        JPanel statsPanel = createStatsPanel();
        JPanel tablePanel = createTablePanel();
        
        mainPanel.add(filtrosPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER); // Tabla en el centro para más espacio
        
        add(mainPanel, BorderLayout.CENTER);
        add(statsPanel, BorderLayout.SOUTH); // Estadísticas abajo como un resumen
        
        // <<< CAMBIO: Asignar listeners después de crear los componentes >>>
        setupActionListeners();
    }
    
    // <<< CAMBIO: Métodos separados para crear cada parte de la UI >>>
    private JPanel createFiltrosPanel() {
        JPanel filtrosPanel = new JPanel(new GridBagLayout());
        // ... (resto de la configuración del panel) ...
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Fila 0
        gbc.gridx = 0; gbc.gridy = 0; filtrosPanel.add(new JLabel("Tipo de Reporte:"), gbc);
        gbc.gridx = 1; cmbTipoReporte = new JComboBox<>(new String[]{"Ventas por Período", "Productos Más Vendidos", "Ventas por Categoría", "Clientes Frecuentes"});
        filtrosPanel.add(cmbTipoReporte, gbc);

        gbc.gridx = 2; filtrosPanel.add(new JLabel("Período:"), gbc);
        gbc.gridx = 3; cmbPeriodo = new JComboBox<>(new String[]{"Hoy", "Esta Semana", "Este Mes", "Último Mes", "Este Año", "Personalizado"});
        filtrosPanel.add(cmbPeriodo, gbc);

        // Fila 1
        gbc.gridx = 0; gbc.gridy = 1; filtrosPanel.add(new JLabel("Fecha Inicio (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1; txtFechaInicio = new JTextField(10);
        filtrosPanel.add(txtFechaInicio, gbc);

        gbc.gridx = 2; filtrosPanel.add(new JLabel("Fecha Fin (YYYY-MM-DD):"), gbc);
        gbc.gridx = 3; txtFechaFin = new JTextField(10);
        filtrosPanel.add(txtFechaFin, gbc);
        
        // Fila 2
        gbc.gridx = 0; gbc.gridy = 2; filtrosPanel.add(new JLabel("Categoría:"), gbc);
        gbc.gridx = 1; cmbCategoria = new JComboBox<>(new String[]{"Todas", "Smartphones", "Laptops", "Accesorios", "Audio", "Gaming"});
        filtrosPanel.add(cmbCategoria, gbc);

        gbc.gridx = 3; gbc.anchor = GridBagConstraints.EAST;
        JButton btnGenerar = DesignUtils.createStyledButton("Generar Reporte", new Color(155, 89, 182));
        btnGenerar.addActionListener(e -> generarReporte());
        filtrosPanel.add(btnGenerar, gbc);
        
        return filtrosPanel;
    }

    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        statsPanel.setBackground(new Color(245, 245, 245));

        // <<< CAMBIO: Se crean los JLabels de valor y se guardan en variables de instancia >>>
        lblTotalVentasValue = new JLabel("S/ 0.00", SwingConstants.CENTER);
        lblProductosVendidosValue = new JLabel("0", SwingConstants.CENTER);
        lblNumVentasValue = new JLabel("0", SwingConstants.CENTER);
        lblPromedioVentaValue = new JLabel("S/ 0.00", SwingConstants.CENTER);
        
        statsPanel.add(createStatCard("Total Ingresos", lblTotalVentasValue, new Color(46, 204, 113)));
        statsPanel.add(createStatCard("Productos Vendidos", lblProductosVendidosValue, new Color(52, 152, 219)));
        statsPanel.add(createStatCard("Nº de Ventas", lblNumVentasValue, new Color(155, 89, 182)));
        statsPanel.add(createStatCard("Promedio por Venta", lblPromedioVentaValue, new Color(241, 196, 15)));
        
        return statsPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Resultados del Reporte"));

        modeloReportes = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tableReportes = new JTable(modeloReportes);
        // ... (configuración de tabla)
        
        tablePanel.add(new JScrollPane(tableReportes), BorderLayout.CENTER);
        return tablePanel;
    }

    private void setupActionListeners() {
        cmbTipoReporte.addActionListener(e -> actualizarEstadoFiltros());
        cmbPeriodo.addActionListener(e -> {
            boolean personalizado = "Personalizado".equals(cmbPeriodo.getSelectedItem());
            txtFechaInicio.setEnabled(personalizado);
            txtFechaFin.setEnabled(personalizado);
        });
    }

    // <<< CAMBIO: Lógica para habilitar/deshabilitar filtros según el reporte >>>
    private void actualizarEstadoFiltros() {
        String tipoReporte = (String) cmbTipoReporte.getSelectedItem();
        boolean personalizado = "Personalizado".equals(cmbPeriodo.getSelectedItem());
        
        // Por defecto, la mayoría de filtros están habilitados
        cmbPeriodo.setEnabled(true);
        txtFechaInicio.setEnabled(personalizado);
        txtFechaFin.setEnabled(personalizado);
        cmbCategoria.setEnabled(true);
        
        if ("Ventas por Categoría".equals(tipoReporte)) {
            // No tiene sentido filtrar por una categoría si el reporte las va a desglosar todas
            cmbCategoria.setEnabled(false);
            cmbCategoria.setSelectedItem("Todas");
        } else if ("Clientes Frecuentes".equals(tipoReporte)) {
            // El filtro de categoría puede no ser relevante aquí
            cmbCategoria.setEnabled(false);
            cmbCategoria.setSelectedItem("Todas");
        }
    }

    // <<< CAMBIO: Método principal que delega la lógica del reporte >>>
    private void generarReporte() {
        modeloReportes.setRowCount(0); // Limpiar tabla
        String tipoReporte = (String) cmbTipoReporte.getSelectedItem();
        
        LocalDate inicio, fin;
        try {
            LocalDate[] rango = obtenerRangoFechas();
            inicio = rango[0];
            fin = rango[1];
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use AAAA-MM-DD.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        switch (tipoReporte) {
            case "Ventas por Período":
                generarReporteVentasPeriodo(inicio, fin);
                break;
            case "Productos Más Vendidos":
                generarReporteProductosMasVendidos(inicio, fin);
                break;
            case "Ventas por Categoría":
                generarReporteVentasPorCategoria(inicio, fin);
                break;
            case "Clientes Frecuentes":
                generarReporteClientesFrecuentes(inicio, fin);
                break;
        }
    }
    
    // <<< CAMBIO: Métodos específicos para cada tipo de reporte >>>
    private void generarReporteVentasPeriodo(LocalDate inicio, LocalDate fin) {
        modeloReportes.setColumnIdentifiers(new String[]{"Fecha", "Cliente", "Producto", "Categoría", "Cantidad", "Precio Unit.", "Subtotal"});
        String categoriaFiltro = (String) cmbCategoria.getSelectedItem();

        double totalVentas = 0;
        int totalProductos = 0;
        int numVentas = 0;

        for (Venta venta : registroVentas.getVentas()) {
            if (venta.fecha.isBefore(inicio) || venta.fecha.isAfter(fin)) continue;
            
            boolean ventaContada = false;
            for (ItemVenta item : venta.items) {
                if ("Todas".equals(categoriaFiltro) || item.producto.categoria.equalsIgnoreCase(categoriaFiltro)) {
                    double subtotal = item.calcularSubtotal();
                    modeloReportes.addRow(new Object[]{
                        venta.fecha.toString(),
                        venta.cliente,
                        item.producto.nombre,
                        item.producto.categoria,
                        item.cantidad,
                        String.format("S/ %.2f", item.producto.precio),
                        String.format("S/ %.2f", subtotal)
                    });
                    totalVentas += subtotal;
                    totalProductos += item.cantidad;
                    if (!ventaContada) {
                        numVentas++;
                        ventaContada = true;
                    }
                }
            }
        }
        actualizarStats(totalVentas, totalProductos, numVentas);
    }
    
private void generarReporteProductosMasVendidos(LocalDate inicio, LocalDate fin) {
    modeloReportes.setColumnIdentifiers(new String[]{"Código", "Producto", "Cantidad Vendida", "Total Generado"});
    String categoriaFiltro = (String) cmbCategoria.getSelectedItem();
    
    // Usamos un Map para agregar los datos por producto
    Map<String, double[]> productosAgregados = new HashMap<>(); // Key: Codigo, Value: {cantidad, total_ingresos}
    Map<String, String> nombresProductos = new HashMap<>(); // Para no perder el nombre

    for (Venta venta : registroVentas.getVentas()) {
        if (venta.fecha.isBefore(inicio) || venta.fecha.isAfter(fin)) continue;

        for (ItemVenta item : venta.items) {
            if ("Todas".equals(categoriaFiltro) || item.producto.categoria.equalsIgnoreCase(categoriaFiltro)) {
                String codigo = item.producto.codigo;
                productosAgregados.compute(codigo, (k, v) -> {
                    if (v == null) return new double[]{item.cantidad, item.calcularSubtotal()};
                    v[0] += item.cantidad;
                    v[1] += item.calcularSubtotal();
                    return v;
                });
                nombresProductos.putIfAbsent(codigo, item.producto.nombre);
            }
        }
    }
    
    // Convertir el Map a una lista para ordenarla
    List<Object[]> filas = new ArrayList<>();
    productosAgregados.forEach((codigo, data) -> {
        filas.add(new Object[]{codigo, nombresProductos.get(codigo), (int)data[0], data[1]});
    });
    
    // Ordenar por cantidad vendida (descendente)
    // <<< CAMBIO: Se especifica el tipo (Object[] o) para evitar el error de inferencia de tipos >>>
    filas.sort(Comparator.comparingInt((Object[] o) -> (int) o[2]).reversed());
    
    filas.forEach(fila -> {
        fila[3] = String.format("S/ %.2f", fila[3]);
        modeloReportes.addRow(fila);
    });
    actualizarStatsConteoGlobal(inicio, fin);
}
    private void generarReporteVentasPorCategoria(LocalDate inicio, LocalDate fin) {
        modeloReportes.setColumnIdentifiers(new String[]{"Categoría", "Productos Vendidos", "Total Ingresos"});
        Map<String, double[]> categoriasAgregadas = new HashMap<>(); // Key: Categoria, Value: {cantidad, total_ingresos}
        
        for (Venta venta : registroVentas.getVentas()) {
            if (venta.fecha.isBefore(inicio) || venta.fecha.isAfter(fin)) continue;
            for (ItemVenta item : venta.items) {
                String categoria = item.producto.categoria;
                categoriasAgregadas.compute(categoria, (k, v) -> {
                    if (v == null) return new double[]{item.cantidad, item.calcularSubtotal()};
                    v[0] += item.cantidad;
                    v[1] += item.calcularSubtotal();
                    return v;
                });
            }
        }

        List<Map.Entry<String, double[]>> sortedList = new ArrayList<>(categoriasAgregadas.entrySet());
        sortedList.sort((e1, e2) -> Double.compare(e2.getValue()[1], e1.getValue()[1])); // Ordenar por ingresos

        sortedList.forEach(entry -> modeloReportes.addRow(new Object[]{
            entry.getKey(),
            (int)entry.getValue()[0],
            String.format("S/ %.2f", entry.getValue()[1])
        }));
        actualizarStatsConteoGlobal(inicio, fin);
    }

    private void generarReporteClientesFrecuentes(LocalDate inicio, LocalDate fin) {
        modeloReportes.setColumnIdentifiers(new String[]{"Cliente", "Nº de Compras", "Total Gastado"});
        Map<String, double[]> clientesAgregados = new HashMap<>(); // Key: Cliente, Value: {num_compras, total_gastado}

        for (Venta venta : registroVentas.getVentas()) {
            if (venta.fecha.isBefore(inicio) || venta.fecha.isAfter(fin)) continue;
            
            String cliente = venta.cliente;
            clientesAgregados.compute(cliente, (k, v) -> {
                if (v == null) return new double[]{1, venta.total};
                v[0]++;
                v[1] += venta.total;
                return v;
            });
        }
        
        List<Map.Entry<String, double[]>> sortedList = new ArrayList<>(clientesAgregados.entrySet());
        sortedList.sort((e1, e2) -> Double.compare(e2.getValue()[1], e1.getValue()[1])); // Ordenar por total gastado

        sortedList.forEach(entry -> modeloReportes.addRow(new Object[]{
            entry.getKey(),
            (int)entry.getValue()[0],
            String.format("S/ %.2f", entry.getValue()[1])
        }));
        actualizarStatsConteoGlobal(inicio, fin);
    }

    // <<< CAMBIO: El método para actualizar las tarjetas ahora tiene lógica >>>
    private void actualizarStats(double totalVentas, int totalProductos, int transacciones) {
        lblTotalVentasValue.setText(String.format("S/ %.2f", totalVentas));
        lblProductosVendidosValue.setText(String.valueOf(totalProductos));
        lblNumVentasValue.setText(String.valueOf(transacciones));
        lblPromedioVentaValue.setText(transacciones > 0 ? String.format("S/ %.2f", totalVentas / transacciones) : "S/ 0.00");
    }

    private void actualizarStatsConteoGlobal(LocalDate inicio, LocalDate fin) {
        double totalVentas = 0;
        int totalProductos = 0;
        int numVentas = 0;
        for (Venta venta : registroVentas.getVentas()) {
            if (venta.fecha.isBefore(inicio) || venta.fecha.isAfter(fin)) continue;
            numVentas++;
            totalVentas += venta.total;
            for (ItemVenta item : venta.items) {
                totalProductos += item.cantidad;
            }
        }
        actualizarStats(totalVentas, totalProductos, numVentas);
    }
    
    // <<< CAMBIO: Lógica de fechas más precisa >>>
    private LocalDate[] obtenerRangoFechas() throws DateTimeParseException {
        String periodo = (String) cmbPeriodo.getSelectedItem();
        LocalDate hoy = LocalDate.now();
        
        if ("Personalizado".equals(periodo)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return new LocalDate[]{
                LocalDate.parse(txtFechaInicio.getText().trim(), formatter),
                LocalDate.parse(txtFechaFin.getText().trim(), formatter)
            };
        }

        switch (periodo) {
            case "Hoy": return new LocalDate[]{hoy, hoy};
            case "Esta Semana":
                DayOfWeek firstDayOfWeek = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();
                return new LocalDate[]{hoy.with(firstDayOfWeek), hoy};
            case "Este Mes": return new LocalDate[]{hoy.withDayOfMonth(1), hoy.withDayOfMonth(hoy.lengthOfMonth())};
            case "Último Mes":
                LocalDate primerDiaMesAnterior = hoy.minusMonths(1).withDayOfMonth(1);
                return new LocalDate[]{primerDiaMesAnterior, primerDiaMesAnterior.withDayOfMonth(primerDiaMesAnterior.lengthOfMonth())};
            case "Este Año": return new LocalDate[]{hoy.withDayOfYear(1), hoy.withDayOfYear(hoy.lengthOfYear())};
            default: return new LocalDate[]{hoy, hoy};
        }
    }
    
    // --- Métodos de UI auxiliares (sin cambios mayores) ---
    private JPanel createStatCard(String title, JLabel valueLabel, Color color) {
        JPanel card = new JPanel(new BorderLayout(0, 5));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        titleLabel.setForeground(new Color(100, 100, 100));
        
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        valueLabel.setForeground(color);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }

    private void setupWindow() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(VentanaReportes::new);
    }

    // Clase auxiliar, igual que en la otra ventana
    static class DesignUtils {
        public static JPanel createHeaderPanel(String title, String subtitle) {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBackground(new Color(52, 73, 94));
            panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            JLabel titleLabel = new JLabel(title);
            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
            titleLabel.setForeground(Color.WHITE);
            panel.add(titleLabel, BorderLayout.NORTH);
            return panel;
        }
        public static JButton createStyledButton(String text, Color color) {
            return new JButton(text);
        }
    }
}