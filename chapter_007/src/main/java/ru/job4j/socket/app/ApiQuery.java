package ru.job4j.socket.app;

import org.json.simple.JSONObject;

public class ApiQuery {
    private String methodName;
    private String params;

    public ApiQuery(String methodName, String params) {
        this.methodName = methodName;
        this.params = params;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getParams() {
        return params;
    }

    public String toJsonString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("METHOD", methodName);
        jsonObject.put("CONTENT", params);
        return jsonObject.toJSONString();
    }
}
