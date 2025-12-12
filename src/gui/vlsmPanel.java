package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import logica.Subnet;
import logica.VLSMAlgoritmo;

public class vlsmPanel extends JPanel {

    // Definimos los componentes de la interfaz
    private JTextField txtIpBase;
    private JTextField txtPrefijoBase;
    
    // Entradas 
    private JTextField txtNombreSubred;
    private JTextField txtHostsRequeridos;
    
    // Modelos Input Output
    private DefaultTableModel modeloTablaEntrada;
    private DefaultTableModel modeloTablaResultados;
    
    // Lista de tipo Subnet que almacenara las subredes 
    private List<Subnet> listaSubredes;

    public vlsmPanel() {
        setLayout(new BorderLayout(10, 10));
        listaSubredes = new ArrayList<>();

        // Configuracion basica del panel
        JPanel panelConfig = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelConfig.setBorder(BorderFactory.createTitledBorder("Configuración Red Base"));
        
        panelConfig.add(new JLabel("Dirección IP Base:"));
        txtIpBase = new JTextField("192.168.0.0", 10);
        panelConfig.add(txtIpBase);

        panelConfig.add(new JLabel("Prefijo Base /"));
        txtPrefijoBase = new JTextField("24", 3);
        panelConfig.add(txtPrefijoBase);

        add(panelConfig, BorderLayout.NORTH);
        
        // Panel de gestion de subredes
        // Se divide el panel : Arriba (Agregar) | Abajo (Resultados)
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setResizeWeight(0.3); // 30% arriba, 70% abajo

        // Arriba agregacion de los datos
        JPanel panelAgregar = new JPanel(new BorderLayout());
        panelAgregar.setBorder(BorderFactory.createTitledBorder("Requerimientos de Subredes"));

        // Formulario donde se agragan las subredes
        JPanel formAgregar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        formAgregar.add(new JLabel("Nombre Depto:"));
        txtNombreSubred = new JTextField(10);
        formAgregar.add(txtNombreSubred);
        
        formAgregar.add(new JLabel("Hosts Necesarios:"));
        txtHostsRequeridos = new JTextField(5);
        formAgregar.add(txtHostsRequeridos);

        JButton btnAgregar = new JButton("Agregar (+)");
        formAgregar.add(btnAgregar);
        
        JButton btnLimpiar = new JButton("Limpiar Todo");
        formAgregar.add(btnLimpiar);

        panelAgregar.add(formAgregar, BorderLayout.NORTH);

        // Tabla para comprobar datos agregados
        String[] columnasEntrada = {"Nombre", "Hosts Requeridos"};
        // Se crea el modelo de tabla
        modeloTablaEntrada = new DefaultTableModel(columnasEntrada, 0);
        // Se crea la tabla con el modelo definido
        JTable tablaEntrada = new JTable(modeloTablaEntrada);
        panelAgregar.add(new JScrollPane(tablaEntrada), BorderLayout.CENTER);
        
        JButton btnCalcular = new JButton("CALCULAR VLSM");
        btnCalcular.setFont(new Font("Arial", Font.BOLD, 14));
        btnCalcular.setBackground(new Color(200, 255, 200)); 
        panelAgregar.add(btnCalcular, BorderLayout.SOUTH);

        splitPane.setTopComponent(panelAgregar);

        // Abajo resultados
        JPanel panelResultados = new JPanel(new BorderLayout());
        panelResultados.setBorder(BorderFactory.createTitledBorder("Tabla de Resultados VLSM"));

        String[] columnasResultados = {
            "Subred", "Hosts Req", "Hosts Disp", "Dirección Red", "Máscara", "Rango Inicio", "Rango Fin", "Broadcast", "Desperdicio"
        };
        // Se realiza la definicion del modelo de tabla
        modeloTablaResultados = new DefaultTableModel(columnasResultados, 0);
        // Se crea la tabla con el modelo definido
        JTable tablaResultados = new JTable(modeloTablaResultados);
        panelResultados.add(new JScrollPane(tablaResultados), BorderLayout.CENTER);

        splitPane.setBottomComponent(panelResultados);
        add(splitPane, BorderLayout.CENTER);

        //  Definicion de acciones de botonoes
        // Botón Agregar
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarSubred();
            }
        });

        // Botón Limpiar
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listaSubredes.clear();
                modeloTablaEntrada.setRowCount(0);
                modeloTablaResultados.setRowCount(0);
            }
        });

        // Botón Calcular
        btnCalcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejecutarCalculo();
            }
        });
    }

    // Metodos auxiliares
    private void agregarSubred() {
        String nombre = txtNombreSubred.getText().trim();
        String hostsStr = txtHostsRequeridos.getText().trim();

        if (nombre.isEmpty() || hostsStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor llene todos los campos.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int hosts = Integer.parseInt(hostsStr);
            if (hosts <= 0) throw new NumberFormatException();
            // Creacion del objeto Subnet
            Subnet nuevaSubred = new Subnet(nombre, hosts);
            // Agregamos a la lista logica
            listaSubredes.add(nuevaSubred);
            // La agregamos a la tabla visual
            modeloTablaEntrada.addRow(new Object[]{nombre, hosts});
            // Limpiar campos
            txtNombreSubred.setText("");
            txtHostsRequeridos.setText("");
            txtNombreSubred.requestFocus();  

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La cantidad de hosts debe ser un número entero positivo.");
        }
    }

    private void ejecutarCalculo() {
        if (listaSubredes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Agrega al menos una subred antes de calcular.");
            return;
        }

        // Limpiar tabla de resultados previos
        modeloTablaResultados.setRowCount(0);

        try {
            // Obtener datos de la Red Base
            String ipBase = txtIpBase.getText().trim();
            int prefijo = Integer.parseInt(txtPrefijoBase.getText().trim());

            // Llamamos al algoritmo VLSM
            // Esto ordenará la lista y llenará los datos dentro de cada objeto Subnet
            VLSMAlgoritmo.calcularVLSM(ipBase, prefijo, listaSubredes);

            // Mostrar los resultados 
            for (Subnet sub : listaSubredes) {
                modeloTablaResultados.addRow(new Object[]{
                    sub.getName(),
                    sub.getHostRequeridos(),
                    sub.getHostDisponibles(),
                    sub.getNetworkAddress() + " /" + sub.getPrefijo(), 
                    sub.getMaskAddress(),
                    sub.getPrimerHost(),
                    sub.getUltimohost(),
                    sub.getBroadcastAddress(),
                    sub.getHostPerdidos() 
                });
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El prefijo base debe ser un número.");
        } catch (Exception ex) {
            // Se ejecuta la excepción si no hay espacio suficiente 
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Cálculo", JOptionPane.ERROR_MESSAGE);
        }
    }
}