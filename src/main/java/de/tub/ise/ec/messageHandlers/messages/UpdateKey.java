package de.tub.ise.ec.messageHandlers.messages;

import de.tub.ise.ec.kv.KeyValueInterface;
import de.tub.ise.hermes.Request;
import de.tub.ise.hermes.Response;

/**
 * Class that represents update keys message
 *
 * @author Jacek Janczura
 */
public class UpdateKey extends Message {

    public UpdateKey(Request request, KeyValueInterface store) {
        super(request, store);
    }

    @Override
    public Response respond() {
        return null;
    }
}
