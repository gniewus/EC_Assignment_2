package de.tub.ise.ec.clients;

import de.tub.ise.hermes.Request;

import java.io.Serializable;

/**
 * Specifies generic interface of a CRUD client.
 * Each of the methods generates a Hermes Request to send.
 *
 * @author Jacek Janczura
 */
public interface ICrud {
    /**
     * Reads value under the key. Can be hadled by both Master and Slave
     * @param key
     * @return
     */
    public Object read(String key);

    /**
     * Th
     * @param key
     * @param value
     * @param id
     * @return
     */
    public Request write(String key, Serializable value,String id);

    /**
     * This message triggers asynchornous replication
     * @param key
     * @param value
     * @return
     */
    public Request asyncWrite(String key, Serializable value);

    /**
     * This message triggers synchronous replication.
     * @param key
     * @param value
     * @return
     */
    public Request syncWrite(String key, Serializable value);


    /**
     * This message deletes the file local on the server which reveived the message.
     * @param key
     * @param id Id of the transaction
     * @return
     */
    public Request delete(String key, String id);

    /**
     * This message updates locally a value under the given key.
     * @param key
     * @param value
     * @param id
     * @return
     */
    public Request update(String key, Serializable value,String id);


}
