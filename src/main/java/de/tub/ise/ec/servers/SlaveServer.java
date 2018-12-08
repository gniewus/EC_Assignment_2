package de.tub.ise.ec.servers;

import de.tub.ise.ec.messageHandlers.StorageMessageHandler;
import de.tub.ise.hermes.Receiver;
import de.tub.ise.hermes.RequestHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * From this class client can only read the value from the keystore
 *
 * This class can propagate the values to other slaves in asynchronous replication
 *
 * @author Jacek Janczura
 */
public class SlaveServer implements IServer{
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    //TODO Find out where to measure the time to calculate latency and staleness
    // TODO Think about measuring more than one timestamp
    //Mapa zawierająca: numer operacji oraz liste
    // Lista zawieta timestamp operacji write, klucz, wartość
    private Map<Integer, List<String>> operationsMap = new HashMap<>();

    private int port;
    private String host;
    private Receiver receiver;
    private RequestHandlerRegistry requestHandlerRegistry;

    public SlaveServer(int port, String host){

        // Server: register handler
         requestHandlerRegistry = RequestHandlerRegistry.getInstance();
        requestHandlerRegistry.registerHandler("storageMessageHandler", new StorageMessageHandler("./kv_store_slave"));

        // Server: start receiver
        try {
            receiver = new Receiver(port);
            receiver.start();
            log.info("Slave listening on port : {}", port);
        } catch (IOException e) {
            log.error("Connection error: {}", e.getMessage(), e);
        }
    }

    public SlaveServer(){
        port = 8000;
        host = "127.0.0.1"; // localhost

        // Server: register handler
        requestHandlerRegistry = RequestHandlerRegistry.getInstance();
        requestHandlerRegistry.registerHandler("storageMessageHandler", new StorageMessageHandler("./kv_store_slave"));

        // Server: start receiver
        try {
            receiver = new Receiver(port);
            receiver.start();
            log.info("Slave listening on port : {}", port);
        } catch (IOException e) {
            log.error("Connection error: {}", e.getMessage(), e);
        }
    }

    public void terminate(){
        //receiver.terminate();
    }
    public Map<Integer, List<String>> getOperationsMap() {
        return operationsMap;
    }

    public int getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }
}
