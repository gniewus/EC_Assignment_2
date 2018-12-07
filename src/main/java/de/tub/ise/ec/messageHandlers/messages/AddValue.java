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

    @Override
    public Response sendMessage() {
        String key = items.get(1).toString();
        String value = items.get(2).toString();
        boolean isStored = store.store(key, value);
        return new Response("Store value " + value + " under the key "+ key + " result " + isStored, isStored, request );
    }
}
