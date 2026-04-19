public class Evento{

    private int id;
    private String nombre;
    private Sensor sensor;
    private int tipoServidor_ns;

    private boolean eventoFin;

    public Evento(int pId, int pTipoServidor_ns ,Sensor pSensor){
        id = pId;
        nombre = pSensor.darNombre() + "-E"+pId;
        sensor = pSensor;
        tipoServidor_ns = pTipoServidor_ns;
        eventoFin = false;
    }

    public Evento(){
        eventoFin = true;
    }

    public boolean darEventoFin(){
        return eventoFin;
    }

    public String darNombre(){
        return nombre;
    }


    public int darTipoServidor(){
        return tipoServidor_ns;
    }

}