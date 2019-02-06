package ru.job4j.socket.app;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;

public class InputJsonReader implements Input {
    private BufferedReader bufferedReader;

    InputJsonReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    @Override
    public ApiQuery nextLint() {
        ApiQuery apiQuery = new ApiQuery("", "");
        try {
            String jsonLine = bufferedReader.readLine();
            JSONObject jsonObject = (JSONObject) JSONValue.parseWithException(jsonLine);
            apiQuery = new ApiQuery(jsonObject.get("METHOD").toString(),jsonObject.get("CONTENT").toString());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return apiQuery;
    }
}
