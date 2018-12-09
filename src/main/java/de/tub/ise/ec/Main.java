package de.tub.ise.ec;

import de.tub.ise.ec.clients.Client;
import de.tub.ise.ec.servers.MasterServer;
import de.tub.ise.ec.servers.SlaveServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {

        Client client = new Client();
        MasterServer master = new MasterServer(8001, "127.0.0.1");
        SlaveServer slave = new SlaveServer(8000, "127.0.0.1");

       // client.syncSendToSlave(client.write("n", "0"));
       // client.syncSendToSlave(client.read("1"));
//       // client.syncSendToSlave(client.listKeys());
//        client.syncSendToMaster(client.write("X", "valuex"));
//        client.syncSendToMaster(client.write("Y", "valuey"));
//        client.syncSendToMaster(client.listKeys());
//        client.syncSendToMaster(client.read("X"));
//        client.syncSendToMaster(client.delete("X"));
//        client.syncSendToMaster(client.listKeys());

        client.syncSendToMaster(client.syncWrite("C", "valuex"));

        client.syncSendToMaster(client.listKeys());
        client.syncSendToSlave(client.listKeys());


    }


}
