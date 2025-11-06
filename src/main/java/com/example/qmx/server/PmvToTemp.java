package com.example.qmx.server;

import com.example.qmx.domain.Person;
import com.example.qmx.mapper.AirConditionMapper;
import com.example.qmx.mapper.EnvironmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PmvToTemp {

    @Autowired
    private EnvironmentMapper environmentMapper;
    @Autowired
    private AirConditionMapper airConditionMapper;

    public void getPMV(List<Person> people,int temp){
        for (Person person : people) {
            int age=person.getAge();
            int gender=person.getGender();

//            Environment environmentDataAll=environmentMapper.getLastENV("次卧环境探测器");
//            int t = environmentDataAll.getTemperature()/100;
            int t=temp;

            int ACDataModel;
            if(t>26){
                ACDataModel=2;
            }
            else {
                ACDataModel=1;
            }


            double PMV= PMVInterface.MY_DLL.calculatePMV(t,age,gender,ACDataModel);
            person.setPMV(PMV);
        }

    }


    public double getMulPmv(int num, List<Person> people) {
        double finalPmv = 0.0;   // 保存最终的 PMV 结果
        double allweight = 0.0;  // 保存所有的权重总和

        switch (num) {
            case 1:
                // 情况1：累加所有人的 PMV 值
                for (Person person : people) {
                    finalPmv += person.getPMV();
                }
                break;

            case 2:
                // 情况2：根据年龄对每个人的 PMV 进行加权计算
                for (Person person : people) {
                    double weight = (person.getAge() < 16 || person.getAge() > 45) ? 0.6 : 0.4;
                    allweight += weight;
                    finalPmv += person.getPMV() * weight;
                }
                finalPmv = finalPmv / allweight;  // 计算加权平均值
                break;

            case 3:
                // 情况3：检查需要特殊处理的人数（年龄小于16或大于45）
                int needNum = 0;
                for (Person person : people) {
                    if (person.getAge() < 16 || person.getAge() > 45) {
                        needNum++;
                    }
                }

                if (needNum == 1) {
                    // 情况3.1：如果特殊年龄段的人员只有1人，则按特定权重进行计算
                    for (Person person : people) {
                        double weight = (person.getAge() < 16 || person.getAge() > 45) ? 0.4 : 0.3;
                        allweight += weight;
                        finalPmv += person.getPMV() * weight;
                    }
                    finalPmv = finalPmv / allweight;  // 计算加权平均值
                } else {
                    // 情况3.2：否则累加所有 PMV，并取平均值
                    for (Person person : people) {
                        finalPmv += person.getPMV();
                    }
                    finalPmv = finalPmv / 3;  // 总人数为3
                }
                break;

            default:
                throw new IllegalArgumentException("无效的 num 参数值: " + num);
        }

        return finalPmv;
    }

    public double getFinalPmv(int temp,int num, List<Person> people){
        getPMV(people,temp);
        double PMV = getMulPmv(num,people);
        return PMV;
    }


    public int getTemp(double targetPMV,int num, List<Person> people) {
        double dif;
        int targetTemp = 20; // 初始目标温度
        double minDiff = Double.MAX_VALUE; // 初始化最小差值

        // 遍历 21 到 27 的温度范围
        for (int i = 21; i <= 27; i++) {
            // 这里需要根据当前温度计算 PMV 值（假设有 calculatePMV 方法）
            double tempPMV = getFinalPmv(i,num, people);

            dif = Math.abs(tempPMV - targetPMV); // 计算当前 PMV 与目标 PMV 的差值

            // 如果当前差值小于最小差值，更新目标温度和最小差值
            if (dif < minDiff) {
                minDiff = dif;
                targetTemp = i;
            }
        }
        return targetTemp; // 返回最佳目标温度
    }



}
