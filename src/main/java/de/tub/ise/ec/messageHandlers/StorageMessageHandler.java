package de.tub.ise.ec.messageHandlers;

import de.tub.ise.ec.kv.FileSystemKVStore;
import de.tub.ise.ec.kv.KeyValueInterface;
import de.tub.ise.ec.messageHandlers.messages.Message;
import de.tub.ise.ec.messageHandlers.messages.MessageFactory;
import de.tub.ise.hermes.IRequestHandler;
import de.tub.ise.hermes.Request;
import de.tub.ise.hermes.Response;

/**
 * Created by tomasztkaczyk on 06.12.18.
 */
public class StorageMessageHandler implements IRequestHandler {

    KeyValueInterface store;
    //Boolean isMaster;
    public  StorageMessageHandler(String path){
        store = new FileSystemKVStore(path);
    }


    @Override
    public Response handleRequest(Request req) {
        return sendMessage(MessageFactory.getMessageType(req, store));
    }

    private Response sendMessage(Message message){
        return message.sendMessage();
    }

    @Override
    public boolean requiresResponse() {
        return true;
    }
}
