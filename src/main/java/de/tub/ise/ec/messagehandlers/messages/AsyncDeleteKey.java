package de.tub.ise.ec.messageHandlers.messages;

import de.tub.ise.ec.clients.Client;
import de.tub.ise.ec.kv.KeyValueInterface;
import de.tub.ise.hermes.Request;
import de.tub.ise.hermes.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * Class that represents add value message
 * It is sent from client to master, and triggers asynchronous replication.
 * @author Tomasz Tkaczyk
 */

public class AsyncDeleteKey extends Message {

    public AsyncDeleteKey(Request request, KeyValueInterface store) {
        super(request, store);
    }

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private void deleteLocaly(String key){
        store.delete(key);
    }
    String key = (String) items.get(1);
    String transactionId = (String) items.get(2);
    Client client = new Client();

    @Override
    public Response respond() {
        deleteLocaly(key);
        log.info("Value stored on master | {} | {}",key,transactionId);
        client.sendAsyncMsgToSlave(client.delete(key,transactionId));
        return new Response("Key " + key + " deleted. ", true, request );
    }
}
