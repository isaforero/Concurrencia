
import java.util.Random;

public class Administrador extends Thread{

    private BuzonClasificacion buzonClasificacion;
    private BuzonAlerta buzonAlerta;

    private int numClasificadores;

    private Random random;

    public Administrador(BuzonClasificacion pBuzonClasificacion, BuzonAlerta pBuzonAlerta, int pNumClasificadores){
        
        buzonClasificacion = pBuzonClasificacion;
        buzonAlerta = pBuzonAlerta;
        numClasificadores = pNumClasificadores;

        random = new Random();
    }

    public void run(){
        boolean terminar = false;

        while(!terminar){
            Evento eventoRetirado = buzonAlerta.retirarEvento();
            if(eventoRetirado.darEventoFin()){
                terminar = true;
            }
            else{
                int decisionEnvioBuzon = random.nextInt(21);
                if(decisionEnvioBuzon%4 == 0){
                    
                    buzonClasificacion.agregarEvento(eventoRetirado);
                }
                else{
                    //System.out.println("Evento Anomalo: "+eventoRetirado.darNombre());
                    // Los demás eventos son enviados fuera del scope del caso.

                }
            }
        }

        for(int i=0; i<numClasificadores; i++){
            Evento eventoFinClasificadores = new Evento();
            buzonClasificacion.agregarEvento(eventoFinClasificadores);
        }
    }
}