package com.example.qmx.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName(value ="ac")
public class AirCondition {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("devName")
    private String devName;

    @TableField("temperature")
    private Integer temperature;

    @TableField("windSpeed")
    private Integer windSpeed;

    @TableField("status")
    private Integer status;

    @TableField("mode")
    private String mode;

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

    public Integer getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Integer windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
