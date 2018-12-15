package de.tub.ise.ec.messagehandlers.messages;

import de.tub.ise.ec.kv.KeyValueInterface;
import de.tub.ise.hermes.Request;
import de.tub.ise.hermes.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * Class that represents update keys message. It is used for internal communication betwenn master and slave.
 * It locally updates the value under the given key.
 * @author Jacek Janczura
 */
public class UpdateKey extends Message {

    public UpdateKey(Request request, KeyValueInterface store) {
        super(request, store);
    }

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    String key = items.get(1).toString();
    String value = items.get(2).toString();
    String transactionId = items.get(3).toString();

    public boolean updateValueLocally(){
        return store.store(key, value);
    }

    @Override
    public Response respond() {
        Boolean isLocalyStored = updateValueLocally();
        if(isLocalyStored){
            log.info("Staleness Stop | {} | {}", key, transactionId);
            return new Response("Sync | Update value " + value + " under the key " + key + " result " + isLocalyStored, isLocalyStored, request);
        }
        return null;
    }
}
