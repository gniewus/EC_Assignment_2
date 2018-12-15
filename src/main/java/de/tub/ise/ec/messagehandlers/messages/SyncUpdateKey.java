package de.tub.ise.ec.messagehandlers.messages;

import de.tub.ise.ec.clients.Client;
import de.tub.ise.ec.kv.KeyValueInterface;
import de.tub.ise.hermes.Request;
import de.tub.ise.hermes.Response;

/**
 * Class that represents update keys message
 * It is sent by client to master, and trigger synchronous replication .
 *
 * @author Tomasz Tkaczyk
 */
public class SyncUpdateKey extends Message {

    public SyncUpdateKey(Request request, KeyValueInterface store) {
        super(request, store);
    }

    String key = items.get(1).toString();
    String value = items.get(2).toString();
    String transactionId = items.get(3).toString();
    Client client = new Client();

    public boolean updateLocally() {
        return store.store(key, value);
    }

    @Override
    public Response respond() {
        boolean isLocalyStored = updateLocally();
        if (isLocalyStored) {
            client.sendMsgToSlave(client.update(key, value, transactionId));
            return new Response("Sync | Update value " + value + " under the key " + key + " result " + isLocalyStored, isLocalyStored, request);
        } else {
            return null;
        }
    }
}
