package de.tub.ise.ec.messagehandlers;

import de.tub.ise.ec.kv.FileSystemKVStore;
import de.tub.ise.ec.kv.KeyValueInterface;
import de.tub.ise.ec.messagehandlers.factories.MasterMessageFactory;
import de.tub.ise.ec.messagehandlers.messages.Message;
import de.tub.ise.hermes.IRequestHandler;
import de.tub.ise.hermes.Request;
import de.tub.ise.hermes.Response;

/**
 * Class handles the messages sent by Master server
 *
 * Created by tomasztkaczyk on 06.12.18.
 */
public class MasterStorageMessageHandler implements IRequestHandler {

    private KeyValueInterface store;


    public MasterStorageMessageHandler(String path) {
        store = new FileSystemKVStore(path);
    }

    /**
     * Methode sends message built using message factory
     *
     * @param req request to be send
     * @return response from server
     */
    @Override
    public Response handleRequest(Request req) {
        return sendMessage(MasterMessageFactory.getMessageType(req, store));
    }

    private Response sendMessage(Message message) {
        return message.respond();
    }

    //:TODO Change this to false in case of async requests.
    @Override
    public boolean requiresResponse() {
        return true;
    }

}
