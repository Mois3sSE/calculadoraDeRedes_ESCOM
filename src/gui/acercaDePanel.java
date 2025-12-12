package gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.*;

public class acercaDePanel extends JPanel {

private static final String[] integrantes = {
    "Arevalo Villanueva Eduardo--2023630062",
    "Díaz Presas Angel Aarón--2024630362",
    "Sandoval Espinoza Moises--2024630268"
}; 

   public acercaDePanel() {
        setLayout(new BorderLayout(10, 10));
    // Configuracion basica del panel
        JPanel panelAcercaDe = new JPanel(new GridLayout(0, 1));
        panelAcercaDe.setBorder(BorderFactory.createTitledBorder("Acerca De"));

        panelAcercaDe.add(new JLabel("Integrantes del Proyecto:"));
        for (String integrante : integrantes) {
            panelAcercaDe.add(new JLabel(integrante));
        }
        panelAcercaDe.add(new JLabel("Curso: 5CM1 - Redes de Computadoras"));
        panelAcercaDe.add(new JLabel("Profesor: Alcaraz Torres Juan Jesus"));

        JButton btnGitHub = new JButton("Repositorio GitHub"); 
        btnGitHub.setPreferredSize(new Dimension(150, 40));
        btnGitHub.setBackground(new Color(50, 50, 50)); 
        btnGitHub.setForeground(Color.WHITE);

        btnGitHub.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirEnlace("https://github.com/Mois3sSE/calculadoraDeRedes_ESCOM");
            }
        }); 
                  
        JButton bntReadMe = new JButton("Ver ReadMe");
        bntReadMe.setPreferredSize(new Dimension(150, 40));
        bntReadMe.setBackground(new Color(237, 201, 194));
        bntReadMe.setForeground(Color.WHITE);
        bntReadMe.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirReadMe("README.md");
            }
        });

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 10));
        panelBoton.add(btnGitHub);
        panelBoton.add(new JLabel("             ")); 
        panelBoton.add(bntReadMe);

        panelAcercaDe.add(new JLabel("Derechos reservados © 2025 - ESCOM - IPN"));

        panelAcercaDe.add(panelBoton);
        add(panelAcercaDe,BorderLayout.NORTH); 
    }

    // Metodos auxiliares 

    private void abrirEnlace(String url) {
        try {
            if(Desktop.isDesktopSupported()){
            Desktop desktop =  Desktop.getDesktop();
                if(desktop.isSupported(Desktop.Action.BROWSE)){
                    desktop.browse(new java.net.URI(url));
                }
            }
        } catch (IOException | URISyntaxException ex) {
           JOptionPane.showMessageDialog(this, "No se pudo abrir el navegador: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void abrirReadMe(String nombreArchivo) {
        try {
            
            File archivo = new File(nombreArchivo);

            if (archivo.exists()) {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(archivo);
                } else {
                    JOptionPane.showMessageDialog(this, "El sistema no soporta abrir archivos automáticamente.");
                }
            } else {
                JOptionPane.showMessageDialog(this, 
                    "No se encontró el archivo: " + nombreArchivo + "\n" +
                    "Ruta buscada: " + archivo.getAbsolutePath(), 
                    "Archivo no encontrado", JOptionPane.WARNING_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al abrir el archivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
