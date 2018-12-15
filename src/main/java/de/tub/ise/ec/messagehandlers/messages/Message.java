package de.tub.ise.ec.messageHandlers.messages;

import de.tub.ise.ec.kv.KeyValueInterface;
import de.tub.ise.hermes.Request;
import de.tub.ise.hermes.Response;

import java.io.Serializable;
import java.util.List;

/**
 * Abstract class for a message. This class is part of Strategy design pattern used to stick closer to the SOLID principles.
 *
 * @author Jacek Janczura
 */
public abstract class Message {
    Request request;
    KeyValueInterface store;
    List<Serializable> items;
    Boolean isMaster;


    Message(Request request, KeyValueInterface store){
        this.request=request;
        this.store=store;
        items = request.getItems();
    }

    public abstract Response respond();
}
