import java.util.ArrayList; 

public class BuzonEvento{

    private ArrayList<Evento> eventos;

    public BuzonEvento(){
        eventos = new ArrayList<Evento>();
    }

    public synchronized void depositarEvento(Evento pEvento){
        eventos.add(pEvento);
        notify(); //notifica cuando ha llegado un evento
    }

    public synchronized Evento retirarEvento(){
        while(eventos.size() == 0){
            try{
                wait();
            }
            catch(Exception e){
                //
            }
        }
        Evento EventoRetirar = eventos.get(0);
        eventos.remove(EventoRetirar);
        return EventoRetirar;
    }


    public int eventosEnElBuzon(){
        return eventos.size();
    }




    

    public void darEventos(){
        System.out.println("HOLA BUZON EVENTO" + "   -   "+ eventos.size());
    }
}