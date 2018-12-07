package de.tub.ise.ec.servers;

import de.tub.ise.ec.messageHandlers.SampleMessageHandler;
import de.tub.ise.hermes.Receiver;
import de.tub.ise.hermes.RequestHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

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
    //Receiver receiver;
    public MasterServer(int port, String host) {
        super(port, host);
        // For simplicity let's run them on different ports

        // Server: register handler
        requestHandlerRegistry = RequestHandlerRegistry.getInstance();
        requestHandlerRegistry.registerHandler("sampleMessageHandler", new SampleMessageHandler());

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
        super();
        // For simplicity let's run them on different ports
        port = 8001;

        // Server: register handler
        requestHandlerRegistry = RequestHandlerRegistry.getInstance();
        requestHandlerRegistry.registerHandler("sampleMessageHandler", new SampleMessageHandler());

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
