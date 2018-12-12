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
 *
 * @author Jacek Janczura
 */

public class AsyncAddValue extends Message {

    public AsyncAddValue(Request request, KeyValueInterface store) {
        super(request, store);
    }

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    String key = items.get(1).toString();
    String value = items.get(2).toString();
    String transactionId = items.get(3).toString();
    Client client = new Client();
    public boolean addValueToKV() {
        return store.store(key, value);
    }

    @Override
    public Response respond() {
        Boolean isLocalyStored = addValueToKV();
        log.info("Value stored on master | {} | {}",key,transactionId);
        client.asyncSendToSlave(client.write(key,value,transactionId));
        return new Response("Async | Store value " + value + " under the key " + key + " result " + isLocalyStored, isLocalyStored, request);
    }
}
