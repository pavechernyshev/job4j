package ru.job4j.socket.app;

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
}
