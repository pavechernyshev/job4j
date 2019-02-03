package ru.job4j.socket.app;

public class ApiQuery {
    private String methodName;
    private String content;

    public ApiQuery(String methodName, String content) {
        this.methodName = methodName;
        this.content = content;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getContent() {
        return content;
    }
}
