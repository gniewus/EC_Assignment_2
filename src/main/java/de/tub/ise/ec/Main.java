package de.tub.ise.ec;

import de.tub.ise.ec.clients.Client;
import de.tub.ise.ec.servers.MasterServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class Main {
	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	public static void main(String[] args) {

		Client client = new Client();

		MasterServer master = new MasterServer();
		//SlaveServer slave = new SlaveServer();

		client.write("1", "0");
		client.listKeys();
		client.write("2", "1");
		client.read("1");
		client.read("2");
		client.listKeys();

	}
}
