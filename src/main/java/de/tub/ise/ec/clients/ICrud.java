package de.tub.ise.ec.clients;

import de.tub.ise.ec.servers.MasterServer;
import de.tub.ise.ec.servers.SlaveServer;

/**
 * Specifies generic interface of a CRUD client
 *
 * @author Jacek Janczura
 */
public interface ICrud {

    public Object read(SlaveServer slaveServer);

    public void write(MasterServer masterServer);

    public void update();

    public void delete();



}
