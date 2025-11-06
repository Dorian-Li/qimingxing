package com.example.qmx.server;

import com.example.qmx.domain.AirCondition;
import com.example.qmx.domain.Person;
import com.example.qmx.mapper.AirConditionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TempControl {

    private int manualChangeCount = -1; // 记录人为改变次数
    private int calculatedTemp = -1;     // 计算出的温度
    private int lastSetTemp;        // 上一次的设定温度
    private boolean isAutoSet = false;
    @Autowired
    private PmvToTemp pmvToTemp;

    @Autowired
    private SendAC sendAC;

    @Autowired
    private AirConditionMapper airConditionMapper;

    String targetURL = "https://union-sit.smartmideazy.com/union/service/nlpControl/deviceControl"; // 目标URL


    // 从前端接收人体参数后计算出的温度
    public int calculateTemp(double targetPMV, int num, List<Person> people) {
        // 这是你现有的温度计算逻辑

        calculatedTemp= pmvToTemp.getTemp(targetPMV, num, people);
        lastSetTemp=calculatedTemp;

        sendAC.sendData(targetURL, calculatedTemp);
        System.out.println("计算最优温度为"+calculatedTemp);
        isAutoSet=true;
        return calculatedTemp;
    }

    // 当空调设定温度被人为改变时调用此方法
    public int onManualTempChange(double userSetTemp) {
        if (calculatedTemp == -1){
            manualChangeCount=-1;
        }else {
            manualChangeCount=manualChangeCount+1;  // 记录改变次数
        }
        System.out.println("人为调控次数为"+manualChangeCount);

        double newTemp;
        switch (manualChangeCount) {
            case 0:
                newTemp = calculatedTemp;
                break;
            case 1:
                // 第一次人为更改，新的温度= 0.75 * 计算值 + 0.25 * 人为设定值
                newTemp = 0.75 * calculatedTemp + 0.25 * userSetTemp;
                break;
            case 2:
                // 第二次人为更改，新的温度= 计算值和设定值的平均值
                newTemp = (calculatedTemp + userSetTemp) / 2;
                break;
            default:
                // 第三次及以后人为更改，设定值即为新的温度
                newTemp = userSetTemp;
                break;
        }
        // 更新最后设定的温度
        lastSetTemp = (int)newTemp;

        sendAC.sendData(targetURL, (int)newTemp);
        System.out.println("根据人为调整温度"+newTemp);
        try {
            System.out.println("开始延时...");
            Thread.sleep(5000); // 延时 2 秒（2000 毫秒）
            System.out.println("延时结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return (int) newTemp;
    }


    // 定时任务，每 5 秒检查一次是否有手动调控
    @Scheduled(fixedRate = 5000) // 5分钟检查一次
    public void monitorManualChange() {
        if (calculatedTemp!=-1){
            AirCondition airCondition = airConditionMapper.getLastAC("空调儿童房"); // 获取数据库最近设定的温度
            int nowTemp= airCondition.getTemperature();

            if (nowTemp != lastSetTemp && !isAutoSet) {
                System.out.println("检测到人为调控，数据库最新温度: " + nowTemp + ", 当前设定温度: " + lastSetTemp);
                onManualTempChange(nowTemp); // 触发人为调控处理
            } else {
                isAutoSet=false;
                System.out.println("未检测到人为调控");
            }
        }else{
            System.out.println("未计算");
        }
    }


    public int getManualChangeCount() {
        return manualChangeCount;
    }

    public void setManualChangeCount(int manualChangeCount) {
        this.manualChangeCount = manualChangeCount;
    }

    public int getCalculatedTemp() {
        int t=(int) Math.round(calculatedTemp);
        return t;
    }

    public void setCalculatedTemp(int calculatedTemp) {
        this.calculatedTemp = calculatedTemp;
    }

    public int getLastSetTemp() {
        return lastSetTemp;
    }

    public void setLastSetTemp(int lastSetTemp) {
        this.lastSetTemp = lastSetTemp;
    }
}
