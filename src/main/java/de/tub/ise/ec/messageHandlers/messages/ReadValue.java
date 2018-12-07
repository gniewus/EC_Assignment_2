package de.tub.ise.ec.messageHandlers.messages;

import de.tub.ise.ec.kv.KeyValueInterface;
import de.tub.ise.hermes.Request;
import de.tub.ise.hermes.Response;

/**
 * Class that represents read value message
 *
 * @author Jacek Janczura
 */

public class ReadValue extends Message{

    public ReadValue(Request request, KeyValueInterface store) {
        super(request, store);
    }

    @Override
    public Response sendMessage() {
        String key = (String) items.get(1);
        String value = store.getValue(key).toString();
        return new Response("Key "+ key + " Value: " + value, true, request, value);
    }
}
