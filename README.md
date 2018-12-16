# Distributed Key-Value Store
### Enterprise Computing WS 2018/19 - Assignment 2 - Distributed Key Value Storage


##Implementation
![alt text](./img/ToDo.png "Master AWS Console")


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

After 200 write operations, we manually collect the logs from Client, Master and Slave. The logs are then merged together, and manually transformed into a single CSV file.

## 3) Analysis

####Synchronous replication 
After collecting the logs, we have applied standard data cleaning transformations and aggregations to compute time differences between particular events. 
First we've analyse synchronous replication. Figure below depicts the differences in latency for 100 requests. On average one request needs 407ms to be processed (median 365ms). On a diagram we can observe 3 'peaks' which were probably caused by the network delays.      

![alt text](./img/LatencySync.jpeg "Latency synchronous")

![alt text](./img/StalenessSync.jpeg "Staleness sync")

 
####Asynchronous replication 

![alt text](./img/LatencyAsync.jpeg "Latency async")

![alt text](./img/StalenessAsync.jpeg "Staleness async")
