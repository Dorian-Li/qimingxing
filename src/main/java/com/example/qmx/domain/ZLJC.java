package com.example.qmx.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value ="sd")
public class ZLJC {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("status")
    private Integer status;

    @TableField("targetNum")
    private Integer targetNum;

    @TableField("completeNum")
    private Integer completeNum;

    @TableField("passNum")
    private Integer passNum;

    @TableField("failNum")
    private Integer failNum;

    @TableField("rate")
    private Double rate;

    @TableField("time")
    private Date time;
}
