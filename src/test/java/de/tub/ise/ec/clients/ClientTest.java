package de.tub.ise.ec.clients;

import de.tub.ise.ec.servers.MasterServer;
import de.tub.ise.ec.servers.SlaveServer;
import de.tub.ise.hermes.Response;
import org.junit.Assert;
import org.junit.Test;

public class ClientTest {
    private Client client = new Client();

    private SlaveServer slave = new SlaveServer(8000, "127.0.0.1");
    private MasterServer masterServer = new MasterServer(8001, "127.0.0.1");


    @Test
    public void generalReplicationTest() {
        client.sendSyncMsgToMaster(client.syncWrite("C", "valuex"));
        client.sendSyncMsgToMaster(client.read("C"));

        client.sendSyncMsgToMaster(client.asyncWrite("Q", "Werty"));
        client.sendSyncMsgToMaster(client.read("Q"));

        Response listKeysMaster = client.sendSyncMsgToMaster(client.listKeys());
        Response listKeysSlave = client.sendSyncMsgToSlave(client.listKeys());

        client.sendSyncMsgToMaster(client.asyncDelete("Q"));
        client.sendSyncMsgToMaster(client.syncDelete("Q"));

        Assert.assertTrue(listKeysSlave.responseCode());
        Assert.assertTrue(listKeysMaster.responseCode());
        Assert.assertEquals(listKeysMaster.getItems(), listKeysSlave.getItems());

    }

    @Test
    public void writeToSlave() {
        Response res = client.sendSyncMsgToSlave(client.write("test","test","testId"));
        Assert.assertTrue(res.responseCode());
    }

    /*
    @Test
    public void addValueTest(){
        Request request = new Request(Arrays.asList("addValue", 1, 1), "storageMessageHandler", "localSampleClient");
        Response response = client.getSenderMaster().sendMessage(request, 5000);
        Assert.assertTrue(response.responseCode());
    }

    @Test
    public void readValueTest(){
        client.write("1", "0","5");
        Request request = new Request(Arrays.asList("readValue", "1"), "storageMessageHandler", "localSampleClient");
        Response response = client.getSenderSlave().sendMessage(request, 5000);
        Assert.assertEquals("0", response.getItems().get(0));
        Assert.assertTrue(response.responseCode());
    }*/


}
