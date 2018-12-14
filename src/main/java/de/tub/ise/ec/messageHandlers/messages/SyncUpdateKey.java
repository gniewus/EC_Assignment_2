package de.tub.ise.ec.messageHandlers.messages;

import de.tub.ise.ec.clients.Client;
import de.tub.ise.ec.kv.KeyValueInterface;
import de.tub.ise.hermes.Request;
import de.tub.ise.hermes.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * Class that represents update keys message
 * It is sent by client to master, and trigger synchronous replication .
 *
 * @author Jacek Janczura
 */
public class SyncUpdateKey extends Message {

    public SyncUpdateKey(Request request, KeyValueInterface store) {
        super(request, store);
    }

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    String key = items.get(1).toString();
    String value = items.get(2).toString();
    String transactionId = items.get(3).toString();
    Client client = new Client();

    public boolean updateLocally() {
        return store.store(key, value);
    }

    @Override
    public Response respond() {
        Boolean isLocalyStored = updateLocally();
        if (isLocalyStored) {
            client.sendSyncMsgToSlave(client.update(key, value, transactionId));
            return new Response("Sync | Update value " + value + " under the key " + key + " result " + isLocalyStored, isLocalyStored, request);
        } else {
            return null;
        }
    }
}
