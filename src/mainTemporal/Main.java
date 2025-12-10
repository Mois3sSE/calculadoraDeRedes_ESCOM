package mainTemporal;

import java.util.Scanner;

import logica.IPUtils; // Asegúrate que es IPUtils (Mayusculas IP) y no IpUtils

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Prueba simple
        System.out.print("Prueba de una ip : ");
        String ipUsuario = scanner.next(); 
        
        // Usando la clase estática directamente
        if(IPUtils.isValidIp(ipUsuario)) {
            long ip = IPUtils.ipToLong(ipUsuario);
            System.out.println("IP convertida: " + ip);
            String ipString = IPUtils.longToIp(ip);
            System.out.println("IP revertida: " + ipString);
        } else {
            System.out.println("La IP no es válida.");
        }

        

        scanner.close();
    }
}