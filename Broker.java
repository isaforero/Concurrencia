import java.util.Random;

public class Broker extends Thread{
    private int totalEventos;
    private int eventosRetirados;


    private BuzonEvento buzonEventos;
    private BuzonAlerta buzonAlerta;
    private BuzonClasificacion buzonClasificacion;

    private Random random;
    
    public Broker(int pTotalEventos, BuzonEvento pBuzonEvento, BuzonAlerta pBuzonAlerta, BuzonClasificacion pBuzonClasificacion){
        
        buzonEventos = pBuzonEvento;
        totalEventos = pTotalEventos;

        buzonAlerta = pBuzonAlerta;
        buzonClasificacion = pBuzonClasificacion;

        random = new Random();
    }

    public void run(){
        while(eventosRetirados < totalEventos){

            Evento eventoRetirado = buzonEventos.retirarEvento();
            if(eventoRetirado == null){
                return;
            }
            eventosRetirados = eventosRetirados + 1;

            int decisionEnvioBuzon = random.nextInt(201);

            if(decisionEnvioBuzon%8 == 0){
                buzonAlerta.agregarEvento(eventoRetirado); // EVENTO ANOMALO
            }
            else{
                buzonClasificacion.agregarEvento(eventoRetirado); // EVENTO NORMAL
            }
        }

        Evento eventoFin = new Evento();
        buzonAlerta.agregarEvento(eventoFin);

    }
}
