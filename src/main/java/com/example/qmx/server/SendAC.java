package com.example.qmx.server;

import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
public class SendAC {
    public void sendData(String targetURL, int temperature) {
        HttpURLConnection connection = null;
        try {
            // 创建 URL 对象
            URL url = new URL(targetURL);
            // 打开连接
            connection = (HttpURLConnection) url.openConnection();

            // 设置请求方法为 POST
            connection.setRequestMethod("POST");
            // 允许写入数据
            connection.setDoOutput(true);
            // 设置请求头
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");

            // 动态生成 JSON 数据
            String jsonData = "{"
                    + "\"reqId\": \"93874446448844764\","
                    + "\"clientId\": \"f6f1ec55481b5dc314bd6555e4d3d3bb\","
                    + "\"stamp\": \"20181201160518000\","
                    + "\"payload\": ["
                    + "    {"
                    + "        \"skill\": \"安其居\","
                    + "        \"intent\": \"设置温度\","
                    + "        \"location\": \"儿童房\","
                    + "        \"sn\": \"000016111MSGWG131C8776870CC30000\","//000016111MSGWG131C8776870CC30000//000016111MSGWG04FCB69D2981660000
                    + "        \"devName\": \"空调儿童房\","
                    + "        \"params\": {"
                    + "            \"value\": " + temperature
                    + "        }"
                    + "    }"
                    + "]"
                    + "}";

            // 发送数据
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonData.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // 检查响应码
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Data sent successfully.");
            } else {
                System.out.println("Failed to send data. Response code: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
