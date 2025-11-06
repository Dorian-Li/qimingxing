package com.example.qmx.server;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.slf4j.Logger;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
@Resource
public class DataServer {
    private RestTemplate    restTemplate;
    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    public DataServer(RestTemplate restTemplate){
        this.restTemplate=restTemplate;
    }

    public String fetchData() {
        String url = "https://union-sit.smartmideazy.com/union/service/pmv/control/aqj/device/query";

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 构建请求体
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("reqId", "28");

        // 构建HttpEntity对象
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        // 发送请求并获取响应
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);


        if(response.getStatusCode() == HttpStatus.OK){
            return response.getBody();

        } else {
            throw new RuntimeException("failed to fetch data from backend");
        }
    }


}
