package ru.job4j.socket.app;

import com.google.common.base.Joiner;
import org.json.simple.JSONObject;
import org.junit.Test;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientTest {
    private String ln = System.lineSeparator();
    private String fs = File.separator;
    private String curDir = System.getProperty("user.dir");

    @Test
    public void whenExitThenExit() throws IOException {
        List<String> userEnterPhrases = new LinkedList<>();
        userEnterPhrases.add("6");
        List<String> serverAnswers = new LinkedList<>();
        serverAnswers.add(new ApiResult(true, "сервер успешно остановлен", "").toJsonString());
        List<String> expectedQueries = new LinkedList<>();
        expectedQueries.add(new ApiQuery("exit", "").toJsonString());
        whenExpected(userEnterPhrases, serverAnswers, expectedQueries);
    }

    @Test
    public void whenShowMenuThenExit() throws IOException {
        List<String> userEnterPhrases = new LinkedList<>();
        userEnterPhrases.add("5");
        userEnterPhrases.add("6");
        List<String> serverAnswers = new LinkedList<>();
        serverAnswers.add(new ApiResult(true, "", "").toJsonString());
        List<String> expectedQueries = new LinkedList<>();
        expectedQueries.add(new ApiQuery("exit", "").toJsonString());
        whenExpected(userEnterPhrases, serverAnswers, expectedQueries);
    }

    @Test
    public void whenGoToDirThenExit() throws IOException {
        List<String> userEnterPhrases = new LinkedList<>();
        userEnterPhrases.add("2");
        userEnterPhrases.add("src");
        userEnterPhrases.add("6");
        List<String> serverAnswers = new LinkedList<>();
        serverAnswers.add(new ApiResult(true, "", "").toJsonString());
        serverAnswers.add(new ApiResult(true, "", "").toJsonString());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("DIR_NAME", "src");
        List<String> expectedQueries = new LinkedList<>();
        expectedQueries.add(new ApiQuery("goToDir", jsonObject.toJSONString()).toJsonString());
        expectedQueries.add(new ApiQuery("exit", "").toJsonString());
        whenExpected(userEnterPhrases, serverAnswers, expectedQueries);
    }

    @Test
    public void whenGoToDirAndGoUpAndPrintDirThenExit() throws IOException {
        List<String> userEnterPhrases = new LinkedList<>();
        userEnterPhrases.add("2");
        userEnterPhrases.add("src");
        userEnterPhrases.add("0");
        userEnterPhrases.add("1");
        userEnterPhrases.add("6");
        List<String> serverAnswers = new LinkedList<>();
        serverAnswers.add(new ApiResult(true, "", "").toJsonString());
        serverAnswers.add(new ApiResult(true, "", "").toJsonString());
        serverAnswers.add(new ApiResult(true, "", "").toJsonString());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("DIR_NAME", "src");
        List<String> expectedQueries = new LinkedList<>();
        expectedQueries.add(new ApiQuery("goToDir", jsonObject.toJSONString()).toJsonString());
        expectedQueries.add(new ApiQuery("goUp", "").toJsonString());
        expectedQueries.add(new ApiQuery("getCurDirContent", "").toJsonString());
        expectedQueries.add(new ApiQuery("exit", "").toJsonString());
        whenExpected(userEnterPhrases, serverAnswers, expectedQueries);
    }

    @Test
    public void whenFileGet() throws IOException {
        List<String> userEnterPhrases = new LinkedList<>();
        String fileName = "pom.xml";
        userEnterPhrases.add("3");
        userEnterPhrases.add(fileName);
        userEnterPhrases.add("6");
        List<String> serverAnswers = new LinkedList<>();
        serverAnswers.add(new ApiResult(true, "", "").toJsonString());
        serverAnswers.add(new ApiResult(true, "", "").toJsonString());
        List<String> expectedQueries = new LinkedList<>();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("FILE_NAME", fileName);
        expectedQueries.add(new ApiQuery("getFile", jsonObject.toJSONString()).toJsonString());
        expectedQueries.add(new ApiQuery("exit", "").toJsonString());
        whenExpected(userEnterPhrases, serverAnswers, expectedQueries);
        String pathToDownloadedFile = Joiner.on(fs).join(curDir, "src", "main", "java", "ru", "job4j", "socket", "app", "downloads", fileName);
        File downloadedFile = new File(pathToDownloadedFile);
        assertTrue(downloadedFile.exists());
        assertTrue(downloadedFile.delete());
    }

    public void whenExpected(List<String> userEnterPhrases, List<String> serverAnswers, List<String> expectedQueries) throws IOException {
        String userEnterPhrasesOneString = Joiner.on(ln).join(userEnterPhrases);
        System.setIn(new ByteArrayInputStream(userEnterPhrasesOneString.getBytes()));
        String serverAnswersOneString = Joiner.on(ln).join(serverAnswers);
        ByteArrayInputStream socketInputStream = new ByteArrayInputStream(serverAnswersOneString.getBytes());
        ByteArrayOutputStream socketOutputStream = new ByteArrayOutputStream();
        ByteArrayOutputStream consoleOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(consoleOut));
        Socket socket = mock(Socket.class);
        when(socket.getInputStream()).thenReturn(socketInputStream);
        when(socket.getOutputStream()).thenReturn(socketOutputStream);
        Client client = new Client();
        client.startup(socket);
        Scanner scannerSocketOutput = new Scanner(socketOutputStream.toString());
        for (String expectedQuery: expectedQueries) {
            assertTrue(scannerSocketOutput.hasNext());
            assertThat(scannerSocketOutput.nextLine(), is(expectedQuery));
        }
    }
}