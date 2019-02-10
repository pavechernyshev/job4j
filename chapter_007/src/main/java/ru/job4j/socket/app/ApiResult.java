package ru.job4j.socket.app;

import org.json.simple.JSONObject;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ApiResult apiResult = (ApiResult) o;
        return success == apiResult.success
                && Objects.equals(mess, apiResult.mess)
                && Objects.equals(content, apiResult.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, mess, content);
    }

    @Override
    public String toString() {
        return "ApiResult{"
                + "success=" + success
                + ", mess='" + mess + '\''
                + ", content='" + content + '\''
                + '}';
    }

    public String toJsonString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("SUCCESS", success);
        jsonObject.put("MESS", mess);
        jsonObject.put("CONTENT", content);
        return jsonObject.toJSONString();
    }
}
