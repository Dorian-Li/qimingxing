package com.example.qmx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.qmx.domain.AirCondition;
import com.example.qmx.domain.XinFeng;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface XinFengMapper extends BaseMapper<XinFeng> {
    // 根据设备名称查询最新一条数据
    @Select("SELECT * FROM XF WHERE devName = #{devName} ORDER BY time DESC LIMIT 1")
    XinFeng getLastXF(String devName);

    @Select("SELECT * FROM XF WHERE time BETWEEN #{startTime} AND #{endTime} ORDER BY time DESC")
    List<AirCondition> getACByTimeRange(@Param("startTime") String startTime,
                                        @Param("endTime") String endTime);
}
