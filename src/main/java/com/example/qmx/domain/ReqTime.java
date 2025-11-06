package com.example.qmx.domain;

import lombok.Data;


@Data
//@Schema(description = "报表导出请求，对于时间来说，2023-05-12 09:25:22")
public class ReqTime {

    /**
     * 设备名称
     */
//    @Schema(description = "开始时间")
    private String deviceName;

    /**
     * 开始时间
     */
//    @Schema(description = "开始时间")
    private String startTime;

    /**
     * 结束时间
     */
//    @Schema(description = "结束时间")
    private String endTime;
}