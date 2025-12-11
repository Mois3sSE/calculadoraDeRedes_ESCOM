package mainTemporal;

import java.util.Scanner;

import logica.IPUtils; // Asegúrate que es IPUtils (Mayusculas IP) y no IpUtils
import logica.Subnet;

public class Main {
    public static void main(String[] args) {
        System.out.println("Prueba del objeto subnet");

        Subnet subnet = new Subnet("Subnet1", 10);
        subnet.setRedData("192.168.1.0",
         "192.168.1.15", 
         "255.255.255.240", 
         28, 
         "192.168.1.1",
        "192.168.1.14",
        14);

        System.out.println(subnet.toString());

        

        

        
    }
}