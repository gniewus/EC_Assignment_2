package de.tub.ise.ec.clients;

import de.tub.ise.hermes.Request;

import java.io.Serializable;

/**
 * Specifies generic interface of a CRUD client
 *
 * @author Jacek Janczura
 */
public interface ICrud {

    public Object read(String key);

    public Request write(String key, Serializable value);

    public Request asyncWrite(String key, Serializable value);

    public Request syncWrite(String key, Serializable value);

    public Request update(String key, Serializable value);

    public Request delete(String key);



}
