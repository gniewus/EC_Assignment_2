package de.tub.ise.ec.servers;

import de.tub.ise.ec.messagehandlers.MasterStorageMessageHandler;
import de.tub.ise.hermes.Receiver;
import de.tub.ise.hermes.RequestHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ResourceBundle;

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

    private int port;
    private String host;
    private Receiver receiver;
    private RequestHandlerRegistry requestHandlerRegistry;
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("config");

    public MasterServer() {
        this.port = Integer.valueOf(RESOURCE_BUNDLE.getString("portMaster"));
        this.host = RESOURCE_BUNDLE.getString("hostMaster");

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

    public MasterServer(int port, String host) {
        this.port = port;
        this.host = host;

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

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public String getHost() {
        return host;
    }
}
