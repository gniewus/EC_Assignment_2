package de.tub.ise.ec.servers;

import de.tub.ise.ec.messagehandlers.SlaveStorageMessageHandler;
import de.tub.ise.hermes.Receiver;
import de.tub.ise.hermes.RequestHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ResourceBundle;

/**
 * From this class client can only read the value from the keystore
 * <p>
 * This class can propagate the values to other slaves in asynchronous replication
 *
 * @author Jacek Janczura
 */
public class SlaveServer implements IServer {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private int port;
    private String host;
    private Receiver receiver;
    private RequestHandlerRegistry requestHandlerRegistry;
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("config");

    public SlaveServer() {
        port = Integer.valueOf(RESOURCE_BUNDLE.getString("portSlave"));
        host = RESOURCE_BUNDLE.getString("hostSlave");

        // Server: register handler
        requestHandlerRegistry = RequestHandlerRegistry.getInstance();
        requestHandlerRegistry.registerHandler("storageMessageHandler", new SlaveStorageMessageHandler("./kv_store_slave"));

        // Server: start receiver
        try {
            receiver = new Receiver(port);
            receiver.start();
            log.info("Slave listening on port : {}", port);
        } catch (IOException e) {
            log.error("Connection error: {}", e.getMessage(), e);
        }
    }

    public SlaveServer(int port, String host) {

        this.port = port;
        this.host = host;

        // Server: register handler
        requestHandlerRegistry = RequestHandlerRegistry.getInstance();
        requestHandlerRegistry.registerHandler("storageMessageHandler", new SlaveStorageMessageHandler("./kv_store_slave"));

        // Server: start receiver
        try {
            receiver = new Receiver(port);
            receiver.start();
            log.info("Slave listening on port : {}", port);
        } catch (IOException e) {
            log.error("Connection error: {}", e.getMessage(), e);
        }
    }

    public void terminate() {
        receiver.terminate();
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
