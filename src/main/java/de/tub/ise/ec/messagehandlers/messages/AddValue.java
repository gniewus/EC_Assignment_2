package de.tub.ise.ec.messagehandlers.messages;

import de.tub.ise.ec.kv.KeyValueInterface;
import de.tub.ise.hermes.Request;
import de.tub.ise.hermes.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * Class that represents add value message.
 * This is internal message which is sent from Master to Slave
 *
 * @author Jacek Janczura
 */

public class AddValue extends Message {

    String key = items.get(1).toString();
    String value = items.get(2).toString();
    String transactionId = items.get(3).toString();
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public AddValue(Request request, KeyValueInterface store) {
        super(request, store);
    }



    private boolean addValueToKV() {
        return store.store(key, value);
    }

    @Override
    public Response respond() {
        boolean isLocalyStored = addValueToKV();
        log.info("Staleness Stop | {} | {}", key, transactionId);
        return new Response("Store value " + value + " under the key " + key + " result " + isLocalyStored, isLocalyStored, request);
    }
}
