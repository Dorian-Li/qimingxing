package com.example.qmx.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value ="sd")
public class SL {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("devName")
    private String devName;

    @TableField("status")
    private Integer status;

    @TableField("level")
    private Double level;

    @TableField("time")
    private Date time;
}
