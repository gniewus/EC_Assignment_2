package de.tub.ise.ec.servers;

import de.tub.ise.hermes.IRequestHandler;
import de.tub.ise.hermes.Request;
import de.tub.ise.hermes.Response;
import de.tub.ise.ec.kv.*;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tomasztkaczyk on 06.12.18.
 */
public class StorageMessageHandler implements IRequestHandler {
    KeyValueInterface store;
    //Boolean isMaster;
    public  StorageMessageHandler(String path){
        store = new FileSystemKVStore(path);
    }


    @Override
    public Response handleRequest(Request req) {

        String target = req.getTarget();
        String sender = req.getOriginator();

        List<Serializable> items = req.getItems();
        // Let's agree that the first item in the array will be the actual CRUD command
        String command = (String) items.get(0);
        Response res = null;


        switch (command) {
            case "listKeys":
                List<String> keys = store.getKeys();
                res = new Response("List of the avalible keys", true,req, (Serializable) keys);
                break;
            case "readValue":
                break;
        }

        // Test kv store
        return res;
    }

    @Override
    public boolean requiresResponse() {
        return true;
    }
}
