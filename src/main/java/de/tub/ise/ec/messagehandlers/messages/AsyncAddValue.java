package de.tub.ise.ec.messagehandlers.messages;

import de.tub.ise.ec.clients.Client;
import de.tub.ise.ec.kv.KeyValueInterface;
import de.tub.ise.hermes.Request;
import de.tub.ise.hermes.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * Class that represents add value message.
 * It is sent from client to master, and triggers asynchronous replication.
 *
 * @author Tomasz Tkaczyk
 */

public class AsyncAddValue extends Message {

    String key = items.get(1).toString();
    String value = items.get(2).toString();
    String transactionId = items.get(3).toString();
    Client client = new Client();
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public AsyncAddValue(Request request, KeyValueInterface store) {
        super(request, store);
    }

    private boolean addValueToKV() {
        return store.store(key, value);
    }

    @Override
    public Response respond() {
        boolean isLocalyStored = addValueToKV();
        log.info("Value stored | {} | {}",key,transactionId);
        client.sendAsyncMsgToSlave(client.write(key,value,transactionId));
        return new Response("Async | Store value " + value + " under the key " + key + " result " + isLocalyStored, isLocalyStored, request);
    }
}
