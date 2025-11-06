package com.example.qmx.server;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class DataSave {

    private static final String FILE_PATH = "PMVData.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static class DataState {
        public int[] acState;
        public int pmvFlag;
        public double pmvThreshold;
        public int[] artificialAdjustData;

        @JsonCreator
        public DataState(
                @JsonProperty("acState") int[] acState,
                @JsonProperty("pmvFlag") int pmvFlag,
                @JsonProperty("pmvThreshold") double pmvThreshold,
                @JsonProperty("artificialAdjustData") int[] artificialAdjustData) {
            this.acState = acState;
            this.pmvFlag = pmvFlag;
            this.pmvThreshold = pmvThreshold;
            this.artificialAdjustData = artificialAdjustData;
        }

        // 无参构造函数，供 Jackson 反序列化使用
        public DataState() {
        }
    }

    public boolean isFile(){
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return false;
        }else {
            return true;
        }
    }


    public void saveState(int[] acState, int pmvFlag, double pmvThreshold,int[] artificialAdjustData) throws IOException {
        DataState state = new DataState(acState, pmvFlag, pmvThreshold,artificialAdjustData);
        File file = new File(FILE_PATH);
                objectMapper.writeValue(file, state);
    }

    public DataState readState() throws IOException {
        return objectMapper.readValue(new File(FILE_PATH), DataState.class);
    }
}
