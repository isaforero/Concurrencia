import java.util.Random;

public class Servidor extends Thread{
    private BuzonConsolidacion buzonConsolidacion;
    private Random random;

    public Servidor(BuzonConsolidacion pBuzonConsolidacion){
        buzonConsolidacion = pBuzonConsolidacion;
        random = new Random();
    }

    public void run(){
        boolean terminar = false;
        while(!terminar){
            Evento eventoRetirado = buzonConsolidacion.retirarEvento();
            if(eventoRetirado == null){
                return;
            }
            if(eventoRetirado.darEventoFin()){
                terminar = true;
            }
            else{
                int tiempolectura = random.nextInt(901)+100;
                try{
                    Thread.sleep(tiempolectura);
                }
                catch(InterruptedException e){
                    Thread.currentThread().interrupt();
                    return;
                }
                //System.out.println(eventoRetirado.darTipoServidor() + " - " + eventoRetirado.darNombre());
            }
        }
    }
} 
