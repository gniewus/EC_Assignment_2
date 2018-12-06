package de.tub.ise.ec.servers;

import de.tub.ise.ec.SampleMessageHandler;
import de.tub.ise.ec.kv.FileSystemKVStore;
import de.tub.ise.ec.kv.KeyValueInterface;
import de.tub.ise.hermes.Receiver;
import de.tub.ise.hermes.Request;
import de.tub.ise.hermes.RequestHandlerRegistry;
import java.io.IOException;

/**
 * From this class client can only read the value from the keystore
 *
 * This class can propagate the values to other slaves in asynchronous replication
 *
 * @author Jacek Janczura
 */
public class SlaveServer {

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
            System.out.println("Slave listening on port : " + port);
        } catch (IOException e) {
            System.out.println("Connection error: " + e);
        }
    }

}
