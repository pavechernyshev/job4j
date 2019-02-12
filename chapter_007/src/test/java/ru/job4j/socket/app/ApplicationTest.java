package ru.job4j.socket.app;

import com.google.common.base.Joiner;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.*;
import java.net.Socket;
import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ApplicationTest {
    private String ln = System.lineSeparator();
    private String fs = File.separator;
    List<ApiQuery> apiQueries = new LinkedList<>();
    List<ApiResult> apiResults = new LinkedList<>();

    private void addExitToLists() {
        apiQueries.add(new ApiQuery("exit", ""));
        apiResults.add(new ApiResult(true, "сервер успешно остановлен", ""));
    }

    @Test
    public void whenExitThenExit() throws IOException, ParseException {
        ApiQuery apiQuery = new ApiQuery("exit", "");
        ApiResult apiResultExpected = new ApiResult(true, "сервер успешно остановлен", "");
        apiQueries.add(apiQuery);
        apiResults.add(apiResultExpected);
        testInputAndExpected(apiQueries, apiResults);
    }

    @Test
    public void whenGoToDir() throws IOException, ParseException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("DIR_NAME", "src");
        ApiQuery apiQuery = new ApiQuery("goToDir", jsonObject.toJSONString());
        ApiResult apiResultExpected = new ApiResult(true, "перход выполнен успешно", "");
        apiQueries.add(apiQuery);
        apiResults.add(apiResultExpected);
        addExitToLists();
        testInputAndExpected(apiQueries, apiResults);
    }

    @Test
    public void whenGetCurDirContent() throws IOException, ParseException {
        ApiQuery apiQuery = new ApiQuery("getCurDirContent", "");
        apiQueries.add(apiQuery);
        addExitToLists();
        List<ApiResult> apiResults = getApiResults(apiQueries);
        assertThat(apiResults.size(), is(2));
        assertThat(apiResults.get(1).getMess(), is("сервер успешно остановлен"));
        ApiResult apiResult = apiResults.get(0);
        List<String> dirContent = new LinkedList<>();
        Scanner scanner = new Scanner(apiResult.getContent());
        while (scanner.hasNextLine()) {
            dirContent.add(scanner.nextLine());
        }
        assertEquals(3, dirContent.size());
        assertTrue(dirContent.contains("file: pom.xml"));
        assertTrue(dirContent.contains("dir: src"));
        assertTrue(dirContent.contains("dir: target"));
    }

    @Test
    public void whenTestGoUp() throws IOException, ParseException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("DIR_NAME", "src");
        apiQueries.add(new ApiQuery("goToDir", jsonObject.toJSONString()));
        jsonObject.clear();
        jsonObject.put("DIR_NAME", "main");
        apiQueries.add(new ApiQuery("goToDir", jsonObject.toJSONString()));
        jsonObject.clear();
        jsonObject.put("DIR_NAME", "java");
        apiQueries.add(new ApiQuery("goToDir", jsonObject.toJSONString()));
        apiQueries.add(new ApiQuery("goUp", ""));
        apiQueries.add(new ApiQuery("goUp", ""));
        StringBuilder expectedContent = new StringBuilder();
        expectedContent.append("dir: main").append(ln).append("dir: test").append(ln);
        apiQueries.add(new ApiQuery("getCurDirContent", ""));
        apiResults.add(new ApiResult(true, "содержимое каталога получено", expectedContent.toString()));
        addExitToLists();
        List<ApiResult> serverAnswers = getApiResults(apiQueries);
        assertEquals(serverAnswers.size(), 7);
        assertThat(serverAnswers.get(0), is(new ApiResult(true, "перход выполнен успешно", "")));
        assertThat(serverAnswers.get(1), is(new ApiResult(true, "перход выполнен успешно", "")));
        assertThat(serverAnswers.get(2), is(new ApiResult(true, "перход выполнен успешно", "")));
        assertThat(serverAnswers.get(3), is(new ApiResult(true, "пререход на верх выполнен успешно.", "")));
        assertThat(serverAnswers.get(4), is(new ApiResult(true, "пререход на верх выполнен успешно.", "")));
        ApiResult contentResult = serverAnswers.get(5);
        assertThat(contentResult.getMess(), is("содержимое каталога получено"));
        List<String> dirContent = new LinkedList<>();
        Scanner scanner = new Scanner(contentResult.getContent());
        while (scanner.hasNextLine()) {
            dirContent.add(scanner.nextLine());
        }
        assertEquals(2, dirContent.size());
        assertTrue(dirContent.contains("dir: main"));
        assertTrue(dirContent.contains("dir: test"));
        assertThat(serverAnswers.get(6), is(new ApiResult(true, "сервер успешно остановлен", "")));

    }

    @Test
    public void whenDownloadFile() throws IOException, ParseException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("FILE_NAME", "pom.xml");
        apiQueries.add(new ApiQuery("getFile", jsonObject.toJSONString()));
        File file = new File(Joiner.on(fs).join(System.getProperty("user.dir"), "pom.xml"));
        Scanner scanner = new Scanner(new FileInputStream(file));
        StringBuilder fileContentStrBuilder = new StringBuilder();
        while (scanner.hasNextLine()) {
            String lineWithSeparator = String.format("%s%s", scanner.nextLine(), System.lineSeparator());
            fileContentStrBuilder.append(lineWithSeparator);
        }
        String encodeFileContent = Base64.getEncoder().encodeToString(fileContentStrBuilder.toString().getBytes());
        apiResults.add(new ApiResult(true, "файл успешно отдан", encodeFileContent));
        addExitToLists();
        testInputAndExpected(apiQueries, apiResults);
    }

    @Test
    public void whenLoadFile() throws IOException, ParseException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("FILE_NAME", "test.txt");
        File file = new File(Joiner.on(fs).join(System.getProperty("user.dir"), "src", "test", "java", "ru", "job4j", "socket", "app", "test.txt"));
        Scanner scanner = new Scanner(new FileInputStream(file));
        StringBuilder fileContentStrBuilder = new StringBuilder();
        while (scanner.hasNextLine()) {
            String lineWithSeparator = String.format("%s%s", scanner.nextLine(), System.lineSeparator());
            fileContentStrBuilder.append(lineWithSeparator);
        }
        String encodeFileContent = Base64.getEncoder().encodeToString(fileContentStrBuilder.toString().getBytes());
        jsonObject.put("FILE_BASE64_CONTENT", encodeFileContent);
        apiQueries.add(new ApiQuery("loadFile", jsonObject.toJSONString()));
        apiResults.add(new ApiResult(true, "Файл успешно загружен", ""));
        addExitToLists();
        testInputAndExpected(apiQueries, apiResults);
        File loadedFile = new File(Joiner.on(fs).join(System.getProperty("user.dir"), "test.txt"));
        assertTrue(loadedFile.exists());
        scanner = new Scanner(new FileInputStream(loadedFile));
        StringBuilder loadedFileContentStrBuilder = new StringBuilder();
        while (scanner.hasNextLine()) {
            String lineWithSeparator = String.format("%s%s", scanner.nextLine(), System.lineSeparator());
            loadedFileContentStrBuilder.append(lineWithSeparator);
        }
        scanner.close();
        assertThat(loadedFileContentStrBuilder.toString(), is(fileContentStrBuilder.toString()));
        assertTrue(loadedFile.delete());

    }

    public void testInputAndExpected(List<ApiQuery> apiQueryList, List<ApiResult> expectedList) throws IOException, ParseException {
        List<ApiResult> apiResults = getApiResults(apiQueryList);
        assertEquals(expectedList.size(), apiResults.size());
        assertThat(apiResults, is(expectedList));
    }

    public List<ApiResult> getApiResults(List<ApiQuery> apiQueryList) throws IOException, ParseException {
        Socket socket = mock(Socket.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        StringBuilder stringBuilder = new StringBuilder();
        for (ApiQuery apiQuery: apiQueryList) {
            stringBuilder.append(apiQuery.toJsonString()).append(System.lineSeparator());
        }
        ByteArrayInputStream inputStream = new ByteArrayInputStream(stringBuilder.toString().getBytes());
        when(socket.getInputStream()).thenReturn(inputStream);
        when(socket.getOutputStream()).thenReturn(outputStream);
        Application server = new Application(socket);
        server.startup();
        String serverAnswer = outputStream.toString();
        Scanner scanner = new Scanner(serverAnswer);
        List<ApiResult> apiResults = new LinkedList<>();
        while (scanner.hasNextLine()) {
            JSONObject jsonObject = (JSONObject) JSONValue.parseWithException(scanner.nextLine());
            ApiResult apiResult = new ApiResult(
                    jsonObject.get("SUCCESS").equals(true),
                    jsonObject.get("MESS").toString(),
                    jsonObject.get("CONTENT").toString()
            );
            apiResults.add(apiResult);
        }
        return apiResults;
    }
}