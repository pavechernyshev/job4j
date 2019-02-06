package ru.job4j.socket.app;

import com.google.common.base.Joiner;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

public class ClientApi {
    BufferedReader in;
    PrintWriter out;

    public ClientApi(BufferedReader in, PrintWriter out) {
        this.in = in;
        this.out = out;
    }

    public String goUp() throws IOException, ParseException {
        ApiQuery apiQuery = new ApiQuery("goUp", "");
        ApiResult apiResult = sendToServer(apiQuery);
        return apiResult.getMess();
    }

    public String getCurDirContent() throws IOException, ParseException {
        ApiQuery apiQuery = new ApiQuery("getCurDirContent", "");
        ApiResult apiResult = sendToServer(apiQuery);
        return apiResult.getContent().length() > 0 ? apiResult.getContent() : apiResult.getMess();
    }

    public String goToDir(String dirName) throws IOException, ParseException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("DIR_NAME", dirName);
        ApiQuery apiQuery = new ApiQuery("goToDir", jsonObject.toJSONString());
        ApiResult apiResult = sendToServer(apiQuery);
        return apiResult.getMess();
    }

    public String getFile(String fileName, String downloadFullFileName) throws IOException, ParseException {
        File targetFile = new File(downloadFullFileName);
        ApiResult apiResult = new ApiResult(false, "", "");
        try {
            if (targetFile.createNewFile()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("FILE_NAME", fileName);
                apiResult = sendToServer(new ApiQuery("getFile", jsonObject.toJSONString()));
                if (apiResult.isSuccess()) {
                    byte[] fileContent = Base64.getDecoder().decode(apiResult.getContent());
                    Scanner scanner = new Scanner(new ByteArrayInputStream(fileContent));
                    FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
                    while (scanner.hasNextLine()) {
                        String lineWithSeparator = String.format("%s%s",scanner.nextLine(), System.lineSeparator());
                        fileOutputStream.write(lineWithSeparator.getBytes(Charset.forName("UTF-8")));
                    }
                    fileOutputStream.close();
                    scanner.close();

                } else {
                    targetFile.deleteOnExit();
                }
            } else {
                apiResult = new ApiResult(false, "не удалось создать файл по указанному пути", "");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String mess = apiResult.getMess();
        if (apiResult.isSuccess()) {
            mess = "Файл успешно скачен";
        }
        return mess;
    }

    public void loadFile(String fullFileName) {

    }

    public void stopServer() throws IOException, ParseException {
        sendToServer(new ApiQuery("exit", ""));
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
