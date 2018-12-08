package de.tub.ise.ec.clients;

import de.tub.ise.hermes.Request;
import de.tub.ise.hermes.Response;
import de.tub.ise.hermes.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.util.Arrays;

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
    private String masterHandler ="storageMessageHandler";
    private String slaveHandler = "storageMessageHandler";

    public Client() {
        this.portSlave = 8000;
        this.hostSlave = "127.0.0.1";

        this.portMaster = 8001;
        this.hostMaster = "127.0.0.1";

        senderMaster = new Sender(this.hostSlave, this.portSlave);
        senderSlave = new Sender(this.hostMaster, this.portMaster);
        log.info("Default client with two senders on ports "+ portMaster+","+portSlave + " and host " + hostMaster+","+hostSlave + " created. ");
    }

    public Client(int port, String host) {
        this.portMaster = port;
        this.hostMaster = host;

        senderMaster = new Sender(hostMaster, portMaster);
        log.info("Client on a port "+ port + " and host " + host + " created. ");
    }

    public Client(int portSlave, String hostSlave, int portMaster, String hostMaster) {
        this.portSlave = portSlave;
        this.hostSlave = hostSlave;
        this.portMaster = portMaster;
        this.hostMaster = hostMaster;

        senderMaster = new Sender(this.hostSlave, this.portSlave);
        senderSlave = new Sender(this.hostMaster, this.portMaster);
        log.info("Client with two senders on ports "+ portMaster+","+portSlave + " and host " + hostMaster+","+hostSlave + " created. ");
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
        for(int i = 0; i<100; i++){
            sleep();
            update(key, value);
        }
    }

    private void sleep(){
        try
        {
            Thread.sleep(1000);
        }
        catch(InterruptedException ex)
        {
            log.error("1 s sleep interupted ", ex);
            Thread.currentThread().interrupt();
        }
    }

    public Request listKeys(){
        return new Request("listKeys", "storageMessageHandler", "localSampleClient");
    }

    public Request read(String key){

        return new Request(Arrays.asList("readValue", key), "storageMessageHandler", "localSampleClient");
        //Response response = sendRequest(request);
        //logResponse(response);
        //return response.getItems().get(0);
    }

    @Override
    public Request write(String key, Serializable value) {
        return new Request(Arrays.asList("addValue", key, value), "storageMessageHandler", "localSampleClient");
    }

    @Override
    public Request delete(String key) {
        return new Request(Arrays.asList("deleteKey", key), "storageMessageHandler", "localSampleClient");
    }

    @Override
    public Request update(String key, Serializable value) {
        return new Request(Arrays.asList("updateKey", key, value), "storageMessageHandler", "localSampleClient");
    }

    public void sendRequest(Request request, boolean toMaster){
        if(toMaster){
            log.debug("Send request: {} to master", request.getItems().get(0));
            Response response = senderMaster.sendMessage(request, 5000);
            logResponse(response);
        }
        else {
            log.debug("Send request: {} to slave", request.getItems().get(0));
            Response response =  senderSlave.sendMessage(request, 5000);
            logResponse(response);
        }
    }

    private void logResponse(Response response){
        if(response.getItems().isEmpty()){
            log.info("Response: " + response.getResponseMessage());
        } else {
            log.info("Response: " + response.getResponseMessage() + " " + response.getItems());
        }
    }
}
