package de.tub.ise.ec.messagehandlers.factories;

import de.tub.ise.ec.kv.KeyValueInterface;
import de.tub.ise.ec.messagehandlers.messages.*;
import de.tub.ise.hermes.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * Class that fulfils Factory design pattern.
 * Class is used to pick the correct message constructor to fulfil Strategy design pattern requirement.
 *
 * @author Jacek Janczura
 */

public class MasterMessageFactory {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private MasterMessageFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static Message getMessageType(Request request, KeyValueInterface store) {
        switch ((String) request.getItems().get(0)) {
            case "listKeys":
                return createListKeys(request, store);
            case "readValue":
                return createReadValue(request, store);
            case "syncAddValue":
                return createSyncAddValue(request, store);
            case "asyncAddValue":
                return createAsyncAddValue(request, store);
            case "syncUpdateKey":
                return createSyncUpdateKey(request, store);
            case "asyncUpdateKey":
                return createAsyncUpdateKey(request, store);
            case "deleteKey":
                return createDeleteKey(request, store);
            case "syncDeleteKey":
                return createAsyncDeleteKey(request, store);
            case "asyncDeleteKey":
                return createSyncDeleteKey(request, store);
            case "addValue":
                return createAddValue(request, store);
            case "updateKey":
                return createAddValue(request, store); // Update is the same as add value
            default:
                throw new IllegalArgumentException();
        }
    }

    private static Message createAsyncDeleteKey(Request request, KeyValueInterface store) {
        return new AsyncDeleteKey(request, store);
    }

    private static Message createSyncDeleteKey(Request request, KeyValueInterface store) {
        return new SyncDeleteKey(request, store);
    }

    private static Message createAsyncAddValue(Request request, KeyValueInterface store) {
        return new AsyncAddValue(request, store);
    }

    private static Message createSyncAddValue(Request request, KeyValueInterface store) {
        return new SyncAddValue(request, store);
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

    private static Message createDeleteKey(Request request, KeyValueInterface store) {
        return new DeleteKey(request, store);
    }

    private static Message createSyncUpdateKey(Request request, KeyValueInterface store) {
        return new SyncDeleteKey(request, store);
    }

    private static Message createAsyncUpdateKey(Request request, KeyValueInterface store) {
        return new AsyncDeleteKey(request, store);
    }

}
