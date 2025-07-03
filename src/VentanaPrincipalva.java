import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// ============================================================================
// VENTANA PRINCIPAL - MENÚ
// ============================================================================
class VentanaPrincipalva extends JFrame {
    
    public VentanaPrincipalva() {
        initComponents();
        setupWindow();
    }
    
    private void initComponents() {
        setTitle("Tecnoby Store - Sistema de Ventas");
        setLayout(new BorderLayout());
        
        // Panel superior con logo y título
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(41, 128, 185));
        headerPanel.setPreferredSize(new Dimension(0, 80));
        
        JLabel titleLabel = new JLabel("Tecnoby Store", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        JLabel subtitleLabel = new JLabel("Sistema de Gestión de Ventas", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(236, 240, 241));
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);
        
        // Panel central con botones del menú
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(245, 245, 245));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Botones del menú principal
        JButton btnProductos = createMenuButton("Gestión de Productos", "Administrar inventario y catálogo", new Color(52, 152, 219));
        
        btnProductos.addActionListener(new ActionListener() {
        @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaProductos().setVisible(true);
            }
        });
        
        JButton btnVentas = createMenuButton("Realizar Venta", "Procesar nuevas ventas", new Color(46, 204, 113));
        btnVentas.addActionListener(new ActionListener() {
        @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaVentas().setVisible(true);
            }
        });
        
        JButton btnReportes = createMenuButton("Reportes de Ventas", "Analizar rendimiento y estadísticas", new Color(155, 89, 182));
        
        btnReportes.addActionListener(new ActionListener() {
        @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaReportes().setVisible(true);
            }
        });
        
        gbc.gridx = 0; gbc.gridy = 0;
        centerPanel.add(btnProductos, gbc);
        
        gbc.gridx = 1; gbc.gridy = 0;
        centerPanel.add(btnVentas, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 2;
        centerPanel.add(btnReportes, gbc);
        
        // Panel inferior con información
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(52, 73, 94));
        footerPanel.setPreferredSize(new Dimension(0, 40));
        JLabel footerLabel = new JLabel("© 2025 Tecnoby Store - Sistema de Gestión Empresarial", SwingConstants.CENTER);
        footerLabel.setForeground(Color.WHITE);
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footerPanel.add(footerLabel);
        
        add(headerPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);
        
        
    }
    
    private JButton createMenuButton(String title, String description, Color color) {
        JButton button = new JButton();
        button.setLayout(new BorderLayout());
        button.setPreferredSize(new Dimension(280, 120));
        button.setBackground(Color.WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(color);
        
        JLabel descLabel = new JLabel("<html>" + description + "</html>");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descLabel.setForeground(new Color(100, 100, 100));
        
        button.add(titleLabel, BorderLayout.NORTH);
        button.add(descLabel, BorderLayout.CENTER);
        
        return button;
    }
    
    private void setupWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
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
                new VentanaPrincipalva().setVisible(true);
            }
        });
    }
}