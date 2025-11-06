package com.example.qmx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.qmx.domain.AirCondition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface AirConditionMapper extends BaseMapper<AirCondition> {
    // 根据设备名称查询最新一条数据
    @Select("SELECT * FROM AC WHERE devName = #{devName} ORDER BY time DESC LIMIT 1")
    AirCondition getLastAC(String devName);

    @Select("SELECT * FROM ( " +
            "    SELECT *, ROW_NUMBER() OVER ( " +
            "        PARTITION BY DATE(time), HOUR(time), FLOOR(MINUTE(time) / 30), devName " +
            "        ORDER BY time ASC " +
            "    ) AS row_num " +
            "    FROM AC " +
            "    WHERE time BETWEEN #{startTime} AND #{endTime} AND devName = #{deviceName} " +
            ") AS grouped_data " +
            "WHERE row_num = 1 " +
            "ORDER BY time ASC")
    List<AirCondition> getACByTimeRange(@Param("deviceName") String deviceName,
                                        @Param("startTime") String startTime,
                                        @Param("endTime") String endTime);



}
