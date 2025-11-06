package com.example.qmx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.qmx.domain.Ele;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface EleMapper extends BaseMapper<Ele> {
    @Select("SELECT * FROM ELE WHERE time BETWEEN #{startTime} AND #{endTime} ORDER BY time DESC")
    List<Ele> getELEByTimeRange(@Param("startTime") String startTime,
                                        @Param("endTime") String endTime);

    @Select("SELECT " +
            "DATE_FORMAT(time, '%Y-%m-%d %H:00:00') AS hour, " +
            "MAX(allPower) - MIN(allPower) AS hourly_consumption " +
            "FROM ELE " +
            "WHERE DATE(time) = CURDATE() " +
            "GROUP BY DATE_FORMAT(time, '%Y-%m-%d %H:00:00') " +
            "ORDER BY hour")
    List<Map<String, Object>> getHourlyConsumption();

    @Select("SELECT " +
            "DATE_FORMAT(time, '%Y-%m-%d') AS day, " +
            "MAX(allPower) - MIN(allPower) AS daily_consumption " +
            "FROM ELE " +
            "WHERE time >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) " +
            "GROUP BY DATE_FORMAT(time, '%Y-%m-%d') " +
            "ORDER BY day")
    List<Map<String, Object>> getDailyConsumptionLast7Days();


    @Select("SELECT " +
            "DATE_FORMAT(time, '%Y-%m') AS month, " +
            "MAX(allPower) - MIN(allPower) AS monthly_consumption " +
            "FROM ELE " +
            "WHERE YEAR(time) = YEAR(CURDATE()) " +
            "GROUP BY DATE_FORMAT(time, '%Y-%m') " +
            "ORDER BY month")
    List<Map<String, Object>> getMonthlyConsumption();


    @Select("SELECT " +
            "MAX(CASE WHEN DATE(time) = CURDATE() THEN allPower ELSE NULL END) - " +
            "MIN(CASE WHEN DATE(time) = CURDATE() THEN allPower ELSE NULL END) AS total_consumption_today, " +
            "IFNULL(" +
            "((MAX(CASE WHEN DATE(time) = CURDATE() THEN allPower ELSE NULL END) - " +
            "MIN(CASE WHEN DATE(time) = CURDATE() THEN allPower ELSE NULL END)) - " +
            "(MAX(CASE WHEN DATE(time) = CURDATE() - INTERVAL 1 YEAR THEN allPower ELSE NULL END) - " +
            "MIN(CASE WHEN DATE(time) = CURDATE() - INTERVAL 1 YEAR THEN allPower ELSE NULL END))) / " +
            "NULLIF((MAX(CASE WHEN DATE(time) = CURDATE() - INTERVAL 1 YEAR THEN allPower ELSE NULL END) - " +
            "MIN(CASE WHEN DATE(time) = CURDATE() - INTERVAL 1 YEAR THEN allPower ELSE NULL END)), 0) * 100, " +
            "-1000) AS year_on_year_growth, " +
            "IFNULL(" +
            "((MAX(CASE WHEN DATE(time) = CURDATE() THEN allPower ELSE NULL END) - " +
            "MIN(CASE WHEN DATE(time) = CURDATE() THEN allPower ELSE NULL END)) - " +
            "(MAX(CASE WHEN DATE(time) = CURDATE() - INTERVAL 1 DAY THEN allPower ELSE NULL END) - " +
            "MIN(CASE WHEN DATE(time) = CURDATE() - INTERVAL 1 DAY THEN allPower ELSE NULL END))) / " +
            "NULLIF((MAX(CASE WHEN DATE(time) = CURDATE() - INTERVAL 1 DAY THEN allPower ELSE NULL END) - " +
            "MIN(CASE WHEN DATE(time) = CURDATE() - INTERVAL 1 DAY THEN allPower ELSE NULL END)), 0) * 100, " +
            "-1000) AS day_on_day_growth " +
            "FROM ELE " +
            "WHERE DATE(time) = CURDATE() OR DATE(time) = CURDATE() - INTERVAL 1 DAY OR DATE(time) = CURDATE() - INTERVAL 1 YEAR")
    Map<String, Object> getConsumptionAndGrowthRates();


        @Select("SELECT " +
                "MAX(CASE WHEN YEAR(time) = YEAR(CURDATE()) AND MONTH(time) = MONTH(CURDATE()) THEN allPower ELSE NULL END) - " +
                "MIN(CASE WHEN YEAR(time) = YEAR(CURDATE()) AND MONTH(time) = MONTH(CURDATE()) THEN allPower ELSE NULL END) AS total_consumption_this_month, " +
                "IFNULL(" +
                "((MAX(CASE WHEN YEAR(time) = YEAR(CURDATE()) AND MONTH(time) = MONTH(CURDATE()) THEN allPower ELSE NULL END) - " +
                "MIN(CASE WHEN YEAR(time) = YEAR(CURDATE()) AND MONTH(time) = MONTH(CURDATE()) THEN allPower ELSE NULL END)) - " +
                "(MAX(CASE WHEN YEAR(time) = YEAR(CURDATE()) - 1 AND MONTH(time) = MONTH(CURDATE()) THEN allPower ELSE NULL END) - " +
                "MIN(CASE WHEN YEAR(time) = YEAR(CURDATE()) - 1 AND MONTH(time) = MONTH(CURDATE()) THEN allPower ELSE NULL END))) / " +
                "NULLIF((MAX(CASE WHEN YEAR(time) = YEAR(CURDATE()) - 1 AND MONTH(time) = MONTH(CURDATE()) THEN allPower ELSE NULL END) - " +
                "MIN(CASE WHEN YEAR(time) = YEAR(CURDATE()) - 1 AND MONTH(time) = MONTH(CURDATE()) THEN allPower ELSE NULL END)), 0) * 100, " +
                "-1000) AS year_on_year_growth, " +
                "IFNULL(" +
                "((MAX(CASE WHEN YEAR(time) = YEAR(CURDATE()) AND MONTH(time) = MONTH(CURDATE()) THEN allPower ELSE NULL END) - " +
                "MIN(CASE WHEN YEAR(time) = YEAR(CURDATE()) AND MONTH(time) = MONTH(CURDATE()) THEN allPower ELSE NULL END)) - " +
                "(MAX(CASE WHEN YEAR(time) = YEAR(CURDATE()) AND MONTH(time) = MONTH(CURDATE()) - 1 THEN allPower ELSE NULL END) - " +
                "MIN(CASE WHEN YEAR(time) = YEAR(CURDATE()) AND MONTH(time) = MONTH(CURDATE()) - 1 THEN allPower ELSE NULL END))) / " +
                "NULLIF((MAX(CASE WHEN YEAR(time) = YEAR(CURDATE()) AND MONTH(time) = MONTH(CURDATE()) - 1 THEN allPower ELSE NULL END) - " +
                "MIN(CASE WHEN YEAR(time) = YEAR(CURDATE()) AND MONTH(time) = MONTH(CURDATE()) - 1 THEN allPower ELSE NULL END)), 0) * 100, " +
                "-1000) AS month_on_month_growth " +
                "FROM ELE " +
                "WHERE " +
                "(YEAR(time) = YEAR(CURDATE()) AND MONTH(time) = MONTH(CURDATE())) OR " +
                "(YEAR(time) = YEAR(CURDATE()) - 1 AND MONTH(time) = MONTH(CURDATE())) OR " +
                "(YEAR(time) = YEAR(CURDATE()) AND MONTH(time) = MONTH(CURDATE()) - 1)")
        Map<String, Object> getMonthlyConsumptionAndGrowthRates();


    @Select("SELECT " +
            "MAX(CASE WHEN YEAR(time) = YEAR(CURDATE()) THEN allPower ELSE NULL END) - " +
            "MIN(CASE WHEN YEAR(time) = YEAR(CURDATE()) THEN allPower ELSE NULL END) AS total_consumption_this_year, " +
            "IFNULL(" +
            "((MAX(CASE WHEN YEAR(time) = YEAR(CURDATE()) THEN allPower ELSE NULL END) - " +
            "MIN(CASE WHEN YEAR(time) = YEAR(CURDATE()) THEN allPower ELSE NULL END)) - " +
            "(MAX(CASE WHEN YEAR(time) = YEAR(CURDATE()) - 1 THEN allPower ELSE NULL END) - " +
            "MIN(CASE WHEN YEAR(time) = YEAR(CURDATE()) - 1 THEN allPower ELSE NULL END))) / " +
            "NULLIF((MAX(CASE WHEN YEAR(time) = YEAR(CURDATE()) - 1 THEN allPower ELSE NULL END) - " +
            "MIN(CASE WHEN YEAR(time) = YEAR(CURDATE()) - 1 THEN allPower ELSE NULL END)), 0) * 100, " +
            "-1000) AS year_on_year_growth, " +
            "IFNULL(" +
            "((MAX(CASE WHEN YEAR(time) = YEAR(CURDATE()) THEN allPower ELSE NULL END) - " +
            "MIN(CASE WHEN YEAR(time) = YEAR(CURDATE()) THEN allPower ELSE NULL END)) - " +
            "(MAX(CASE WHEN YEAR(time) = YEAR(CURDATE()) - 1 THEN allPower ELSE NULL END) - " +
            "MIN(CASE WHEN YEAR(time) = YEAR(CURDATE()) - 1 THEN allPower ELSE NULL END))) / " +
            "NULLIF((MAX(CASE WHEN YEAR(time) = YEAR(CURDATE()) - 1 THEN allPower ELSE NULL END) - " +
            "MIN(CASE WHEN YEAR(time) = YEAR(CURDATE()) - 1 THEN allPower ELSE NULL END)), 0) * 100, " +
            "-1000) AS year_on_year_growth " +
            "FROM ELE " +
            "WHERE YEAR(time) = YEAR(CURDATE()) OR YEAR(time) = YEAR(CURDATE()) - 1")
    Map<String, Object> getYearlyConsumptionAndGrowthRates();


    @Select("SELECT " +
            "MAX(lightPower) - MIN(lightPower) AS lightConsumptionToday, " +
            "MAX(socketPower) - MIN(socketPower) AS socketConsumptionToday, " +
            "MAX(airconditionPower) - MIN(airconditionPower) AS airconditionConsumptionToday, " +
            "MAX(freshPower) - MIN(freshPower) AS freshConsumptionToday, " +
            "MAX(allPower) - MIN(allPower) AS totalConsumptionToday " +
            "FROM ELE " +
            "WHERE DATE(time) = CURDATE()")
    Map<String, Object> getTodayConsumption();


    @Select("SELECT * FROM ( " +
            "    SELECT *, ROW_NUMBER() OVER ( " +
            "        PARTITION BY DATE(time), HOUR(time), FLOOR(MINUTE(time) / 30) " +
            "        ORDER BY time ASC " +
            "    ) AS row_num " +
            "    FROM Ele " +
            "    WHERE time BETWEEN #{startTime} AND #{endTime} " +
            ") AS grouped_data " +
            "WHERE row_num = 1 " +
            "ORDER BY time ASC")
    List<Ele> getEleByTimeRange(        @Param("startTime") String startTime,
                                           @Param("endTime") String endTime);

}
