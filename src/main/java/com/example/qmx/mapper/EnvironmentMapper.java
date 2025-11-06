package com.example.qmx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.qmx.domain.EnvOut;
import com.example.qmx.domain.Environment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface EnvironmentMapper extends BaseMapper<Environment> {
    @Select("SELECT * FROM ENV WHERE devName = #{devName} ORDER BY time DESC LIMIT 1")
    Environment getLastENV(String devName);

//    @Select("SELECT * FROM ENV WHERE time BETWEEN #{startTime} AND #{endTime} ORDER BY time DESC")
//    List<Environment> getEnvironmentByTimeRange(@Param("startTime") String startTime,
//                                        @Param("endTime") String endTime);


    @Select("SELECT * FROM ( " +
            "    SELECT *, ROW_NUMBER() OVER ( " +
            "        PARTITION BY DATE(time), HOUR(time), FLOOR(MINUTE(time) / 30), devName " +
            "        ORDER BY time ASC " +
            "    ) AS row_num " +
            "    FROM ENV " +
            "    WHERE time BETWEEN #{startTime} AND #{endTime} AND devName = #{deviceName} " +
            ") AS grouped_data " +
            "WHERE row_num = 1 " +
            "ORDER BY time ASC")
    List<EnvOut> getEnvironmentByTimeRange(@Param("deviceName") String deviceName,
                                        @Param("startTime") String startTime,
                                        @Param("endTime") String endTime);



    @Select("SELECT id,devName,temperature,time " +
            "FROM (" +
            "    SELECT *, DATE_FORMAT(time, '%Y-%m-%d %H:00:00') AS hour_group " +
            "    FROM ENV " +
            "    WHERE time >= CURDATE() " +
            "    AND time < CURDATE() + INTERVAL 1 DAY " +
            "    AND devName = #{devName} " +
            "    ORDER BY time DESC" +
            ") AS hourly_data " +
            "GROUP BY hour_group " +
            "ORDER BY hour_group ASC")
    List<Environment> getLast24HoursTemperature(String devName);

    @Select("SELECT id,devName,humidity,time " +
            "FROM (" +
            "    SELECT *, DATE_FORMAT(time, '%Y-%m-%d %H:00:00') AS hour_group " +
            "    FROM ENV " +
            "    WHERE time >= CURDATE() " +
            "    AND time < CURDATE() + INTERVAL 1 DAY " +
            "    AND devName = #{devName} " +
            "    ORDER BY time DESC" +
            ") AS hourly_data " +
            "GROUP BY hour_group " +
            "ORDER BY hour_group ASC")
    List<Environment> getLast24HoursHumidity(String devName);

    @Select("SELECT id,devName,pm25,time " +
            "FROM (" +
            "    SELECT *, DATE_FORMAT(time, '%Y-%m-%d %H:00:00') AS hour_group " +
            "    FROM ENV " +
            "    WHERE time >= CURDATE() " +
            "    AND time < CURDATE() + INTERVAL 1 DAY " +
            "    AND devName = #{devName} " +
            "    ORDER BY time DESC" +
            ") AS hourly_data " +
            "GROUP BY hour_group " +
            "ORDER BY hour_group ASC")
    List<Environment> getLast24HoursPm25(String devName);

    @Select("SELECT id,devName,pm10,time " +
            "FROM (" +
            "    SELECT *, DATE_FORMAT(time, '%Y-%m-%d %H:00:00') AS hour_group " +
            "    FROM ENV " +
            "    WHERE time >= CURDATE() " +
            "    AND time < CURDATE() + INTERVAL 1 DAY " +
            "    AND devName = #{devName} " +
            "    ORDER BY time DESC" +
            ") AS hourly_data " +
            "GROUP BY hour_group " +
            "ORDER BY hour_group ASC")
    List<Environment> getLast24HoursPm10(String devName);

    @Select("SELECT id,devName,co2,time " +
            "FROM (" +
            "    SELECT *, DATE_FORMAT(time, '%Y-%m-%d %H:00:00') AS hour_group " +
            "    FROM ENV " +
            "    WHERE time >= CURDATE() " +
            "    AND time < CURDATE() + INTERVAL 1 DAY " +
            "    AND devName = #{devName} " +
            "    ORDER BY time DESC" +
            ") AS hourly_data " +
            "GROUP BY hour_group " +
            "ORDER BY hour_group ASC")
    List<Environment> getLast24HoursCo2(String devName);



    @Select("SELECT id, time_group, time, devName, temperature, humidity, co2, pm25, pm10 " +
        "FROM ( " +
        "    SELECT id, devName, temperature, humidity, co2, pm25, pm10, time, " +
        "           DATE_FORMAT(time, '%Y-%m-%d %H:%i:00') AS time_group, " +
        "           ROW_NUMBER() OVER ( " +
        "               PARTITION BY DATE(time), HOUR(time), FLOOR(MINUTE(time) / 30), devName " +
        "               ORDER BY time ASC " +
        "           ) AS row_num " +
        "    FROM ENV " +
        "    WHERE time >= NOW() - INTERVAL 7 DAY " +
        ") AS grouped_data " +
        "WHERE row_num = 1 " +
        "ORDER BY time_group ASC")
    List<EnvOut> getLast7DaysTemperatureReport();




}
