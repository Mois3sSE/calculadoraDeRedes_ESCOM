package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import logica.Subnet;
import logica.VLSMAlgoritmo;

public class cidrPanel extends JPanel {

    // Componentes de la interfaz
    private JTextField txtIp;
    private JTextField txtPrefijo; 
    private JTextArea areaResultados;
    private JButton btnCalcular;
    private JButton btnLimpiar;

    public cidrPanel() {
        // Configuramos el diseño (Layout)
        setLayout(new BorderLayout(10, 10)); // Márgenes
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Relleno alrededor

        // --- Entradas ---
        JPanel panelEntrada = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        panelEntrada.add(new JLabel("Dirección IP:"));
        txtIp = new JTextField(12); // Campo de texto ancho 12
        txtIp.setText("192.168.1.0"); // Valor por defecto para facilitar pruebas
        panelEntrada.add(txtIp);

        panelEntrada.add(new JLabel("Prefijo (CIDR /):"));
        txtPrefijo = new JTextField(3);
        txtPrefijo.setText("24");
        panelEntrada.add(txtPrefijo);

        btnCalcular = new JButton("Calcular");
        panelEntrada.add(btnCalcular);
        btnLimpiar = new JButton("Limpiar");
        panelEntrada.add(btnLimpiar);

        add(panelEntrada, BorderLayout.NORTH);

        // --- Resultados ---
        areaResultados = new JTextArea();
        areaResultados.setEditable(false); // Imposible editar
        areaResultados.setFont(new Font("Monospaced", Font.PLAIN, 14)); // Definicion de fuente 
        
        // Colocamos los resultados en un scroll pane
        JScrollPane scroll = new JScrollPane(areaResultados);
        // Título del borde
        scroll.setBorder(BorderFactory.createTitledBorder("Resultados CIDR"));
        add(scroll, BorderLayout.CENTER);

        // --- Definimos la accion del boton ---
        btnCalcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcular();
            }
        });
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                areaResultados.setText("");
            }
        });
    }

    private void calcular() {
        try {
            // Tomar los valores de Ip y prefijo
            String ip = txtIp.getText().trim();
            String prefijoStr = txtPrefijo.getText().trim();

            // Validamos si esta vacio
            if (ip.isEmpty() || prefijoStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor llene todos los campos.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            // Validamos si el pregijo es un numero valido
            int prefijo = Integer.parseInt(prefijoStr);
            if (prefijo < 0 || prefijo > 32) {
                JOptionPane.showMessageDialog(this, "El prefijo debe estar entre 0 y 32.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Llamamos a la logica de calculo CIDR
            Subnet resultado = VLSMAlgoritmo.calcularCIDR(ip, prefijo);

            // Impresion de los resultados en el area de texto
            StringBuilder sb = new StringBuilder();
            sb.append("=== ANÁLISIS CIDR ===\n\n");
            sb.append("Dirección de Red:    ").append(resultado.getNetworkAddress()).append("\n");
            sb.append("Máscara de Subred:   ").append(resultado.getMaskAddress()).append("\n");
            sb.append("Prefijo:             /").append(resultado.getPrefijo()).append("\n");
            sb.append("Dirección Broadcast: ").append(resultado.getBroadcastAddress()).append("\n");
            sb.append("--------------------------------------------\n");
            sb.append("Rango de Hosts Útiles:\n");
            sb.append("   Desde: ").append(resultado.getPrimerHost()).append("\n");
            sb.append("   Hasta: ").append(resultado.getUltimohost()).append("\n");
            sb.append("--------------------------------------------\n");
            sb.append("Total Hosts Disponibles: ").append(resultado.getHostDisponibles()).append("\n");

            areaResultados.setText(sb.toString());

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "El prefijo debe ser un número entero.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            // Determinamos si la logica falla
            areaResultados.setText("");
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error en Cálculo", JOptionPane.ERROR_MESSAGE);
        }
    }
}