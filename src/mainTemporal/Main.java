package mainTemporal;

import java.util.ArrayList;
import java.util.List;
import logica.IPUtils;
import logica.Subnet;
import logica.VLSMAlgoritmo;

public class Main {
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("   PROYECTO REDES: PRUEBAS DE CONFORMIDAD (PDF)  ");
        System.out.println("=================================================\n");

        // -----------------------------------------------------------------------------
        // CASO DE PRUEBA 1: CIDR (Según Pág. 3 del PDF)
        // Entrada: 192.168.10.0/24
        // Esperado: Rango .1-.254, Broadcast .255
        // -----------------------------------------------------------------------------
        System.out.println(">>> PRUEBA 1: Calculadora CIDR <<<");
        try {
            String ip = "192.168.10.0";
            int prefijo = 24;
            System.out.println("Entrada: IP " + ip + " Prefijo /" + prefijo);
            
            Subnet resultado = VLSMAlgoritmo.calcularCIDR(ip, prefijo);
            System.out.println(resultado.toString());
            System.out.println("✅ PRUEBA 1 SUPERADA\n");
            
        } catch (Exception e) {
            System.out.println("❌ ERROR EN PRUEBA 1: " + e.getMessage());
        }

        // -----------------------------------------------------------------------------
        // CASO DE PRUEBA 2: VLSM (Según Pág. 3 del PDF)
        // Base: 192.168.0.0/24
        // Requerimientos: A=100, B=50, C=25, D=10
        // El algoritmo DEBE ordenar esto automáticamente a: A, B, C, D
        // -----------------------------------------------------------------------------
        System.out.println(">>> PRUEBA 2: Algoritmo VLSM (Ordenamiento y Asignación) <<<");
        try {
            String ipBase = "192.168.0.0";
            int prefijoBase = 24;

            List<Subnet> departamentos = new ArrayList<>();
            // Las agregamos en desorden para probar que tu código las ordene
            departamentos.add(new Subnet("Depto C", 25));
            departamentos.add(new Subnet("Depto A", 100));
            departamentos.add(new Subnet("Depto D", 10));
            departamentos.add(new Subnet("Depto B", 50));

            System.out.println("Entrada Base: " + ipBase + "/" + prefijoBase);
            System.out.println("Requerimientos (Desordenados): C(25), A(100), D(10), B(50)");

            // EJECUTAR ALGORITMO
            VLSMAlgoritmo.calcularVLSM(ipBase, prefijoBase, departamentos);

            System.out.println("\n--- RESULTADOS CALCULADOS ---");
            for (Subnet sub : departamentos) {
                // Imprimimos un resumen de una línea para verificar rápido
                System.out.printf("Subred: %-10s | Hosts: %-3d | Red: %-15s /%d | Rango: %s - %s\n",
                    sub.getName(), 
                    sub.getHostRequeridos(), 
                    sub.getNetworkAddress(), 
                    sub.getPrefijo(),
                    sub.getPrimerHost(),
                    sub.getUltimohost()
                );
            }
            
            // Verificación rápida del orden
            if (departamentos.get(0).getName().equals("Depto A")) {
                System.out.println("\n✅ PRUEBA 2 SUPERADA: El ordenamiento fue correcto.");
            } else {
                System.out.println("\n❌ ERROR: El algoritmo no ordenó las subredes por tamaño.");
            }
            System.out.println("");

        } catch (Exception e) {
            System.out.println("❌ ERROR CRÍTICO EN VLSM: " + e.getMessage());
            e.printStackTrace();
        }

        // -----------------------------------------------------------------------------
        // CASO DE PRUEBA 3: Manejo de Errores (Límite Excedido)
        // Pedir más hosts de los que caben debe lanzar la Excepción que programamos.
        // -----------------------------------------------------------------------------
        System.out.println(">>> PRUEBA 3: Validación de Límite (Debe fallar controladamente) <<<");
        try {
            List<Subnet> casoError = new ArrayList<>();
            casoError.add(new Subnet("Imposible", 500)); // 500 hosts no caben en /24
            
            VLSMAlgoritmo.calcularVLSM("192.168.0.0", 24, casoError);
            
            System.out.println("❌ ERROR: El programa debió lanzar una excepción y no lo hizo.");
        } catch (Exception e) {
            System.out.println("✅ PRUEBA 3 SUPERADA: Se capturó el error esperado -> " + e.getMessage());
        }
    }
}