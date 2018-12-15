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
     *
     * @param key
     * @return
     */
    Object read(String key);

    /**
     * Method to write the value
     *
     * @param key   Key
     * @param value Value which will be written in for a key
     * @return ready request to send to the server
     */
    Request write(String key, Serializable value);

    /**
     * This method triggers asynchronous replication on a write request. All replicas will be asynchronously replicated.
     *
     * @param key   Key in a KV store
     * @param value Value which will be written in for a key
     * @return ready request to send to the server
     */
    Request asyncWrite(String key, Serializable value);

    /**
     * This method triggers synchronous replication on a write request. All replicas will be synchronously replicated.
     *
     * @param key   Key in a KV store
     * @param value Value which will be written in for a key
     * @return ready request to send to the server
     */
    Request syncWrite(String key, Serializable value);


    /**
     * This message deletes the file local on the server which reveived the message.
     *
     * @param key
     * @return
     */
    Request delete(String key, String id);

    /**
     * This message updates locally a value under the given key.
     *
     * @param key
     * @param value
     * @return
     */
    Request update(String key, Serializable value);


}
