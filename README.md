# Distributed Key-Value Store
### Enterprise Computing WS 2018/19 - Assignment 2 - Distributed Key Value Storage
Authors: Jacek Janczura & Tomasz Tkaczyk

## Implementation
![alt text](./img/ToDo.png "Master AWS Console")
<br><br>
The aim of the project was to implement simple distributed key-value store which consists of two replicas, whereby one is the master and one is the slave. Clients always write to the master but can potentially read from both replica.

* JavaDoc is provided. All the details connected with the implementation can be found there.

* Ports and hosts are taken from config.properties file. 
```
portSlave=8000
hostSlave=127.0.0.1
portMaster=8001
hostMaster=127.0.0.1
```
* All logs are stored in the file - EC_Assignment_2.log

* To run the simple main just run the jar. <br>
 ``java -jar target/EC_Assignment_2-1.0-SNAPSHOT-shaded.jar ``

### Deployment
After the development phase, we've deployed two variants of our code on two AWS EC2 instances. We've decided to take two t2.micro instances and spin them in different avalibility zones. The Master instance was deployed on  *EU-West-1* (Irland), whereas the Slave was deployed on *EU-Central-1* (Frankfurt). 
![alt text](./img/slave.png "Slave AWS Console")

![alt text](./img/master.png "Master AWS Console")

## 2) Benchmarking Latency and Staleness 

Client application was started locally. 

```
    /**
     * Method used for benchmarking latency and staleness of asynchronic replication. 
     * This method sends an update (In our KV store write is equal to an update) every second for 100s using asynchron. replication.
     */
    public void crazyUpdateAsynchronic() {
        for (int i = 0; i < 100; i++) {
            Request req = asyncWrite("Asy", "req" + i);
            sendSyncMsgToMaster(req);
            sleep(1000);
        }
    }
    /**
     * Method used for benchmarking latency and staleness of synchronic replication. 
     * This method sends an update (In our KV store write is equal to an update) every second for 100s using synchron. replication.
     */
    public void crazyUpdateSynchronic() {
        for (int i = 0; i < 100; i++) {
            Request req = syncWrite("Sy", "req" + i);
            sendSyncMsgToMaster(req);
            sleep(1000);
        }
    }
```

After 200 write operations, we manually collect the logs from Client, Master and Slave. The logs are then merged together, and manualy transformed into a CSV file.

## 3) Analysis

