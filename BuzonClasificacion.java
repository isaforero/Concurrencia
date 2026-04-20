import java.util.ArrayList; 

public class BuzonClasificacion{

    private ArrayList<Evento> eventos;
    private int tam;
    private int clasificadoresActivos;


    public BuzonClasificacion(int pTam, int pNumClasificadores){
        eventos = new ArrayList<Evento>();
        tam = pTam;
        clasificadoresActivos = pNumClasificadores;

    }

    public void agregarEvento(Evento pEventoAgregar){
        while(true){
            synchronized(this){
                if(eventos.size() < tam){
                    eventos.add(pEventoAgregar);
                    return;
                }
            }
            // Espera semi-activa requerida por el caso para este buzon limitado.
            Thread.yield();
        }
    }

    public Evento retirarEvento(){
        while(true){
            synchronized(this){
                if(eventos.size() > 0){
                    Evento eventoRetirado = eventos.get(0);
                    eventos.remove(eventoRetirado);
                    return eventoRetirado;
                }
            }
            Thread.yield(); // si estaba vacío, cede turno e intenta de nuevo
        }
    }

    public synchronized boolean identificarUltimoClasificador(){
        clasificadoresActivos = clasificadoresActivos-1;
        return clasificadoresActivos == 0;
    }

    public synchronized int eventosEnElBuzon(){
        return eventos.size();
    }


    public void darEventos(){
        System.out.println("HOLA BUZON CLASIFICACION" + "   -   "+ eventos.size());

    }

}
