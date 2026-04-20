import java.util.ArrayList; 

public class BuzonConsolidacion{
    
    private ArrayList<Evento> eventos;
    private int tam;
    private int tipo;

    public BuzonConsolidacion(int pTipo, int pTam){
        tam = pTam;
        tipo = pTipo;
        eventos = new ArrayList<Evento>();
    }

    public synchronized void agregarEvento(Evento pEventoAgregar){
        while(eventos.size() == tam){
            try{
                wait();
            }
            catch(InterruptedException e){
                Thread.currentThread().interrupt();
                return;
            }
        }
        eventos.add(pEventoAgregar);
        notify();
    }

    public synchronized Evento retirarEvento(){
        while(eventos.size() == 0){
            try{
                wait();
            }
            catch(InterruptedException e){
                Thread.currentThread().interrupt();
                return null;
            }
        }
        Evento eventoRetirado = eventos.remove(0);
        notify();
        return eventoRetirado;
    }

    public void darEventos(){
        System.out.println("HOLA BUZON CONSOLIDACION - " + eventos.size());
    }

    public synchronized int eventosEnElBuzon(){
        return eventos.size();
    }
}
