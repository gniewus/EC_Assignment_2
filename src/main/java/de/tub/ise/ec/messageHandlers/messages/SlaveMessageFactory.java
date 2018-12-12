package de.tub.ise.ec.messageHandlers.messages;

import de.tub.ise.ec.kv.KeyValueInterface;
import de.tub.ise.hermes.Request;

public class SlaveMessageFactory {
    public static Message getMessageType(Request request, KeyValueInterface store) {
        switch ((String) request.getItems().get(0)) {
            case "listKeys":
                return createListKeys(request, store);
            case "readValue":
                return createReadValue(request, store);
            case "addValue":
                return createAddValue(request, store);
            case "deleteKey":
                return createDeleteKey(request, store);
        }
        throw new IllegalArgumentException();
    }

    private static Message createDeleteKey(Request request, KeyValueInterface store) {
        return new DeleteKey(request, store);
    }

    private static Message createListKeys(Request request, KeyValueInterface store) {
        return new ListKeys(request, store);
    }

    private static Message createReadValue(Request request, KeyValueInterface store) {
        return new ReadValue(request, store);
    }
    private static Message createAddValue(Request request, KeyValueInterface store) {
        return new AddValue(request, store);
    }
}
