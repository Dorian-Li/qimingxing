package com.example.qmx.server;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
public class DataProcessingServer {
    private DataServer dataServer;
    private DataToObj dataToObj;
    public DataProcessingServer(DataServer dataServer,DataToObj dataToObj){
        this.dataServer=dataServer;
        this.dataToObj=dataToObj;
    }
    @Scheduled(fixedRate = 5000)//单位毫秒，5*1000
    public void fetchAndSave(){
        String rawData=dataServer.fetchData();
        String value=dataToObj.extractData(rawData);

    }
}
