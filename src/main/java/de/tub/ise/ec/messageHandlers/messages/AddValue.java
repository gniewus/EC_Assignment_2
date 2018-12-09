package de.tub.ise.ec.messageHandlers.messages;

import de.tub.ise.ec.kv.KeyValueInterface;
import de.tub.ise.hermes.Request;
import de.tub.ise.hermes.Response;

/**
 * Class that represents add value message
 *
 * @author Jacek Janczura
 */

public class AddValue extends Message {

    public AddValue(Request request, KeyValueInterface store) {
        super(request, store);
    }

    String key = items.get(1).toString();
    String value = items.get(2).toString();

    public boolean addValueToKV() {
        return store.store(key, value);
    }

    @Override
    public Response respond() {
        Boolean isLocalyStored = addValueToKV();
        Response res = new Response("Store value " + value + " under the key " + key + " result " + isLocalyStored, isLocalyStored, request);
        return res;
    }
}
