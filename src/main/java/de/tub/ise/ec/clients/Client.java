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

    private int port;
    private String host;
    private Sender sender;

    public Client() {
        port = 8001;
        host = "127.0.0.1"; // localhost
        sender = new Sender(host, port);
        log.info("Default client on a port "+ port + " and host " + host + " created. ");
    }

    public Client(int port, String host) {
        this.port = port;
        this.host = host;
        sender = new Sender(host, port);
        log.info("Client on a port "+ port + " and host " + host + " created. ");
    }


    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void createSender(String host, int port){
        sender = new Sender(host, port);
    }

    public Sender getSender() {
        return sender;
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

    public void listKeys(){
        Request request = new Request("listKeys", "storageMessageHandler", "localSampleClient");
        logResponse(sendRequest(request));
    }

    public Serializable read(String key){
        Request request = new Request(Arrays.asList("readValue", key), "storageMessageHandler", "localSampleClient");
        Response response = sendRequest(request);
        logResponse(response);
        return response.getItems().get(0);
    }

    @Override
    public void write(String key, Serializable value) {
        Request request = new Request(Arrays.asList("addValue", key, value), "storageMessageHandler", "localSampleClient");
        logResponse(sendRequest(request));
    }

    @Override
    public void delete(String key) {
        Request request = new Request(Arrays.asList("deleteKey", key), "storageMessageHandler", "localSampleClient");
        logResponse(sendRequest(request));
    }

    @Override
    public void update(String key, Serializable value) {
        Request request = new Request(Arrays.asList("updateKey", key, value), "storageMessageHandler", "localSampleClient");
        logResponse(sendRequest(request));
    }

    private Response sendRequest(Request request){
        return sender.sendMessage(request, 5000);
    }

    private void logResponse(Response response){
        if(response.getItems().isEmpty()){
            log.info("Response: " + response.getResponseMessage());
        }
        else {
            log.info("Response: " + response.getResponseMessage() + " " + response.getItems());
        }
    }




}
