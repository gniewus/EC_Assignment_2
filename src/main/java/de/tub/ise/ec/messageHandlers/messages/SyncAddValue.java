package de.tub.ise.ec.messageHandlers.messages;

import de.tub.ise.ec.kv.KeyValueInterface;
import de.tub.ise.hermes.Request;
import de.tub.ise.hermes.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.tub.ise.ec.clients.*;

import java.lang.invoke.MethodHandles;

/**
 * Class that represents add value message
 *
 * @author Jacek Janczura
 */

public class SyncAddValue extends Message {

    public SyncAddValue(Request request, KeyValueInterface store) {
        super(request, store);
    }

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    String key = items.get(1).toString();
    String value = items.get(2).toString();

    public boolean addValueToKV() {
        return store.store(key, value);
    }

    @Override
    public Response respond() {
        Boolean isLocalyStored = addValueToKV();
        Client client = new Client();
        client.syncSendToSlave(client.write(key,value));
        Response res = new Response("Sync | Store value " + value + " under the key " + key + " result " + isLocalyStored, isLocalyStored, request);
        return res;
    }
}
