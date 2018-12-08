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

        client.sendToSlave(client.write("1", "0"));
        client.sendToSlave(client.read("1"));
        client.sendToSlave(client.listKeys());
        client.sendToMaster(client.write("X", "Y"));
        client.sendToMaster(client.read("X"));
        client.sendToMaster(client.listKeys());

    }


}
