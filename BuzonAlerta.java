import java.util.ArrayList; 

public class BuzonAlerta{

    private ArrayList<Evento> eventos;

    public BuzonAlerta(){
        eventos = new ArrayList<Evento>();
    }

    public synchronized void agregarEvento(Evento pEventoAgregar){
        eventos.add(pEventoAgregar);
    }

    public Evento retirarEvento(){
        while(eventos.size() == 0){
            try{
                Thread.yield();
            }
            catch(Exception e){
                //
            }
        }

        Evento eventoRetirado = eventos.get(0);
        eventos.remove(eventoRetirado);
        return eventoRetirado;
    }


    public void darEventos(){
        System.out.println("HOLA BUZON ALERTA" + "   -   "+ eventos.size());
    }
}