package logica;
// Este sera el objeto que contendra la informacion de entrada

public class Subnet {
// --- DATOS DE ENTRADA INGRESADOS POR EL USUARIO ---
    private String name;          
    private int hostRequeridos;     

// --- DATOS DE SALIDA PROCESADOS POR EL PROGRAMA ---
    private String networkAddress;   
    private String broadcastAddress; 
    private String maskAddress;      
    private int prefijo;             
    private String primerHost;       
    private String ultimoHost;        
    private int hostDiponibles;     
    private int hostPerdidos;

    // Constructor de la clase Subnet solicitamos el nombre y los hosts requeridos
    public Subnet(String name, int hostRequeridos) {
        this.name = name;
        this.hostRequeridos = hostRequeridos;
    }
    // --- GETTERS Se obtendran los valores desde la interfaz grafica ---
    public String getName() { return name; }
    public int getHostRequeridos() { return hostRequeridos; }
    public String getNetworkAddress() { return networkAddress; }
    public String getBroadcastAddress() { return broadcastAddress; }
    public String getMaskAddress() { return maskAddress; }
    public int getPrefijo() { return prefijo; }
    public String getPrimerHost() { return primerHost; }
    public String getUltimohost() { return ultimoHost; }
    public int getHostDisponibles() { return hostDiponibles; }
    public int getHostPerdidos() { return hostPerdidos; }
    // Metodo paa guardar todos los datos y calcularlos valores de salida  
    public void setRedData(String networkAddress, String broadcastAddress, String maskAddress,
                              int prefijo, String primerHost, String ultimoHost,
                              int hostDisponibles) {
        this.networkAddress = networkAddress;
        this.broadcastAddress = broadcastAddress;
        this.maskAddress = maskAddress;
        this.prefijo = prefijo;
        this.primerHost = primerHost;
        this.ultimoHost = ultimoHost;
        this.hostDiponibles = hostDisponibles;
       
    // Calculo de los host perdidos 
        this.hostPerdidos = (hostDisponibles > hostRequeridos) ? (hostDisponibles - hostRequeridos) : 0;
    }
    @Override
    public String toString() { 
        return "Subnet{" +
                "name='" + name + '\'' +
                ", hostRequeridos=" + hostRequeridos +
                ", networkAddress='" + networkAddress + '\'' +
                ", broadcastAddress='" + broadcastAddress + '\'' +
                ", maskAddress='" + maskAddress + '\'' +
                ", prefijo=" + prefijo +
                ", primerHost='" + primerHost + '\'' +
                ", ultimoHost='" + ultimoHost + '\'' +
                ", hostDiponibles=" + hostDiponibles +
                ", hostPerdidos=" + hostPerdidos +
                '}';
    }

}
