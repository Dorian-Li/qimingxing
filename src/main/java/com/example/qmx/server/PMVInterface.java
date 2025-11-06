package com.example.qmx.server;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public interface PMVInterface extends Library {
    PMVInterface MY_DLL = Native.load("D:\\启明星项目\\后端\\qmx\\src\\main\\java\\com\\example\\qmx\\dll\\PMVcalculate.dll", PMVInterface.class);

    double calculatePMV(int t, int age, int gendar, int ACDataModel);

     Pointer getACParameters(int[] evironmentData, int[] physiologicalData,
                            int personCounts, int[] ACData, int[] setACData,
                            double PMVLimit, int[] artificialAdjustData);

}

