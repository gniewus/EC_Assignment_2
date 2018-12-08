package de.tub.ise.ec.servers;

import java.util.List;
import java.util.Map;

public interface IServer {

    public int getPort();

    public String getHost();

    public Map<Integer, List<String>> getOperationsMap();

}
