public class Clasificador extends Thread{

    private BuzonClasificacion buzonClasificacion;
    private BuzonConsolidacion[] buzonesConsolidacion;

    public Clasificador(BuzonClasificacion pBuzonClasificacion, BuzonConsolidacion[] pBuzonesConsolidacion){
        buzonClasificacion = pBuzonClasificacion;
        buzonesConsolidacion = pBuzonesConsolidacion;
    }

    public void run(){
        boolean terminar = false;

        while(!terminar){
            Evento eventoRetirado = buzonClasificacion.retirarEvento();
            if(eventoRetirado.darEventoFin()){
                if(buzonClasificacion.identificarUltimoClasificador()){
                    for(int i=0; i<buzonesConsolidacion.length; i++){
                        Evento eventoFin = new Evento();
                        buzonesConsolidacion[i].agregarEvento(eventoFin);
                    }
                }
                terminar = true;
            }
            else{
                int tipo = eventoRetirado.darTipoServidor();
                buzonesConsolidacion[tipo-1].agregarEvento(eventoRetirado);
            }
        }
    }
}