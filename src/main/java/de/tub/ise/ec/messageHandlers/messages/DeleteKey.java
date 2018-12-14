package de.tub.ise.ec.messageHandlers.messages;

import de.tub.ise.ec.kv.KeyValueInterface;
import de.tub.ise.hermes.Request;
import de.tub.ise.hermes.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * Class that represents delete key message
 * This is internal message which is sent from Master to Slave
 * @author Jacek Janczura
 */

public class DeleteKey extends Message {
    public DeleteKey(Request request, KeyValueInterface store) {
        super(request,store);
    }
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private void deleteLocaly(String key){
        store.delete(key);

    }
    String key = (String) items.get(1);
    String id = (String) items.get(2);

    @Override
    public Response respond() {
        deleteLocaly(key);
        log.info("Replication success | {} | {}",key,id);
        return new Response("Key " + key + " deleted. ", true, request );
    }
}
