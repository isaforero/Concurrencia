import java.util.ArrayList; 

public class BuzonAlerta{

    private ArrayList<Evento> eventos;

    public BuzonAlerta(){
        eventos = new ArrayList<Evento>();
    }

    public synchronized void agregarEvento(Evento pEventoAgregar){
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
        return eventoRetirado;
    }

    public synchronized int eventosEnElBuzon(){
        return eventos.size();
    }

    public void darEventos(){
        System.out.println("HOLA BUZON ALERTA" + "   -   "+ eventos.size());
    }
}
