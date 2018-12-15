package de.tub.ise.ec.messageHandlers.messages;

import de.tub.ise.ec.clients.Client;
import de.tub.ise.ec.kv.KeyValueInterface;
import de.tub.ise.hermes.Request;
import de.tub.ise.hermes.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * Class that represents delete key-value pairs message.
 * It is sent from client to master, and triggers synchronous replication.
 * @author Tomasz Tkaczyk
 */

public class SyncDeleteKey extends Message {

    public SyncDeleteKey(Request request, KeyValueInterface store) {
        super(request, store);
    }

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    Client client = new Client();

    private Boolean deleteLocaly(String key) {
        try {
            store.delete(key);
            return true;
        } catch (Exception msg) {
            log.error(msg.getMessage());
            return false;
        }

    }

    String key = (String) items.get(1);
    String transactionId = (String) items.get(1);

    @Override
    public Response respond() {
        if (deleteLocaly(key)) {
            client.sendSyncMsgToSlave(client.delete(key,transactionId));
            return new Response("Key " + key + " deleted. ", true, request );
        }else {
            return new Response("Failed to delete key "+key,false,request);
        }


    }
}
