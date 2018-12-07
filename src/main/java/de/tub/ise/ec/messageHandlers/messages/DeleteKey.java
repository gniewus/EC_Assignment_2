package de.tub.ise.ec.messageHandlers.messages;

import de.tub.ise.ec.kv.KeyValueInterface;
import de.tub.ise.hermes.Request;
import de.tub.ise.hermes.Response;

/**
 * Class that represents delete key message
 *
 * @author Jacek Janczura
 */

public class DeleteKey extends Message {
    public DeleteKey(Request request, KeyValueInterface store) {
        super(request, store);
    }

    @Override
    public Response sendMessage() {
        String key = (String) items.get(1);
        store.delete(key);
        return new Response("Key " + key + " deleted. ", true, request );
    }
}
