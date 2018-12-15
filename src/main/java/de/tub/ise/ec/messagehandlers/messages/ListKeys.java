package de.tub.ise.ec.messageHandlers.messages;

import de.tub.ise.ec.kv.KeyValueInterface;
import de.tub.ise.hermes.Request;
import de.tub.ise.hermes.Response;

import java.io.Serializable;
import java.util.List;

/**
 * Class that represents list keys message
 *
 * @author Jacek Janczura
 */

public class ListKeys extends Message {

    public ListKeys(Request request, KeyValueInterface store) {
        super(request, store);
    }

    @Override
    public Response respond() {
        List<String> keys = store.getKeys();
        return new Response("List of the available keys", true, request, (Serializable) keys);

    }

}
