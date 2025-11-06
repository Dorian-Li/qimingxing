package com.example.qmx.domain;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName(value ="env")
public class Environment {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("devName")
    private String devName;

    @TableField("temperature")
    private Integer temperature;

    @TableField("humidity")
    private Integer humidity;

    @TableField("co2")
    private Integer co2;

    @TableField("aqi1")
    private Integer aqi1;

    @TableField("aqi2")
    private Integer aqi2;


    @TableField("pm25")
    private Integer pm25;

    @TableField("pm10")
    private Integer pm10;


    @TableField("time")
    private Date time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Integer getCo2() {
        return co2;
    }

    public void setCo2(Integer co2) {
        this.co2 = co2;
    }

    public Integer getAqi1() {
        return aqi1;
    }

    public void setAqi1(Integer aqi1) {
        this.aqi1 = aqi1;
    }

    public Integer getAqi2() {
        return aqi2;
    }

    public void setAqi2(Integer aqi2) {
        this.aqi2 = aqi2;
    }


    public Integer getPm25() {
        return pm25;
    }

    public void setPm25(Integer pm25) {
        this.pm25 = pm25;
    }

    public Integer getPm10() {
        return pm10;
    }

    public void setPm10(Integer pm10) {
        this.pm10 = pm10;
    }



    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
