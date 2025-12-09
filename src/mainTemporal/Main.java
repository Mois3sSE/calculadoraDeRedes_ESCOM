package mainTemporal;

import logica.IPUtils; // Asegúrate que es IPUtils (Mayusculas IP) y no IpUtils

public class Main {
    public static void main(String[] args) {
        // Prueba simple
        System.out.println("Si lees esto, la importación funcionó.");
        
        // Usando la clase estática directamente
        long ip = IPUtils.ipToLong("192.168.0.1");
        System.out.println("IP convertida: " + ip);
    }
}