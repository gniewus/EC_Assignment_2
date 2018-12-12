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
//        client.sendSyncMsgToMaster(client.write("X", "valuex"));
//        client.sendSyncMsgToMaster(client.write("Y", "valuey"));
//        client.sendSyncMsgToMaster(client.listKeys());
//        client.sendSyncMsgToMaster(client.read("X"));
//        client.sendSyncMsgToMaster(client.delete("X"));
//        client.sendSyncMsgToMaster(client.listKeys());

        client.sendSyncMsgToMaster(client.syncWrite("C", "valuex"));
        client.sendSyncMsgToMaster(client.read("C"));

        client.sendSyncMsgToMaster(client.asyncWrite("Q", "Werty"));
        client.sendSyncMsgToMaster(client.read("Q"));

        client.sendSyncMsgToMaster(client.listKeys());
        client.syncSendToSlave(client.listKeys());

        client.sendSyncMsgToMaster(client.asyncDelete("Q"));
        client.sendSyncMsgToMaster(client.syncDelete("Q"));

    }


}
