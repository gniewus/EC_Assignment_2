package de.tub.ise.ec.servers;

import de.tub.ise.ec.SampleMessageHandler;
import de.tub.ise.ec.kv.FileSystemKVStore;
import de.tub.ise.ec.kv.KeyValueInterface;
import de.tub.ise.hermes.Receiver;
import de.tub.ise.hermes.Request;
import de.tub.ise.hermes.RequestHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

/**
 * From this class client can only read the value from the keystore
 *
 * This class can propagate the values to other slaves in asynchronous replication
 *
 * @author Jacek Janczura
 */
public class SlaveServer {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    //KeyValueInterface store = new FileSystemKVStore("./kv_store_slave");


    public SlaveServer(){
        int port = 8000;
        String host = "127.0.0.1"; // localhost

        // Server: register handler
        RequestHandlerRegistry reg = RequestHandlerRegistry.getInstance();
        reg.registerHandler("storageMessageHandler", new StorageMessageHandler("./kv_store_slave"));

        // Server: start receiver
        try {
            Receiver receiver = new Receiver(port);
            receiver.start();
            log.info("Slave listening on port : {}", port);
        } catch (IOException e) {
            log.error("Connection error: {}", e.getMessage(), e);
        }
    }

}
