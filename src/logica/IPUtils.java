package logica;
// Esta claseIPUtilsa convertir la ip de entrada en un numero long 

public class IPUtils {
// Metodo que convierte una direccion IP en formato String a un numero long
    public static long ipToLong(String ipAddress) {
        long result = 0;

        String[] octetos = ipAddress.split("\\.");
        for (int i = 0; i < octetos.length; i++) {
            int part = Integer.parseInt(octetos[i]);
// Se hace un corrimiento de bits para colocar cada octeto en su posicion correcta
            result |= ((long) part << (24 - (8 * i)));
        }
        return result;
    }

// Convertira el numero long a formato String
    public static String longToIp(long ip) {
// Retomara el formato de la ip se tomara un numero entero seguido de un punto D.D.D.D
        return String.format("%d.%d.%d.%d",
// Se dirige a cada octeto con corrimientos de bits y se toma el filtro del Hexadecimal
                (ip >> 24) & 0xFF,
                (ip >> 16) & 0xFF,
                (ip >> 8) & 0xFF,
                ip & 0xFF);
    }

// Se determinara una formula para validar que la ip sea correcta
    public static Boolean isValidIp(String ip){
// Se determina la expresion regular de la sigiiente manera: 
// Se iniciara en el simbolo "^" y terminara en el simbolo "$"
// Puede iniciar con 25 y puede tener de 0-5 (255) o seguido de 2 con 0-4 y 0-9 (240)
// o puede iniciar con 0 o 1 seguido de 0-9 y 0-9 (199) seguido de un punto "."
// Se tomara esta logica y se repetira 3 veces "{3}" y al final se repetira la logica sin el punto "."
        String patron = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

        return ip != null && ip.matches(patron);
    }
}
