package de.tub.ise.ec;

import de.tub.ise.ec.clients.Client;
import de.tub.ise.ec.servers.MasterServer;
import de.tub.ise.ec.servers.SlaveServer;
import de.tub.ise.hermes.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.lang.invoke.MethodHandles;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {

        Client client = new Client();
        MasterServer master = new MasterServer(8001, "127.0.0.1");
        SlaveServer slave = new SlaveServer(8000, "127.0.0.1");

        sendSlave(client, client.write("1", "0"));
        sendSlave(client, client.read("1"));
        sendSlave(client, client.listKeys());

        sendMaster(client, client.write("X", "Y"));
        sendMaster(client, client.read("X"));
        sendMaster(client, client.listKeys());


    }

    public static void sendSlave(Client client, Request req) {
        client.sendRequest(req, false);
    }

    public static void sendMaster(Client client, Request req) {
        client.sendRequest(req, true);
    }
}
