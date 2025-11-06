package com.example.qmx.domain;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName(value ="ele")
public class Ele {


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("allPower")
    private Double allPower;

    @TableField("lightPower")
    private Double lightPower;

    @TableField("socketPower")
    private Double socketPower;

    @TableField("airconditionPower")
    private Double airconditionPower;

    @TableField("freshPower")
    private Double freshPower;

    @TableField("time")
    private Date time;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Double getAllPower() {
        return allPower;
    }

    public void setAllPower(Double allPower) {
        this.allPower = allPower;
    }

    public Double getLightPower() {
        return lightPower;
    }

    public void setLightPower(Double lightPower) {
        this.lightPower = lightPower;
    }

    public Double getSocketPower() {
        return socketPower;
    }

    public void setSocketPower(Double socketPower) {
        this.socketPower = socketPower;
    }

    public Double getAirconditionPower() {
        return airconditionPower;
    }

    public void setAirconditionPower(Double airconditionPower) {
        this.airconditionPower = airconditionPower;
    }

    public Double getFreshPower() {
        return freshPower;
    }

    public void setFreshPower(Double freshPower) {
        this.freshPower = freshPower;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
