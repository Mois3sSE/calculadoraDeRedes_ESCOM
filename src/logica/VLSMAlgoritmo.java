// Esta clase determinara la logica para el calculo de VLSM

package logica;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class VLSMAlgoritmo {
    public static Subnet calcularCIDR(String ip, int prefijo) {
        // Se conviere la ip a formato long para poder operar con ella
        long ipLong = IPUtils.ipToLong(ip);
        // Creacion de la mascara buscando desplazar 0xFFFFFFFL a la izquierda
        // 0xFFFFFFFL es el equivalente a 32 bits en. Se calculan los 0 necesarios
        // esos 0 necesarios se empujan a la izquierda con el operador de corrimiento a
        // la izquierda
        long mascara = 0xFFFFFFFFL << (32 - prefijo);
        // Calculo de la direccion de red y de broadcast
        // Para la red se usa la operacion AND entre la ip y la mascara con la finalidad
        // de limpiar los bits de host
        long direccionRed = ipLong & mascara;
        // Para el broadcast se usa la operacion OR entre la direccion de red y el
        // wildcard
        long direccionBroadcast = direccionRed | (~mascara & 0xFFFFFFFFL);
        // Se crea el objeto de respuesta
        Subnet resultado = new Subnet("Red CIDR ", 0);
        // Calculamos los host totales
        long hostTotales = (long) Math.pow(2, 32 - prefijo) - 2;
        // Se realiza una proteccion para las mascaras /31 y /32 que no tienen hosts
        // disponibles
        if (hostTotales < 0) {
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
                (int) hostTotales);
        return resultado;
    }

    // Definimos el algoritmo VLSM por medio del algoritmo definido en la pagina 3
    public static void calcularVLSM(String ipBase, int basePrefijo, List<Subnet> subnets) throws Exception {
        long redActual = IPUtils.ipToLong(ipBase);
        // Calculamos el limite de la red
        long totalBaseHost = (long) Math.pow(2, 32 - basePrefijo) - 2;
        long direccionesLimite = redActual + totalBaseHost;
        // Ordenamos las subnets de mayor a menor requerimiento de hosts
        Collections.sort(subnets, new Comparator<Subnet>() {
            @Override
            public int compare(Subnet s1, Subnet s2) {
                // Comparamos al reves para ordenar de manera descendente
                return Integer.compare(s2.getHostRequeridos(), s1.getHostRequeridos());
            }
        });
        // Asignamos las subnets
        for (Subnet subnet : subnets) {
            // Calculamos los host reales solicitados
            int hostNecesitados = subnet.getHostRequeridos() + 2;
            // Calculamos los bits de host necesarios
            // Al usar logaritmo natural se debe dividir entre log(2)
            // MAth.ceil para redondear hacia arriba
            int hostBits = (int) Math.ceil(Math.log(hostNecesitados) / Math.log(2));
            // Calculamos el nuevo prefijo
            int nuevoPrefijo = 32 - hostBits;
            // Calculamos el tamaño del bloque 2^n
            long tamañoBloque = (long) Math.pow(2, hostBits);
            // Verificamos que no se exceda el limite de la red
            if (redActual + tamañoBloque > direccionesLimite) {
                throw new Exception("No hay suficiente espacio en la red para asignar la subnet: " + subnet.getName());
            }
        // Calculamos los datos de la subred
            long direccionMascara = 0xFFFFFFFFL << hostBits;
            long direccionRed = redActual; 
            long direccionBroadcast = direccionRed + tamañoBloque - 1;
            long primerhost = direccionRed + 1;
            long ultimoHost = direccionBroadcast - 1;
        // Se guarda en el objeto 
            subnet.setRedData(
                    IPUtils.longToIp(direccionRed),
                    IPUtils.longToIp(direccionBroadcast),
                    IPUtils.longToIp(direccionMascara),
                    nuevoPrefijo,
                    IPUtils.longToIp(primerhost),
                    IPUtils.longToIp(ultimoHost),
                    (int)(tamañoBloque - 2)
            );
        // Avanzamos a la siguiente red
            redActual += tamañoBloque;    
        }
    }

}
