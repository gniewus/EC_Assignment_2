package de.tub.ise.ec.clients;

import de.tub.ise.ec.servers.MasterServer;
import de.tub.ise.ec.servers.SlaveServer;
import de.tub.ise.hermes.Response;
import de.tub.ise.hermes.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * Implementation of a CRUD client
 * Client can write only to MasterServer but read from Master and SlaveServer. (Master server extends slave server)
 *
 * @author Jacek Janczura
 */
public class Client implements ICrud {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    @Override
    public Object read(SlaveServer slaveServer) {
        return null;
    }

    @Override
    public void write(MasterServer masterServer) {

    }

    @Override
    public void delete() {

    }

    @Override
    public void update() {

    }
}
