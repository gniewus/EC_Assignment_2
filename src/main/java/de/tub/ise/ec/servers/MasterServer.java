package de.tub.ise.ec.servers;

import de.tub.ise.ec.messageHandlers.SampleMessageHandler;
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
 * Class that extends the SlaveServer.
 * From this class client can apart from reading from the keystore also write to it.
 * <p>
 * This class propagate the changes to it's slaves
 *
 * @author Jacek Janczura
 */
public class MasterServer extends SlaveServer {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    //TODO Find out where to measure the time to calculate latency and staleness
    // TODO Think about measuring more than one timestamp
    //Mapa zawierająca: numer operacji oraz liste
    // Lista zawieta timestamp operacji write, klucz, wartość
    private Map<Integer, List<String>> operationsMap = new HashMap<>();


    public MasterServer(int port, String host) {
        // For simplicity let's run them on different ports

        // Server: register handler
        RequestHandlerRegistry reg = RequestHandlerRegistry.getInstance();
        reg.registerHandler("sampleMessageHandler", new SampleMessageHandler());



        // Server: start receiver
        try {
            Receiver receiver = new Receiver(port);
            receiver.start();
            log.info("Master listening on port : {}", port);

        } catch (IOException e) {
            log.error("Connection error: {}", e.getMessage(), e);
        }
    }
    public MasterServer() {
        // For simplicity let's run them on different ports
        int port = 8001;

        // Server: register handler
        RequestHandlerRegistry reg = RequestHandlerRegistry.getInstance();
        reg.registerHandler("sampleMessageHandler", new SampleMessageHandler());



        // Server: start receiver
        try {
            Receiver receiver = new Receiver(port);
            receiver.start();
            log.info("Master listening on port : {}", port);

        } catch (IOException e) {
            log.error("Connection error: {}", e.getMessage(), e);
        }
    }

    private void write() {

    }

    public Map<Integer, List<String>> getOperationsMap() {
        return operationsMap;
    }
}
