package de.tub.ise.ec.clients;

import de.tub.ise.hermes.AsyncCallbackRecipient;
import de.tub.ise.hermes.Request;
import de.tub.ise.hermes.Response;
import de.tub.ise.hermes.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * Implementation of a CRUD client.<br>
 * Client can send messages to servers. Master and slave server. To send the message client.sendMsgToSlave(client.listKeys());
 * <br>
 * Client
 *
 * @author Jacek Janczura
 */
public class Client implements ICrud {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String LOCAL_SAMPLE_CLIENT = "localSampleClient";

    private int portSlave;
    private int portMaster;

    private String hostSlave;
    private String hostMaster;
    private Sender senderMaster;
    private Sender senderSlave;
    private static final String STORAGE_MESSAGE_HANDLER = "storageMessageHandler";
    private static final String LOCAL_HOST = "127.0.0.1";
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("config");

    /**
     * Default constructor which creates the Slave and Master server on a hosts and ports read form config.properties file.
     */
    public Client() {
        this.portSlave = Integer.valueOf(RESOURCE_BUNDLE.getString("portSlave"));
        this.hostSlave = RESOURCE_BUNDLE.getString("hostSlave");

        this.portMaster = Integer.valueOf(RESOURCE_BUNDLE.getString("portMaster"));
        this.hostMaster = RESOURCE_BUNDLE.getString("hostMaster");

        senderSlave = new Sender(this.hostSlave, this.portSlave);
        senderMaster = new Sender(this.hostMaster, this.portMaster);
        log.debug("Default client with two senders on ports {},{} and host {},{} created. ", portMaster, portSlave, hostMaster, hostSlave);
    }

    /**
     * Constructor can take two parameters to start master sender.
     * Slave server can either work on localhost or not exists.
     *
     * @param port Master server port
     * @param host Master server host
     */
    public Client(int port, String host) {
        this.portMaster = port;
        this.hostMaster = host;
        this.portSlave = 8000;
        this.hostSlave = LOCAL_HOST;

        senderMaster = new Sender(hostMaster, portMaster);
        senderSlave = new Sender(this.hostSlave, this.portSlave);

        log.info("Client on a port {} and host {} created. ", port, host);
    }

    /**
     * Constructor creates the client. It can take four parameters to parametrise both master and slave sender.
     *
     * @param portSlave  Slave server port
     * @param hostSlave  Slave server host
     * @param portMaster Master server port
     * @param hostMaster Master server host
     */
    public Client(int portSlave, String hostSlave, int portMaster, String hostMaster) {
        this.portSlave = portSlave;
        this.hostSlave = hostSlave;
        this.portMaster = portMaster;
        this.hostMaster = hostMaster;

        senderMaster = new Sender(this.hostSlave, this.portSlave);
        senderSlave = new Sender(this.hostMaster, this.portMaster);
        log.info("Client with two senders on ports  {} ,{} and host {}, {} created. ", portMaster, portSlave, hostMaster, hostSlave);
    }


    /**
     * Method used for benchmarking latency and staleness of asynchronic replication.
     * This method sends an update (In our KV store write is equal to an update) every second for 100s using asynchron. replication.
     */
    public void crazyUpdateAsynchronic() {
        for (int i = 0; i < 100; i++) {
            Request req = asyncWrite("Asy", "req" + i);
            sendMsgToMaster(req);
            sleep(1000);
        }
    }
    /**
     * Method used for benchmarking latency and staleness of synchronic replication.
     * This method sends an update (In our KV store write is equal to an update) every second for 100s using synchron. replication.
     */
    public void crazyUpdateSynchronic() {
        for (int i = 0; i < 100; i++) {
            Request req = syncWrite("Sy", "req" + i);
            sendMsgToMaster(req);
            sleep(1000);
        }
    }

    /**
     * This method safely "stops" the main thread for @timeperiod milliseconds.
     *
     * @param timeperiod umber of miliseconds to sleep
     */
    private void sleep(int timeperiod) {
        try {
            Thread.sleep(timeperiod);
        } catch (InterruptedException ex) {
            log.error("sleep interupted ", ex);
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Methode used to generate unique transaction ID. UUID.randomUUID() is used which is the correct approach in java to generate unique ID.
     *
     * @param key key from the KV store to generate ID for
     * @return unique transaction ID generated for the key from KV store
     */
    public String generateId(String key) {
        String uniqueID = UUID.randomUUID().toString();
        uniqueID = uniqueID.substring(0, uniqueID.length() - 28);
        return key + "-" + uniqueID;
    }

    /**
     * Method builds the request to list all the keys in a keystore.
     *
     * @return ready request to send to the server
     */
    public Request listKeys() {
        return new Request("listKeys", STORAGE_MESSAGE_HANDLER, LOCAL_SAMPLE_CLIENT);
    }

    /**
     * Method builds the request - read the value for a key.
     *
     * @param key key to read the value
     * @return ready request to send to the server
     */
    public Request read(String key) {
        return new Request(Arrays.asList("readValue", key, generateId(key)), STORAGE_MESSAGE_HANDLER, LOCAL_SAMPLE_CLIENT);
    }

    /**
     * Method to write the value in a KV store with a custom transaction ID.
     *
     * @param key   Key in a KV store
     * @param value Value which will be written in for a key
     * @param id    Custom transaction ID
     * @return
     */
    public Request write(String key, Serializable value, String id) {
        return new Request(Arrays.asList("addValue", key, value, id), STORAGE_MESSAGE_HANDLER, LOCAL_SAMPLE_CLIENT);
    }

    /**
     * Method to write the value in a KV store.
     *
     * @param key   Key in a KV store
     * @param value Value which will be written in for a key
     * @return ready request to send to the server
     */
    @Override
    public Request write(String key, Serializable value) {
        return new Request(Arrays.asList("addValue", key, value, generateId(key)), STORAGE_MESSAGE_HANDLER, LOCAL_SAMPLE_CLIENT);
    }

    /**
     * This method triggers asynchronous replication on a write request. All replicas will be asynchronously replicated.
     *
     * @param key   Key in a KV store
     * @param value Value which will be written in for a key
     * @return ready request to send to the server
     */
    @Override
    public Request asyncWrite(String key, Serializable value) {
        return new Request(Arrays.asList("asyncAddValue", key, value, generateId(key)), STORAGE_MESSAGE_HANDLER, LOCAL_SAMPLE_CLIENT);
    }

    /**
     * This method triggers synchronous replication on a write request. All replicas will be synchronously replicated.
     *
     * @param key   Key in a KV store
     * @param value Value which will be written in for a key
     * @return ready request to send to the server
     */
    @Override
    public Request syncWrite(String key, Serializable value) {
        return new Request(Arrays.asList("syncAddValue", key, value, generateId(key)), STORAGE_MESSAGE_HANDLER, LOCAL_SAMPLE_CLIENT);
    }

    /**
     * @param key
     * @param id  Id of the transaction
     * @return ready request to send to the server
     */
    @Override
    public Request delete(String key, String id) {
        if (id.isEmpty()) {
            return new Request(Arrays.asList("deleteKey", key, id), STORAGE_MESSAGE_HANDLER, LOCAL_SAMPLE_CLIENT);
        } else {
            return new Request(Arrays.asList("deleteKey", key, generateId(key)), STORAGE_MESSAGE_HANDLER, LOCAL_SAMPLE_CLIENT);
        }
    }


    public Request syncDelete(String key) {
        return new Request(Arrays.asList("syncDeleteKey", key, generateId(key)), STORAGE_MESSAGE_HANDLER, LOCAL_SAMPLE_CLIENT);
    }

    public Request asyncDelete(String key) {
        return new Request(Arrays.asList("asyncDeleteKey", key, generateId(key)), STORAGE_MESSAGE_HANDLER, LOCAL_SAMPLE_CLIENT);
    }

    public Request syncUpdate(String key, String value) {
        return new Request(Arrays.asList("syncUpdateKey", key, value, generateId(key)), STORAGE_MESSAGE_HANDLER, LOCAL_SAMPLE_CLIENT);
    }

    public Request asyncUpdate(String key, String value) {
        return new Request(Arrays.asList("asyncUpdateKey", key, value, generateId(key)), STORAGE_MESSAGE_HANDLER, LOCAL_SAMPLE_CLIENT);
    }

    @Override
    public Request update(String key, Serializable value) {
        return new Request(Arrays.asList("updateKey", key, value, generateId(key)), STORAGE_MESSAGE_HANDLER, LOCAL_SAMPLE_CLIENT);
    }

    public Request update(String key, Serializable value, String id) {
        return new Request(Arrays.asList("updateKey", key, value, id), STORAGE_MESSAGE_HANDLER, LOCAL_SAMPLE_CLIENT);
    }


    /**
     * Method which sends the request to master server
     *
     * @param request request to be sent to master
     * @return response from the master server
     */
    public Response sendMsgToMaster(Request request) {
        String req = request.getItems().toString();
        log.debug("Sent request: {} to master ", req);
        Response response = senderMaster.sendMessage(request, 5000);
        logResponse(response);
        return response;
    }

    /**
     * Method which sends the request to slave server
     *
     * @param request request to be sent to slave
     * @return response from the master server
     */
    public Response sendMsgToSlave(Request request) {
        log.debug("Sent sync request: {} to slave", request.getItems().get(0));
        Response response = senderSlave.sendMessage(request, 5000);
        logResponse(response);
        return response;
    }

    /**
     * Method which sends the request to slave server asynchronously.
     *
     * @param request request to be sent to the slave
     *
     */
    public void sendAsyncMsgToSlave(Request request) {

        AsyncCallback asyncCallback = new AsyncCallback();
        Boolean messsageSent = senderSlave.sendMessageAsync(request, asyncCallback);
        log.debug("Send async request: {} to slave: {}", request.getItems().get(0), messsageSent);
        callbackFunction(asyncCallback);
    }

    /**
     * Callback function used in an asynchronous replication. Function waits for the response and returns it.
     *
     * @param callback Asynch callback to observe and to wait for the rsponse message.
     * @return response from the server
     */
    private Response callbackFunction(AsyncCallback callback) {
        while (true) {
            sleep(50);
            if (callback.getResponse() != null) break;
        }
        return callback.getResponse();
    }

    /**
     * Method to log the response message
     *
     * @param response response from the server
     */
    private void logResponse(Response response) {
        if (response.getItems().isEmpty()) {
            log.debug("Response: {}", response.getResponseMessage());
        } else {
            log.debug("Response: {} {}", response.getResponseMessage(), response.getItems());
        }
    }


    /**
     * class that needs to be implemented to set asynchronous messages
     */
    private class AsyncCallback implements AsyncCallbackRecipient {

        public Response getResponse() {
            return response;
        }

        public void setResponse(Response response) {
            this.response = response;
        }

        private Response response;

        @Override
        public void callback(Response resp) {
            log.debug("Callback finished working!");
            logResponse(resp);
            setResponse(resp);

        }
    }


}


