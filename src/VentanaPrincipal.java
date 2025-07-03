/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Entity.ListaProductos;
import Entity.RegistroVentas;
import Entity.Venta;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

/**
 * Ventana principal mejorada usando DesignUtils 2.0 Colores más vibrantes y
 * mejor contraste visual
 */
public class VentanaPrincipal extends JFrame {
    // <<< CAMBIO: Variables para almacenar los datos y las etiquetas de estadísticas >>>

    private ListaProductos listaProductos;
    private RegistroVentas registroVentas;

    private JLabel lblVentasHoyValue;
    private JLabel lblTotalProductosValue;
    private JLabel lblIngresosHoyValue;

    public VentanaPrincipal() {
        // <<< CAMBIO: Cargar datos ANTES de construir la interfaz >>>
        cargarDatos();
        initComponents();
        Image icon = new ImageIcon(getClass().getResource("/resources/logo.png")).getImage();
        this.setIconImage(icon);
        setupWindow();
        // <<< CAMBIO: Actualizar estadísticas al iniciar >>>
        actualizarEstadisticas();
    }

    private void cargarDatos() {
        try {
            listaProductos = ListaProductos.cargarDeArchivo("productos.dat");
        } catch (Exception e) {
            System.out.println("No se encontró 'productos.dat'. Iniciando con lista vacía.");
            listaProductos = new ListaProductos();
        }
        try {
            registroVentas = RegistroVentas.cargar("ventas.dat");
        } catch (Exception e) {
            System.out.println("No se encontró 'ventas.dat'. Iniciando con registro vacío.");
            registroVentas = new RegistroVentas();
        }
    }

    private void initComponents() {
        setTitle("Tecnoby Store - Sistema de Ventas");
        setLayout(new BorderLayout());

        JPanel headerPanel = DesignUtils.createHeaderPanel(
                "Tecnoby Store",
                "Sistema de Gestión de Ventas - Versión 2.1"
        );

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(DesignUtils.BACKGROUND_LIGHT);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // <<< CAMBIO: Se usan los nuevos botones con iconos gráficos >>>
        JButton btnProductos = DesignUtils.createMenuButton(
                new ImageIcon(getClass().getResource("/resources/products.png")),
                "Gestión de Productos",
                "Administrar inventario, catálogo y stock.",
                DesignUtils.PRIMARY_BLUE
        );
        btnProductos.addActionListener(e -> new VentanaProductos().setVisible(true));

        JButton btnVentas = DesignUtils.createMenuButton(
                new ImageIcon(getClass().getResource("/resources/sales.png")),
                "Realizar Venta",
                "Procesar nuevas ventas y transacciones.",
                DesignUtils.SUCCESS_GREEN
        );
        btnVentas.addActionListener(e -> new VentanaVentas().setVisible(true));

        JButton btnReportes = DesignUtils.createMenuButton(
                new ImageIcon(getClass().getResource("/resources/reports.png")),
                "Reportes de Ventas",
                "Analizar rendimiento y métricas del negocio.",
                DesignUtils.INFO_PURPLE
        );
        btnReportes.addActionListener(e -> new VentanaReportes().setVisible(true));

        JButton btnConfiguracion = DesignUtils.createMenuButton(
                new ImageIcon(getClass().getResource("/resources/settings.png")),
                "Configuración",
                "Ajustes del sistema y preferencias.",
                DesignUtils.DANGER_RED
        );
        btnConfiguracion.addActionListener(e -> JOptionPane.showMessageDialog(this, "Módulo de configuración en desarrollo."));

        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(btnProductos, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        centerPanel.add(btnVentas, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(btnReportes, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        centerPanel.add(btnConfiguracion, gbc);

        JPanel statsPanel = createStatsPanel();
        JPanel footerPanel = DesignUtils.createFooterPanel(
                "© 2025 Tecnoby Store - Sistema de Gestión Empresarial"
        );

        add(headerPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(statsPanel, BorderLayout.EAST);
        add(footerPanel, BorderLayout.SOUTH);
    }

    /**
     * Crea un panel con estadísticas rápidas del sistema
     */
    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.setBackground(DesignUtils.BACKGROUND_WHITE);
        statsPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 1, 0, 0, DesignUtils.BORDER_LIGHT),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        // Se mantiene el ancho que ayudó al botón "Actualizar"
        statsPanel.setPreferredSize(new Dimension(280, 0));

        // <<< FIX: Se acorta el texto del título para que no se corte >>>
        JLabel statsTitle = new JLabel("Resumen", new ImageIcon(getClass().getResource("/resources/summary.png")), SwingConstants.LEFT);
        statsTitle.setFont(DesignUtils.HEADER_FONT);
        statsTitle.setForeground(DesignUtils.TEXT_PRIMARY);
        statsTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        statsPanel.add(statsTitle);
        statsPanel.add(Box.createVerticalStrut(25));

        // <<< CAMBIO: Inicializar los JLabels de valor y crear las tarjetas >>>
        lblIngresosHoyValue = new JLabel("S/ 0.00");
        statsPanel.add(DesignUtils.createStatCard("Ingresos Hoy", lblIngresosHoyValue, DesignUtils.SUCCESS_GREEN));
        statsPanel.add(Box.createVerticalStrut(15));

        lblVentasHoyValue = new JLabel("0");
        statsPanel.add(DesignUtils.createStatCard("Ventas Hoy", lblVentasHoyValue, DesignUtils.PRIMARY_BLUE));
        statsPanel.add(Box.createVerticalStrut(15));

        lblTotalProductosValue = new JLabel("0");
        statsPanel.add(DesignUtils.createStatCard("Productos Totales", lblTotalProductosValue, DesignUtils.INFO_PURPLE));

        statsPanel.add(Box.createVerticalGlue());

        // Botón de actualizar con icono
        JButton btnActualizar = DesignUtils.createStyledButton("Actualizar", DesignUtils.ACCENT_CYAN);
        btnActualizar.setIcon(new ImageIcon(getClass().getResource("/resources/refresh.png")));
        btnActualizar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnActualizar.addActionListener(e -> actualizarEstadisticas());
        statsPanel.add(btnActualizar);

        return statsPanel;
    }

    private void actualizarEstadisticas() {
        // Recargar los datos para obtener la información más reciente
        cargarDatos();

        // 1. Calcular total de productos
        int totalProductos = listaProductos.toList().size();

        // 2. Calcular ventas e ingresos de hoy
        int ventasHoy = 0;
        double ingresosHoy = 0.0;
        LocalDate hoy = LocalDate.now();

        if (registroVentas != null) {
            for (Venta venta : registroVentas.getVentas()) {
                if (venta.fecha.equals(hoy)) {
                    ventasHoy++;
                    ingresosHoy += venta.total;
                }
            }
        }

        // 3. Actualizar los JLabels
        lblTotalProductosValue.setText(String.valueOf(totalProductos));
        lblVentasHoyValue.setText(String.valueOf(ventasHoy));
        lblIngresosHoyValue.setText(String.format("S/ %,.2f", ingresosHoy));

        // Opcional: mostrar un mensaje
        System.out.println("Estadísticas actualizadas.");
    }

    private void setupWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1024, 768)); // Tamaño mínimo más grande
        setSize(1200, 800); // Tamaño inicial
        setLocationRelativeTo(null);
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
                new VentanaPrincipal().setVisible(true);
            }
        });
    }
}
