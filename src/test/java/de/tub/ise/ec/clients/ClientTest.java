package de.tub.ise.ec.clients;

import de.tub.ise.ec.servers.MasterServer;

public class ClientTest {
    private Client client = new Client();

    //MasterServer master = new MasterServer();
    private MasterServer masterServer = new MasterServer();

/*    @Before
    public void setUp(){
        if(client == null){
            client = new Client();
        }
        if(slaveServer == null) {
            slaveServer = new SlaveServer();
        }
    }*/

/*    @After
    public void cleanUp(){
        slaveServer.terminate();
    }*/
/*
    @Test
    public void generalOperationsTest(){
        //:TODO Refactor test
        client.write("1", 0,"1");
        client.listKeys();
        client.write("2", "1","2");
        client.read("1");
        client.read("2");
        client.listKeys();
        client.delete("1","3");
        client.listKeys();
        client.write("1", 4,"4");
        client.listKeys();

    }

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
