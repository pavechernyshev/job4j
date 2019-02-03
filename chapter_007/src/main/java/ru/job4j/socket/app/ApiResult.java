package ru.job4j.socket.app;

public class ApiResult {
    private boolean success;
    private String mess;
    private String content;

    public ApiResult(boolean success, String mess, String content) {
        this.success = success;
        this.mess = mess;
        this.content = content;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMess() {
        return mess;
    }

    public String getContent() {
        return content;
    }
}
