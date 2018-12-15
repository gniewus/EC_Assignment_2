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
        //MasterServer master = new MasterServer();
        // SlaveServer slave = new SlaveServer();

        // client.syncSendToSlave(client.write("n", "0"));
        // client.syncSendToSlave(client.read("1"));
        // client.sendSyncMsgToSlave(client.write("n", "0","testt"));
        // client.sendSyncMsgToSlave(client.listKeys());
        //client.sendSyncMsgToSlave(client.delete("n","testt"));
//        client.sendSyncMsgToMaster(client.write("X", "valuex"));
//        client.sendSyncMsgToMaster(client.write("Y", "valuey"));
//        client.sendSyncMsgToMaster(client.listKeys());
//        client.sendSyncMsgToMaster(client.read("X"));
//        client.sendSyncMsgToMaster(client.delete("X"));
//        client.sendSyncMsgToMaster(client.listKeys());
//

        try {
            client.crazyUpdateAsynchronic();
            client.crazyUpdateSynchronic();
        } catch (Exception e) {
            log.error(e.getMessage());
        }


    }


}
