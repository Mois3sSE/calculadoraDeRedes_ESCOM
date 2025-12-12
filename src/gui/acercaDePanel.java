package gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        add(panelAcercaDe); 
    }

}
