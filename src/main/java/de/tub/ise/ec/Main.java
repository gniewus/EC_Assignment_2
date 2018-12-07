package de.tub.ise.ec;

import de.tub.ise.ec.kv.FileSystemKVStore;
import de.tub.ise.ec.kv.KeyValueInterface;
import de.tub.ise.hermes.*;
import  de.tub.ise.ec.servers.*;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	public static void main(String[] args) {

		// HERMES TEST
		int port = 8000;
		String host = "127.0.0.1"; // localhost

		//MasterServer master = new MasterServer();
		SlaveServer slave = new SlaveServer();

		// Let's agree that the first item in the array will be the actual CRUD command
		Request req = new Request("listKeys", "storageMessageHandler", "localSampleClient");


		Sender sender = new Sender(host, port);
		Response res = sender.sendMessage(req, 5000);
		log.info("Response: " + res.getResponseMessage()+ res.getItems());


/*
		// Server: register handler
		RequestHandlerRegistry reg = RequestHandlerRegistry.getInstance();
		reg.registerHandler("sampleMessageHandler", new SampleMessageHandler());

		// Server: start receiver
		try {
			Receiver receiver = new Receiver(port);
			receiver.start();
		} catch (IOException e) {
			System.out.println("Connection error: " + e);
		}

		// Client: create request
		Request req = new Request("Message", "sampleMessageHandler", "localSampleClient");

		// Client: send messages
		Sender sender = new Sender(host, port);
		Response res = sender.sendMessage(req, 5000);
		System.out.println("Received: " + res.getResponseMessage());

		 // Test kv store
		 KeyValueInterface store = new FileSystemKVStore();
		 store.store("monkey","banana");
		 System.out.println("Received: " + store.getValue("monkey"));
		 store.delete("monkey");*/
	}
}
