package com.example.qmx.server;

import com.example.qmx.domain.AirCondition;
import com.example.qmx.domain.Ele;
import com.example.qmx.domain.Environment;
import com.example.qmx.mapper.AirConditionMapper;
import com.example.qmx.mapper.EleMapper;
import com.example.qmx.mapper.EnvironmentMapper;
import com.example.qmx.mapper.XinFengMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Service
public class DataToObj {


    @Autowired
    private AirConditionMapper airConditionMapper;
    @Autowired
    private EnvironmentMapper environmentMapper;
    @Autowired
    private XinFengMapper xinFengMapper;
    @Autowired
    private EleMapper eleMapper;


    private ObjectMapper objectMapper;
    private final TempControl tempControl;
    @Autowired
    public DataToObj(TempControl tempControl){
        this.tempControl = tempControl;
        this.objectMapper=new ObjectMapper();

    }
    private static final Map<String, String> airConditionMap = new HashMap<>(); // 设备字典
    private static final Map<String, String> envMap = new HashMap<>(); // 设备字典

    static {
        // 在静态代码块中定义多个设备名称
        airConditionMap.put("空调客厅","空调客厅");
        airConditionMap.put("空调书房","空调书房");
        airConditionMap.put("空调儿童房","空调儿童房");
        airConditionMap.put("空调主卧","空调主卧");
        airConditionMap.put("空调厨房","空调厨房");

        envMap.put("环境探测器浴室","环境探测器浴室");
        envMap.put("环境探测器客厅","环境探测器客厅");
        envMap.put("环境探测器主卧","环境探测器主卧");
        envMap.put("环境探测器儿童房","环境探测器儿童房");

    }

    public String extractData(String jsonString){
        try {
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            JsonNode dataArray = jsonNode.get("data");
            Ele ele= new Ele();
            for (JsonNode node : dataArray) {

                String devName = node.get("devName").asText();

                if (airConditionMap.containsKey(devName)) {

                    AirCondition airCondition=new AirCondition();
                    airCondition.setDevName(devName);

                    // 解析嵌套的status字段
                    JsonNode statusNode = objectMapper.readTree(node.get("status").asText());
                    int  temperature =statusNode.path("setTemper").asInt();
                    temperature=temperature/100;
                    airCondition.setTemperature(temperature);
                    int windSpeed = statusNode.path("windSpeed").asInt();
                    airCondition.setWindSpeed(windSpeed);
                    int status =statusNode.path("power_1").asInt();
                    airCondition.setStatus(status);
                    String mode = statusNode.path("operationMode").toString();
                    airCondition.setMode(mode);

                    int a=airConditionMapper.insert(airCondition);
                    if (a>0){
                        System.out.println("插入空调数据成功");
                    }else {
                        System.out.println("插入空调数据失败");
                    }

                }

                if (envMap.containsKey(devName)) {
                    Environment environment = new Environment();
                    environment.setDevName(devName);
                    JsonNode statusNode = objectMapper.readTree(node.get("status").asText());
                    int  temperature =statusNode.path("curTemper").asInt();
                    environment.setTemperature(temperature);
                    int humidity = statusNode.path("curHumidity").asInt();
                    environment.setHumidity(humidity);
                    int co2 = statusNode.path("co2").asInt();
                    environment.setCo2(co2);
                    int aqi1 =statusNode.path("aqi").asInt();
                    environment.setAqi1(aqi1);
                    int aqi2 = statusNode.path("AQI").asInt();
                    environment.setAqi2(aqi2);
//                    int tvoc = statusNode.path("tvoc").asInt();
//                    environment.setTvoc(tvoc);
                    int pm25 =statusNode.path("PM25").asInt();
                    environment.setPm25(pm25);
                    int pm10 = statusNode.path("PM10").asInt();
                    environment.setPm10(pm10);
//                    int hcho = statusNode.path("hcho").asInt();
//                    environment.setHcho(hcho);

                    int a=environmentMapper.insert(environment);
                    if (a>0){
                        System.out.println("插入环境数据成功");
                    }else {
                        System.out.println("插入环境数据失败");
                    }
                }




                if (devName.equals("空调断路器")) {

                    JsonNode statusNode = objectMapper.readTree(node.get("status").asText());
                    double  power =statusNode.path("kWh_Total").asInt();
                    ele.setAirconditionPower(power/100);


                }
                if (devName.equals("新风断路器")) {

                    JsonNode statusNode = objectMapper.readTree(node.get("status").asText());
                    double  power =statusNode.path("kWh_Total").asInt();
                    ele.setFreshPower(power/100);

                }
                if (devName.equals("照明断路器")) {

                    JsonNode statusNode = objectMapper.readTree(node.get("status").asText());
                    double  power =statusNode.path("kWh_Total").asInt();
                    ele.setLightPower(power/100);

                }

            }

            double calAll=0.0;
            if (ele.getLightPower()==null){ele.setLightPower(0.0);}else {calAll=calAll+ele.getLightPower();}
            if (ele.getAirconditionPower()==null){ele.setAirconditionPower(0.0);}else {calAll=calAll+ele.getAirconditionPower();}
            if (ele.getFreshPower()==null){ele.setFreshPower(0.0);}else {calAll=calAll+ele.getFreshPower();}
            if (ele.getSocketPower()==null){ele.setSocketPower(0.0);}else {calAll=calAll+ele.getSocketPower();}
            double allPower=Math.round(calAll*100.0)/100.0;
            ele.setAllPower(allPower);



            int a=eleMapper.insert(ele);
            if (a>0){
                System.out.println("插入能耗数据成功");
            }else {
                System.out.println("插入能耗数据失败");
            }


        } catch (IOException e){
                e.printStackTrace();
                return null;
            }
        return "null";
    }

}
