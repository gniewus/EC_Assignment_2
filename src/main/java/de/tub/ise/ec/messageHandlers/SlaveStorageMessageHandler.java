package de.tub.ise.ec.messageHandlers;

import de.tub.ise.ec.kv.FileSystemKVStore;
import de.tub.ise.ec.kv.KeyValueInterface;
import de.tub.ise.ec.messageHandlers.messages.MasterMessageFactory;
import de.tub.ise.ec.messageHandlers.messages.Message;
import de.tub.ise.ec.messageHandlers.messages.SlaveMessageFactory;
import de.tub.ise.hermes.IRequestHandler;
import de.tub.ise.hermes.Request;
import de.tub.ise.hermes.Response;

/**
 * Created by tomasztkaczyk on 06.12.18.
 */
public class SlaveStorageMessageHandler implements IRequestHandler {

    private KeyValueInterface store;


    public SlaveStorageMessageHandler(String path) {
        store = new FileSystemKVStore(path);
    }


    @Override
    public Response handleRequest(Request req) {
        return sendMessage(SlaveMessageFactory.getMessageType(req, store));
    }

    private Response sendMessage(Message message)  {
        return message.sendMessage();
    }

    //:TODO Change this to false in case of async requests.
    @Override
    public boolean requiresResponse() {
        return true;
    }

}
