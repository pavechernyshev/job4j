package ru.job4j.socket.app;

import org.json.simple.JSONObject;

import java.io.PrintWriter;

public class OutputJsonWriter implements Output {
    private PrintWriter printWriter;

    OutputJsonWriter(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }

    @Override
    public void answer(ApiResult apiResult) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("SUCCESS", apiResult.isSuccess());
        jsonObject.put("MESS", apiResult.getMess());
        jsonObject.put("CONTENT", apiResult.getContent());
        String jsonString = jsonObject.toJSONString();
        printWriter.println(jsonString);
    }
}
