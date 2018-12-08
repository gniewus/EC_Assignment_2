package de.tub.ise.ec.servers;

import de.tub.ise.ec.messageHandlers.MasterStorageMessageHandler;
import de.tub.ise.hermes.Receiver;
import de.tub.ise.hermes.RequestHandlerRegistry;
import de.tub.ise.hermes.Sender;
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
public class MasterServer implements IServer {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private Map<Integer, List<String>> operationsMap = new HashMap<>();

    private int port;
    private String host;
    private Receiver receiver;
    private Sender sender;
    private RequestHandlerRegistry requestHandlerRegistry;

    public MasterServer(int port, String host) {
        //super(port, host);
        this.port = port;
        this.host = host;
        // For simplicity let's run them on different ports

        // Server: register handler
        requestHandlerRegistry = RequestHandlerRegistry.getInstance();
        requestHandlerRegistry.registerHandler("storageMessageHandler", new MasterStorageMessageHandler("./kv_store_master"));

        // Server: start receiver
        try {
             receiver = new Receiver(port);
            receiver.start();
            log.info("Master listening on port : {}", port);

        } catch (IOException e) {
            log.error("Connection error: {}", e.getMessage(), e);
        }
    }

    public MasterServer() {
        //super(8001, "127.0.0.1");
        // For simplicity let's run them on different ports
        this.port = 8001;
        this.host = "127.0.0.1";
        // Server: register handler
        requestHandlerRegistry = RequestHandlerRegistry.getInstance();
        //requestHandlerRegistry.registerHandler("sampleMessageHandler", new SampleMessageHandler());
        requestHandlerRegistry.registerHandler("storageMessageHandler", new MasterStorageMessageHandler("./kv_store_master"));


        // Server: start receiver
        try {
            receiver = new Receiver(port);
            receiver.start();
            log.info("Master listening on port : {}", port);

        } catch (IOException e) {
            log.error("Connection error: {}", e.getMessage(), e);
        }
    }

    private void write() {

    }

}
