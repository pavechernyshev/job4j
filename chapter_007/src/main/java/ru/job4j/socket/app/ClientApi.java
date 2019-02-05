package ru.job4j.socket.app;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ClientApi {
    BufferedReader in;
    PrintWriter out;

    public ClientApi(BufferedReader in, PrintWriter out) {
        this.in = in;
        this.out = out;
    }

    public void goUp() {

    }

    public void GetCurDirContent() {

    }

    public void GoToDir() {

    }

    public void GetFile() {

    }

    public void LoadFile() {

    }

    private ApiResult sendToServer(ApiQuery apiQuery) throws IOException, ParseException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("METHOD", apiQuery.getMethodName());
        jsonObject.put("CONTENT", apiQuery.getParams());
        out.println(jsonObject.toJSONString());
        String jsonAnswer = in.readLine();
        JSONObject jsonResult = (JSONObject) JSONValue.parseWithException(jsonAnswer);
        boolean success = (boolean) jsonResult.get("SUCCESS");
        String mess = (String) jsonResult.get("MESS");
        String content = (String) jsonResult.get("CONTENT");
        ApiResult apiResult = new ApiResult(success, mess, content);
        return apiResult;
    }
}
