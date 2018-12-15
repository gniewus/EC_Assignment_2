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
        MasterServer master = new MasterServer();
        SlaveServer slave = new SlaveServer();

        client.sendMsgToSlave(client.write("n", "0","testt"));
        client.sendMsgToSlave(client.listKeys());
        client.sendMsgToSlave(client.delete("n","testt"));
        client.sendMsgToMaster(client.syncWrite("X", "valuex"));
        client.sendMsgToMaster(client.asyncWrite("Y", "valuey"));
        client.sendMsgToMaster(client.listKeys());

    }


}
