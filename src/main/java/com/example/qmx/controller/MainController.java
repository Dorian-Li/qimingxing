package com.example.qmx.controller;

import com.example.qmx.domain.*;
import com.example.qmx.mapper.AirConditionMapper;
import com.example.qmx.mapper.EleMapper;
import com.example.qmx.mapper.EnvironmentMapper;
import com.example.qmx.server.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController

@CrossOrigin(origins = "*")
public class MainController {


    private final TempControl tempControl;
    private DataServer dataServer;
    private DataToObj  dataToObj;
    @Autowired
    public  MainController(TempControl tempControl, DataServer dataServer, DataToObj dataToObj){
        this.tempControl = tempControl;
        this.dataServer=dataServer;
        this.dataToObj=dataToObj;
    }

    @PostMapping("/calculatePMV")
    @ApiOperation(value = "计算PMV值",notes = "环境温度，年龄，性别（0女，1男），空调模式（制冷1，制热2，其他3）")
    public String calculatePMV(int t, int age, int gendar, int ACDataModel){
        Double add =  PMVInterface.MY_DLL.calculatePMV(t,age,gendar,ACDataModel);
        System.out.println("Java:" + add);
        return add.toString();
    }

    @PostMapping("/setAC")
    @ApiOperation(value = "设置人员参数",notes = "1-3人")
    public String calculateTemp(@RequestBody PeopleRequest peopleRequest){
        int num = peopleRequest.getNumberOfPeople();
        List<Person> people = peopleRequest.getPeople();
        double targetPMV=0.5;
        int temp=tempControl.calculateTemp(targetPMV, num, people);
        tempControl.setManualChangeCount(0);
        return "temp="+temp;
    }

    @GetMapping("/getACSet")
    @ApiOperation(value = "查询最后的折中温度",notes = "")
    public int findSetTemp(){
        return tempControl.getLastSetTemp();
    }





//    空调相关-------------------------------------------------------
    @Autowired
    private AirConditionMapper airConditionMapper;

    @ApiOperation(value = "获取空调最新数据",notes = "次卧空调")
    @GetMapping("/getLastAC")
    public AirCondition getLastAC(String devName){
        return airConditionMapper.getLastAC(devName);

    }

    @ApiOperation(value = "空调报表")
    @PostMapping("/getACByTimeRange")
    public List<AirCondition> getACByTimeRange(@RequestBody ReqTime reqTime){
        String startTime=reqTime.getStartTime();
        String endTime = reqTime.getEndTime();
        String deviceName = reqTime.getDeviceName();
        return airConditionMapper.getACByTimeRange(deviceName,startTime,endTime);
    }

    //    新风相关-------------------------------------------------------
//    @Autowired
//    private XinFengMapper xinFengMapper;
//
//    @ApiOperation(value = "获取新风最新数据",notes = "新风系统")
//    @GetMapping("/getLastXF")
//    public AirCondition getLastXF(String devName){
//        return airConditionMapper.getLastAC(devName);
//
//    }
//
//    @ApiOperation(value = "新风报表")
//    @GetMapping("/getXFByTimeRange")
//    public List<AirCondition> getXFByTimeRange(String startTime,String endTime){
//        return airConditionMapper.getACByTimeRange(startTime,endTime);
//    }


//    环境相关--------------------------------------------------------
    @Autowired
    private EnvironmentMapper environmentMapper;

    @ApiOperation(value = "获取环境最新数据",notes = "次卧环境探测器，环境探测器床头")
    @GetMapping("/getLastENV")
    public Environment getLastENV(String devName){
        return environmentMapper.getLastENV(devName);

    }

    @ApiOperation(value = "获取所有环境最新数据",notes = "次卧环境探测器，环境探测器床头")
    @GetMapping("/getAllLastENV")
    public List<Environment> getAllLastENV(){
        List<Environment> environments = new ArrayList<>();
        Environment e1= environmentMapper.getLastENV("环境探测器浴室");
        Environment e2= environmentMapper.getLastENV("环境探测器客厅");
        Environment e3= environmentMapper.getLastENV("环境探测器主卧");
        Environment e4= environmentMapper.getLastENV("环境探测器儿童房");
        environments.add(e1);
        environments.add(e2);
        environments.add(e3);
        environments.add(e4);
        return environments;

    }

    @ApiOperation(value = "获取24h温度数据",notes = "次卧环境探测器，环境探测器床头")
    @GetMapping("/getLast24HoursTemperature")
    public List<Environment> getLast24HoursTemperature(){
        List<Environment> environments = new ArrayList<>();
        List<Environment> e1= environmentMapper.getLast24HoursTemperature("环境探测器浴室");
        List<Environment> e2= environmentMapper.getLast24HoursTemperature("环境探测器客厅");
        List<Environment> e3= environmentMapper.getLast24HoursTemperature("环境探测器主卧");
        List<Environment> e4= environmentMapper.getLast24HoursTemperature("环境探测器儿童房");
        environments.addAll(e1);
        environments.addAll(e2);
        environments.addAll(e3);
        environments.addAll(e4);
        return environments;
    }

    @ApiOperation(value = "获取24h湿度数据",notes = "次卧环境探测器，环境探测器床头")
    @GetMapping("/getLast24HoursHumidity")
    public List<Environment> getLast24HoursHumidity(){
        List<Environment> environments = new ArrayList<>();
        List<Environment> e1= environmentMapper.getLast24HoursHumidity("环境探测器浴室");
        List<Environment> e2= environmentMapper.getLast24HoursHumidity("环境探测器客厅");
        List<Environment> e3= environmentMapper.getLast24HoursHumidity("环境探测器主卧");
        List<Environment> e4= environmentMapper.getLast24HoursHumidity("环境探测器儿童房");
        environments.addAll(e1);
        environments.addAll(e2);
        environments.addAll(e3);
        environments.addAll(e4);
        return environments;
    }

    @ApiOperation(value = "获取24hPM2.5数据",notes = "次卧环境探测器，环境探测器床头")
    @GetMapping("/getLast24HoursPm25")
    public List<Environment> getLast24HoursPm25(){
        List<Environment> environments = new ArrayList<>();
        List<Environment> e1= environmentMapper.getLast24HoursPm25("环境探测器浴室");
        List<Environment> e2= environmentMapper.getLast24HoursPm25("环境探测器客厅");
        List<Environment> e3= environmentMapper.getLast24HoursPm25("环境探测器主卧");
        List<Environment> e4= environmentMapper.getLast24HoursPm25("环境探测器儿童房");
        environments.addAll(e1);
        environments.addAll(e2);
        environments.addAll(e3);
        environments.addAll(e4);
        return environments;
    }

    @ApiOperation(value = "获取24hPM10数据",notes = "次卧环境探测器，环境探测器床头")
    @GetMapping("/getLast24HoursPm10")
    public List<Environment> getLast24HoursPm10(){
        List<Environment> environments = new ArrayList<>();
        List<Environment> e1= environmentMapper.getLast24HoursPm10("环境探测器浴室");
        List<Environment> e2= environmentMapper.getLast24HoursPm10("环境探测器客厅");
        List<Environment> e3= environmentMapper.getLast24HoursPm10("环境探测器主卧");
        List<Environment> e4= environmentMapper.getLast24HoursPm10("环境探测器儿童房");
        environments.addAll(e1);
        environments.addAll(e2);
        environments.addAll(e3);
        environments.addAll(e4);
        return environments;
    }

    @ApiOperation(value = "获取24hco2数据",notes = "次卧环境探测器，环境探测器床头")
    @GetMapping("/getLast24HoursCo2")
    public List<Environment> getLast24HoursCo2(){
        List<Environment> environments = new ArrayList<>();
        List<Environment> e1= environmentMapper.getLast24HoursCo2("环境探测器浴室");
        List<Environment> e2= environmentMapper.getLast24HoursCo2("环境探测器客厅");
        List<Environment> e3= environmentMapper.getLast24HoursCo2("环境探测器主卧");
        List<Environment> e4= environmentMapper.getLast24HoursCo2("环境探测器儿童房");
        environments.addAll(e1);
        environments.addAll(e2);
        environments.addAll(e3);
        environments.addAll(e4);
        return environments;
    }

//    @ApiOperation(value = "获取环境报表")
//    @PostMapping("/getACByTimeRange")
//    public List<EnvOut> getLast7DayEnv(){
//        return environmentMapper.getLast7DaysTemperatureReport();
//    }


    @ApiOperation(value = "获取环境报表")
    @PostMapping("/getEnvByTimeRange")
    public List<EnvOut> getLast7DayEnv(@RequestBody ReqTime reqTime){
        String startTime=reqTime.getStartTime();
        String endTime = reqTime.getEndTime();
        String deviceName = reqTime.getDeviceName();
        return environmentMapper.getEnvironmentByTimeRange(deviceName,startTime,endTime);
    }


//能耗相关——————————————————————————————————————————————————————————————

    @Autowired
    private EleMapper eleMapper;

    @ApiOperation(value = "计算每小时能耗")
    @GetMapping("/getHourlyConsumption")
    public List<Map<String, Object>> getHourlyConsumption(){
        List<Map<String, Object>> HourlyConsumption= eleMapper.getHourlyConsumption();

        return HourlyConsumption;
    }

    @ApiOperation(value = "计算每天能耗")
    @GetMapping("/getDailyConsumptionLast7Days")
    public List<Map<String, Object>> getDailyConsumptionLast7Days(){
        List<Map<String, Object>> DailyConsumptionLast7Days= eleMapper.getDailyConsumptionLast7Days();

        return DailyConsumptionLast7Days;
    }


    @ApiOperation(value = "计算每月能耗")
    @GetMapping("/getMonthlyConsumption")
    public List<Map<String, Object>> getMonthlyConsumption(){
        List<Map<String, Object>> MonthlyConsumption= eleMapper.getMonthlyConsumption();

        return MonthlyConsumption;
    }


    @ApiOperation(value = "每日能耗，同比，环比")
    @GetMapping("/getConsumptionAndGrowthRates")
    public Map<String, Object> getConsumptionAndGrowthRates(){
        Map<String, Object> ConsumptionAndGrowthRates= eleMapper.getConsumptionAndGrowthRates();

        return ConsumptionAndGrowthRates;
    }

    @ApiOperation(value = "每月能耗，同比，环比")
    @GetMapping("/getMonthlyConsumptionAndGrowthRates")
    public Map<String, Object> getMonthlyConsumptionAndGrowthRates(){
        Map<String, Object> MonthlyConsumptionAndGrowthRates= eleMapper.getMonthlyConsumptionAndGrowthRates();

        return MonthlyConsumptionAndGrowthRates;
    }

    @ApiOperation(value = "每年能耗，同比")
    @GetMapping("/getYearlyConsumptionAndGrowthRates")
    public Map<String, Object> getYearlyConsumptionAndGrowthRates(){
        Map<String, Object> YearlyConsumptionAndGrowthRates= eleMapper.getYearlyConsumptionAndGrowthRates();

        return YearlyConsumptionAndGrowthRates;
    }


    @ApiOperation(value = "当日分项能耗")
    @GetMapping("/getTodayConsumption")
    public Map<String, Object> getTodayConsumption(){
        Map<String, Object> getTodayConsumption= eleMapper.getTodayConsumption();

        return getTodayConsumption;
    }

    @ApiOperation(value = "获取能耗报表")
    @PostMapping("/getEleByTimeRange")
    public List<Ele> getEleByTimeRange(@RequestBody ReqTime reqTime){
        String startTime=reqTime.getStartTime();
        String endTime = reqTime.getEndTime();
        return eleMapper.getEleByTimeRange(startTime,endTime);
    }

}
