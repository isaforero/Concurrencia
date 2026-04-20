import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        
        if (args.length < 1) {
            System.out.println("Uso: Ingrese la ruta del archivo .txt");
            return;
        }

        int  numSensores_ni, numEventos_ne, numClasificasdores_nc, numServidores_ns, tamBuzonClasificador_tam1, tamBuzonConsolidado_tam2;

        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
            numSensores_ni   = Integer.parseInt(br.readLine().trim());
            numEventos_ne   = Integer.parseInt(br.readLine().trim());
            numClasificasdores_nc   = Integer.parseInt(br.readLine().trim());
            numServidores_ns   = Integer.parseInt(br.readLine().trim());
            tamBuzonClasificador_tam1 = Integer.parseInt(br.readLine().trim());
            tamBuzonConsolidado_tam2 = Integer.parseInt(br.readLine().trim());

        } 
        catch (IOException e) {
            System.out.println("Error leyendo el archivo: " + e.getMessage());
            return;
        } 
        catch (NumberFormatException e) {
            System.out.println("Error de formato en el archivo: " + e.getMessage());
            return;
        }

        Sensor[] sensores = new Sensor[numSensores_ni];
        Clasificador[] clasificadores = new Clasificador[numClasificasdores_nc];
        Servidor[] servidores = new Servidor[numServidores_ns];

        BuzonEvento buzonEvento = new BuzonEvento();
        BuzonAlerta buzonAlerta = new BuzonAlerta();

        BuzonClasificacion buzonClasificacion = new BuzonClasificacion(tamBuzonClasificador_tam1, numClasificasdores_nc);
        
        BuzonConsolidacion[] buzonesConsolidacion = new BuzonConsolidacion[numServidores_ns];

        for(int i = 0; i < numServidores_ns; i++){
            buzonesConsolidacion[i] = new BuzonConsolidacion((i+1), tamBuzonConsolidado_tam2);
        }

        Broker broker = new Broker(numEventos_ne * numSensores_ni  * (numSensores_ni + 1)/2, buzonEvento, buzonAlerta, buzonClasificacion);
        broker.start();

        Administrador administrador = new Administrador(buzonClasificacion, buzonAlerta, numClasificasdores_nc);
        administrador.start();

        for(int i = 0; i < numSensores_ni; i++){
            sensores[i] = new Sensor((i+1), numEventos_ne, numServidores_ns, buzonEvento); // Id, numEventos-input
            sensores[i].start();
        }

        for(int i = 0; i < numServidores_ns; i++){
            servidores[i] = new Servidor(buzonesConsolidacion[i]);
            servidores[i].start();
        }

        for(int i = 0; i < numClasificasdores_nc; i++){
            clasificadores[i] = new Clasificador(buzonClasificacion, buzonesConsolidacion);
            clasificadores[i].start();

        }   

        try{
            for(int i = 0; i < numSensores_ni; i++){
                sensores[i].join();
            }

            broker.join();
            administrador.join();

            for(int i = 0; i < numClasificasdores_nc; i++){
                clasificadores[i].join();
            }

            for(int i = 0; i < numServidores_ns; i++){
                servidores[i].join();
            }
        }
        catch(InterruptedException e){
            Thread.currentThread().interrupt();
            System.out.println("Ejecucion interrumpida: " + e.getMessage());
            return;
        }

        boolean buzonesVacios = buzonEvento.eventosEnElBuzon() == 0
                && buzonAlerta.eventosEnElBuzon() == 0
                && buzonClasificacion.eventosEnElBuzon() == 0;

        for(int i = 0; i < numServidores_ns; i++){
            buzonesVacios = buzonesVacios && buzonesConsolidacion[i].eventosEnElBuzon() == 0;
        }

        System.out.println("Buzones vacios al final: " + buzonesVacios);

    }
}
