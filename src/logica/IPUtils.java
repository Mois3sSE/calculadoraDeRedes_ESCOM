package logica;
// Esta claseIPUtilsa convertir la ip de entrada en un numero long 

public class IPUtils {
// Metodo que convierte una direccion IP en formato String a un numero long
    public static long ipToLong(String ipAddress) {
        long result = 0;

        String[] octetos = ipAddress.split("\\.");
        for (int i = 0; i < octetos.length; i++) {
            int part = Integer.parseInt(octetos[i]);
            result = (long) part << (24 - (8 * i));
        }
        return result;
    }
}
