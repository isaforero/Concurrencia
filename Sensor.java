import java.util.Random;

public class Sensor extends Thread{
    private int id;
    private String nombre;
    private int numEventos_ne; //Número de eventos que genera el sensor
    private int numServidores_ns;

    private BuzonEvento buzonEventos;
    
    private Random random;

    public Sensor(int pId, int pNumEventos_ne, int pNumServidores_ns, BuzonEvento pBuzonEventos){
        id = pId;
        nombre = "S"+pId;
        numEventos_ne = pNumEventos_ne * pId;
        buzonEventos = pBuzonEventos;
        numServidores_ns = pNumServidores_ns;
        random = new Random();
    }

    public void run(){
        for(int i = 0; i < numEventos_ne; i++){
            Evento evento = new Evento((i+1), random.nextInt(numServidores_ns)+1, this);
            
            buzonEventos.depositarEvento(evento);
        }
    }
    
    public String darNombre(){
        return nombre;
    }

}