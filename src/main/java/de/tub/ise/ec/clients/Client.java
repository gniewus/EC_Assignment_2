package de.tub.ise.ec.clients;

import de.tub.ise.hermes.Request;
import de.tub.ise.hermes.AsyncCallbackRecipient;
import de.tub.ise.hermes.Response;
import de.tub.ise.hermes.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.UUID;

/**
 * Implementation of a CRUD client
 * Client can write only to MasterServer but read from Master and SlaveServer. (Master server extends slave server)
 *
 * @author Jacek Janczura
 */
public class Client implements ICrud {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private int portSlave;
    private int portMaster;

    private String hostSlave;
    private String hostMaster;
    private Sender senderMaster;
    private Sender senderSlave;
    private String masterHandler = "storageMessageHandler";
    private String slaveHandler = "storageMessageHandler";
    /**
     * Default constructor with hardcoded test values.
     */
    public Client() {
        this.portSlave = 8000;
        this.hostSlave = "127.0.0.1";

        this.portMaster = 8001;
        this.hostMaster = "127.0.0.1";

        senderSlave = new Sender(this.hostSlave, this.portSlave);
        senderMaster = new Sender(this.hostMaster, this.portMaster);
        //log.info("Default client with two senders on ports " + portMaster + "," + portSlave + " and host " + hostMaster + "," + hostSlave + " created. ");
    }

    /**
     * Constructor can take two parameters to start master sender.
     * @param port
     * @param host
     */
    public Client(int port, String host) {
        this.portMaster = port;
        this.hostMaster = host;

        senderMaster = new Sender(hostMaster, portMaster);
        log.info("Client on a port " + port + " and host " + host + " created. ");
    }
    /**
     * Constructor can take four parameters to start and parametrise both master and slave sender.
     * @param portSlave
     * @param hostSlave
     * @param portMaster
     * @param hostMaster
     */
    public Client(int portSlave, String hostSlave, int portMaster, String hostMaster) {
        this.portSlave = portSlave;
        this.hostSlave = hostSlave;
        this.portMaster = portMaster;
        this.hostMaster = hostMaster;

        senderMaster = new Sender(this.hostSlave, this.portSlave);
        senderSlave = new Sender(this.hostMaster, this.portMaster);
        log.info("Client with two senders on ports " + portMaster + "," + portSlave + " and host " + hostMaster + "," + hostSlave + " created. ");
    }

    public Sender getSenderMaster() {
        return senderMaster;
    }

    public Sender getSenderSlave() {
        return senderSlave;
    }

    public int getPortSlave() {
        return portSlave;
    }

    public void setPortSlave(int portSlave) {
        this.portSlave = portSlave;
    }

    public int getPortMaster() {
        return portMaster;
    }

    public void setPortMaster(int portMaster) {
        this.portMaster = portMaster;
    }

    public String getHostSlave() {
        return hostSlave;
    }

    public void setHostSlave(String hostSlave) {
        this.hostSlave = hostSlave;
    }

    public String getHostMaster() {
        return hostMaster;
    }

    public void setHostMaster(String hostMaster) {
        this.hostMaster = hostMaster;
    }

    public String getMasterHandler() {
        return masterHandler;
    }

    public void setMasterHandler(String masterHandler) {
        this.masterHandler = masterHandler;
    }

    public String getSlaveHandler() {
        return slaveHandler;
    }

    public void setSlaveHandler(String slaveHandler) {
        this.slaveHandler = slaveHandler;
    }

    private void crazyUpdate(String key, String value) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            sleep(50);
            update(key, value);
        }
    }

    private void sleep(int timeperiod) {
        try {
            Thread.sleep(timeperiod);
        } catch (InterruptedException ex) {
            log.error("1 s sleep interupted ", ex);
            Thread.currentThread().interrupt();
        }
    }

    public Request listKeys() {
        return new Request("listKeys", "storageMessageHandler", "localSampleClient");
    }

    public String generateId(String key){
        String uniqueID = UUID.randomUUID().toString();
        uniqueID= uniqueID.substring(0, uniqueID.length() - 28);
        String id = key+"-"+uniqueID;
        return id;
    }

    public Request read(String key) {

        return new Request(Arrays.asList("readValue", key,generateId(key)), "storageMessageHandler", "localSampleClient");
        //Response response = sendRequest(request);
        //logResponse(response);
        //return response.getItems().get(0);
    }

    @Override
    public Request write(String key, Serializable value, String id) {
        if(id.isEmpty()){
            return new Request(Arrays.asList("addValue", key, value,generateId(key)), "storageMessageHandler", "localSampleClient");
        }else
        {return new Request(Arrays.asList("addValue", key, value,id), "storageMessageHandler", "localSampleClient");}

    }

    @Override
    public Request asyncWrite(String key, Serializable value) {
        return new Request(Arrays.asList("asyncAddValue", key, value,generateId(key)), "storageMessageHandler", "localSampleClient");
    }

    @Override
    public Request syncWrite(String key, Serializable value) {
        return new Request(Arrays.asList("syncAddValue", key, value,generateId(key)), "storageMessageHandler", "localSampleClient");
    }

    @Override
    public Request delete(String key,String id) {
        if(id.isEmpty()){
            return new Request(Arrays.asList("deleteKey", key,id), "storageMessageHandler", "localSampleClient");
        }else {
            return new Request(Arrays.asList("deleteKey", key,generateId(key)), "storageMessageHandler", "localSampleClient");
        }
    }

    public Request syncDelete(String key) {
        return new Request(Arrays.asList("syncDeleteKey", key,generateId(key)), "storageMessageHandler", "localSampleClient");
    }

    public Request asyncDelete(String key) {
        return new Request(Arrays.asList("asyncDeleteKey", key,generateId(key)), "storageMessageHandler", "localSampleClient");
    }

    @Override
    public Request update(String key, Serializable value) {
        //TODO Trzeba jescze update synchronicze i asynchronicznie zaimplementowac, prawda ?
        return new Request(Arrays.asList("updateKey", key, value,generateId(key)), "storageMessageHandler", "localSampleClient");
    }

    public Response sendSyncMsgToMaster(Request request) {
        log.debug("Sent request: {} to master ", request.getItems().toString());
        Response response = senderMaster.sendMessage(request, 5000);
        return response;
    }

    public Response sendSyncMsgToSlave(Request request) {
        log.debug("Sent sync request: {} to slave", request.getItems().get(0));
        Response response = senderSlave.sendMessage(request, 5000);
        logResponse(response);
        return response;
    }
//Not needed
//    public Boolean sendAsyncMsgToMaster(Request request) {
//        log.debug("Sent async request: {} to master", request.getItems().get(0));
//        asyncCallback AsyncCallback = new asyncCallback();
//        Boolean messageSent = senderMaster.sendMessageAsync(request, AsyncCallback);
//        callbackFunction(AsyncCallback);
//        return messageSent;
//    }

    public void sendAsyncMsgToSlave(Request request) {

        asyncCallback AsyncCallback = new asyncCallback();
        Boolean messsageSent = senderSlave.sendMessageAsync(request, AsyncCallback);
        log.debug("Send async request: {} to slave: " + messsageSent, request.getItems().get(0));
        callbackFunction(AsyncCallback);
    }

    private Response callbackFunction(asyncCallback callback) {
        while (true) {
            sleep(50);
            if (callback.getResponse() != null) break;
        }
        Response response = callback.getResponse();
        return response;
    }

    private void logResponse(Response response) {
        if (response.getItems().isEmpty()) {
            log.info("Response: " + response.getResponseMessage());
        } else {
            log.info("Response: " + response.getResponseMessage() + " " + response.getItems());
        }
    }


    private class asyncCallback implements AsyncCallbackRecipient {

        public Response getResponse() {
            return response;
        }

        public void setResponse(Response response) {
            this.response = response;
        }

        private Response response;

        @Override
        public void callback(Response resp) {
            log.info("Callback finished working!");
            logResponse(resp);
            setResponse(resp);

        }
    }


}


