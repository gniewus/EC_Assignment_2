package de.tub.ise.ec.kv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

public class FileSystemKVStore implements KeyValueInterface {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private String rootDir;

    public FileSystemKVStore() {
        this("." + File.separator + "kv_store");
    }

    public FileSystemKVStore(String rootDir) {
        this.rootDir = rootDir;
        ;
    }

    /**
     * returns a value for a given key
     *
     * @param key
     * @return
     */
    @Override
    public Object getValue(String key) {
        File f = new File(rootDir + File.separator + key);
        Object value = null;
        try {
            FileInputStream fi = new FileInputStream(f);
            ObjectInputStream oi = new ObjectInputStream(fi);
            value = oi.readObject();
            oi.close();
        } catch (IOException | ClassNotFoundException e) {
            log.error("Retrieving value for key {} failed.", key, e);
        }
        return value;
    }

    /**
     * returns a list of all keys
     */
    @Override
    public List<String> getKeys() {
        List<String> result = new ArrayList<>();

        File dir = new File(rootDir);
        File[] files = dir.listFiles();
        try {
            for (File f : files) {
                result.add(f.getName());
            }

        } catch (NullPointerException err) {
            log.error("Empty or non existing storage folder", err);
            return result;

        }
        return result;
    }

    /**
     * stores a key value pair on the file system, whereby the key maps to the file
     * name and the value to the file's content
     *
     * @param key
     * @param value
     */
    @Override
    public void store(String key, Serializable value) {
        File f = new File(rootDir + File.separator + key);
        if (!f.isDirectory()) {
            if (!f.isFile()) {
                try {
                    File parent = f.getParentFile();
                    parent.mkdirs(); // create parent directories
                    f.createNewFile();
                } catch (IOException e) {
                    log.error("File {} could not be created. ", f.getAbsolutePath(), e);
                }
                // update file content
                try {
                    FileOutputStream fo = new FileOutputStream(f);
                    ObjectOutputStream oo = new ObjectOutputStream(fo);
                    oo.writeObject(value);
                    oo.close();
                } catch (IOException e) {
                    log.error("Writing value to file failed for key {} .", key, e);
                }
            }
        }
    }

    /**
     * deletes a key value pair on the file system
     *
     * @param key
     */
    public void delete(String key) {
        File f = new File(rootDir + File.separator + key);
        if (!f.isDirectory()) {
            if (f.isFile()) {
                f.delete();
            }
        }
    }
}
