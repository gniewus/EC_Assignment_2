package de.tub.ise.test;

import de.tub.ise.ec.clients.Client;
import de.tub.ise.ec.servers.SlaveServer;
import de.tub.ise.hermes.Request;
import de.tub.ise.hermes.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class ClientTest {
    Client client = new Client();

    //MasterServer master = new MasterServer();
    SlaveServer slave = new SlaveServer();

    @Test
    public void generalOperationsTest(){
        client.write("1", 0);
        client.listKeys();
        client.write("2", "1");
        client.read("1");
        client.read("2");
        client.listKeys();
        client.delete("1");
        client.listKeys();
        client.write("1", 0);
        client.listKeys();
    }

    @Test
    public void addValueTest(){
        Request request = new Request(Arrays.asList("addValue", 1, 1), "storageMessageHandler", "localSampleClient");
        Response response = client.getSender().sendMessage(request, 5000);
        Assert.assertTrue(response.responseCode());
    }

    @Test
    public void readValueTest(){
        client.write("1", "0");
        Request request = new Request(Arrays.asList("readValue", "1"), "storageMessageHandler", "localSampleClient");
        Response response = client.getSender().sendMessage(request, 5000);
        Assert.assertEquals("0", response.getItems().get(0));
        Assert.assertTrue(response.responseCode());
    }



}
