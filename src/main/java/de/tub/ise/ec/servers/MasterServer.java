package de.tub.ise.ec.servers;

import de.tub.ise.ec.SampleMessageHandler;
import de.tub.ise.ec.kv.FileSystemKVStore;
import de.tub.ise.ec.kv.KeyValueInterface;
import de.tub.ise.hermes.Receiver;
import de.tub.ise.hermes.Request;
import de.tub.ise.hermes.RequestHandlerRegistry;

import java.io.IOException;

/**
 * Class that extends the SlaveServer.
 * From this class client can apart from reading from the keystore also write to it.
 * <p>
 * This class propagate the changes to it's slaves
 *
 * @author Jacek Janczura
 */
public class MasterServer extends SlaveServer {

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
            System.out.println("Master listening on port : " + port);

        } catch (IOException e) {
            System.out.println("Connection error: " + e);
        }
    }

    private void write() {

    }





}
