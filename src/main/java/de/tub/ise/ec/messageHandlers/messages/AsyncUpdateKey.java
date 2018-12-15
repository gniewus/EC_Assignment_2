package de.tub.ise.ec.messagehandlers.messages;

import de.tub.ise.ec.clients.Client;
import de.tub.ise.ec.kv.KeyValueInterface;
import de.tub.ise.hermes.Request;
import de.tub.ise.hermes.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * Class that represents update keys message
 * It is sent by client to master, and trigger synchronous replication .
 *
 * @author Tomasz Tkaczyk
 */
public class AsyncUpdateKey extends Message {

    String key = items.get(1).toString();
    String value = items.get(2).toString();
    String transactionId = items.get(3).toString();
    Client client = new Client();
    boolean isLocalyStored = false;
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public AsyncUpdateKey(Request request, KeyValueInterface store) {
        super(request, store);
    }

    private boolean updateLocally() {
        return store.store(key, value);
    }

    @Override
    public Response respond() {
        isLocalyStored = updateLocally();
        log.info("Value updated | {} | {}",key,transactionId);
        client.sendAsyncMsgToSlave(client.update(key, value, transactionId));
        return new Response("Async | Update value " + value + " under the key " + key + " result " + isLocalyStored, isLocalyStored, request);
    }
}
