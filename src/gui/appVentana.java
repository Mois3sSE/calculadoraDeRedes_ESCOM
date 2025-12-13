package gui;

import javax.swing.*;
// import java.awt.*; // Lo usaremos más adelante para iconos o estilos si quieres

public class appVentana extends JFrame {

    public appVentana() {
        // Configuracion basica de la ventana
        setTitle("Calculadora IP - 5CM1 - Proyecto Redes"); 
        setSize(800, 900); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setLocationRelativeTo(null); 

        // Se hara uso de un sistema de pestañas para separar CIDR y VLSM
        // Por lo tanto creamos el JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();
        //  Pestaña 1: CIDR 
        // Instancia del panel CIDR
        cidrPanel panelCidr = new cidrPanel();
        tabbedPane.addTab("Calculadora CIDR", panelCidr);

        // Pestaña 2: VLSM
        // Instancia del panel VLSM
        vlsmPanel panelVlsm = new vlsmPanel();
        tabbedPane.addTab("Algoritmo VLSM", panelVlsm);

        // Pestaña 3: Acerca De
        // Instancia del panel Acerca De
        acercaDePanel panelAcercaDe = new acercaDePanel();
        tabbedPane.addTab("Acerca De", panelAcercaDe);
        
        add(tabbedPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                appVentana ventana = new appVentana();
                ventana.setVisible(true);
            }
        });
    }
}