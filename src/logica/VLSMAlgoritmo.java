// Esta clase determinara la logica para el calculo de VLSM

package logica;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class VLSMAlgoritmo {
    public static Subnet calcularCIDR(String ip,int prefijo){
    // Se conviere la ip a formato long para poder operar con ella 
        long ipLong = IPUtils.ipToLong(ip);
    // Creacion de la mascara buscando desplazar 0xFFFFFFFL a la izquierda
    // 0xFFFFFFFL es el equivalente a 32 bits en. Se calculan los 0 necesarios 
    // esos 0 necesarios se empujan a la izquierda con el operador de corrimiento a la izquierda
        long mascara = 0xFFFFFFFFL << (32 - prefijo);
    // Calculo de la direccion de red y de broadcast
    // Para la red se usa la operacion AND entre la ip y la mascara con la finalidad de limpiar los bits de host
        long direccionRed = ipLong & mascara;
     // Para el broadcast se usa la operacion OR entre la direccion de red y el wildcard 
        long direccionBroadcast = direccionRed | (~mascara & 0xFFFFFFFFL);
    // Se crea el objeto de respuesta 
        Subnet resultado = new Subnet("Red CIDR ",0);
    // Calculamos los host totales 
        long hostTotales = (long) Math.pow(2, 32 - prefijo) - 2; 
        if(hostTotales < 0){
            hostTotales = 0;
        }
    // Guardamos todos los datos en el objeto resultado
        resultado.setRedData(
            IPUtils.longToIp(direccionRed),
            IPUtils.longToIp(direccionBroadcast),
            IPUtils.longToIp(mascara),
            prefijo,
            IPUtils.longToIp(direccionRed + 1),
            IPUtils.longToIp(direccionBroadcast - 1),
            (int) hostTotales
        );
        return resultado;

    }
}
