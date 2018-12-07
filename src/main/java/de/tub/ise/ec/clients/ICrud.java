package de.tub.ise.ec.clients;

import java.io.Serializable;

/**
 * Specifies generic interface of a CRUD client
 *
 * @author Jacek Janczura
 */
public interface ICrud {

    public Object read(String key);

    public void write(String key, Serializable value);

    public void update(String key, Serializable value);

    public void delete(String key);



}
