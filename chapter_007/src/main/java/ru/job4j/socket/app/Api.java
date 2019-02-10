package ru.job4j.socket.app;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

public class Api {

    private final String ln = System.lineSeparator();
    private ArrayList<ApiAction> apiActions = new ArrayList<>();
    private Output output;
    private FileInspector fileInspector;
    private ApiQuery lastApiQuery;
    private boolean exit = false;

    public boolean isExit() {
        return exit;
    }

    public void exit() {
        exit = true;
    }

    Api(Output output, FileInspector fileInspector) {
        this.output = output;
        this.fileInspector = fileInspector;
        apiActions.add(new GoUp());
        apiActions.add(new GetCurDirContent());
        apiActions.add(new GoToDir());
        apiActions.add(new GetFile());
        apiActions.add(new LoadFile());
        apiActions.add(new Exit());
    }

    public void select(ApiQuery apiQuery) {
        lastApiQuery = apiQuery;
        for (ApiAction apiAction: apiActions) {
            if (apiAction != null && apiAction.methodName().equals(apiQuery.getMethodName())) {
                apiAction.execute(this.output, this.fileInspector);
            }
        }
    }

    private class Exit implements ApiAction {

        @Override
        public String methodName() {
            return "exit";
        }

        @Override
        public void execute(Output output, FileInspector fileInspector) {
            output.answer(new ApiResult(true, "сервер успешно остановлен", ""));
            exit();
        }

        @Override
        public String info() {
            return "метод exit останавливает сервер";
        }
    }

    private class GoUp implements ApiAction {

        @Override
        public String methodName() {
            return "goUp";
        }

        @Override
        public void execute(Output output, FileInspector fileInspector) {
            boolean res = fileInspector.goUp();
            String mess;
            if (res) {
                mess = "пререход на верх выполнен успешно.";
            } else {
                mess = "что-то пошло не так";
            }
            output.answer(new ApiResult(res, mess, ""));
        }

        @Override
        public String info() {
            return "метод goUp() переносит в выше лежаший каталог";
        }
    }

    private class GetCurDirContent implements ApiAction {

        @Override
        public String methodName() {
            return "getCurDirContent";
        }

        @Override
        public void execute(Output output, FileInspector fileInspector) {
            List<File> fileList = fileInspector.getCurDirContent();
            String mess;
            if (fileList.size() > 0) {
                mess = "содержимое каталога получено";
            } else {
                mess = "каталог пуст";
            }
            StringBuilder content = new StringBuilder();
            for (File file: fileList) {
                if (file.isDirectory()) {
                    content.append("dir: ");
                } else if (file.isFile()) {
                    content.append("file: ");
                } else {
                    continue;
                }
                content.append(file.getName());
                content.append(ln);
            }
            output.answer(new ApiResult(true, mess, content.toString()));
        }

        @Override
        public String info() {
            return "метод getCurDirContent() возвращает список директорий и файлов текущей директории";
        }
    }

    private class GoToDir implements ApiAction {

        @Override
        public String methodName() {
            return "goToDir";
        }

        @Override
        public void execute(Output output, FileInspector fileInspector) {
            boolean success = false;
            String jsonParams = lastApiQuery.getParams();
            String mess = "";
            String content = "";
            try {
                JSONObject jsonObject = (JSONObject) JSONValue.parseWithException(jsonParams);
                String potentialDirName = jsonObject.get("DIR_NAME").toString();
                if (potentialDirName.length() == 0) {
                    mess = "название директории не введено";
                } else if (potentialDirName.contains(" ")) {
                    mess = "название директории не должно содержать пробелов";
                } else {
                    success = fileInspector.goToDir(potentialDirName);
                    if (success) {
                        mess = "перход выполнен успешно";
                    } else {
                        mess = "не удалось перейти";
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            output.answer(new ApiResult(success, mess, content));
        }

        @Override
        public String info() {
            return "метод goToDir принимает один параметр DIR_NAME- имя директории";
        }
    }

    private class GetFile implements ApiAction {

        @Override
        public String methodName() {
            return "getFile";
        }

        @Override
        public void execute(Output output, FileInspector fileInspector) {
            boolean success = false;
            String mess = "";
            String content = "";
            String jsonParams = lastApiQuery.getParams();
            try {
                JSONObject jsonObject = (JSONObject) JSONValue.parseWithException(jsonParams);
                String fullFileName = jsonObject.get("FILE_NAME").toString();
                if (fullFileName.length() == 0) {
                    mess = "название файла не введено";
                } else {
                    File file = fileInspector.getFile(fullFileName);
                    if (file != null) {
                        Scanner scanner = new Scanner(new FileInputStream(file));
                        StringBuilder fileContentStrBuilder = new StringBuilder();
                        while (scanner.hasNextLine()) {
                            String lineWithSeparator = String.format("%s%s", scanner.nextLine(), System.lineSeparator());
                            fileContentStrBuilder.append(lineWithSeparator);
                        }
                        String encodedFileContent = Base64.getEncoder().encodeToString(fileContentStrBuilder.toString().getBytes());
                        mess = "файл успешно отдан";
                        content = encodedFileContent;
                        success = true;
                    }
                }
            } catch (ParseException | FileNotFoundException e) {
                e.printStackTrace();
            }
            output.answer(new ApiResult(success, mess, content));
        }

        @Override
        public String info() {
            return "метод getFile принимает параметр FILE_NAME и возвращает содержимое файла в кодировке base64";
        }
    }

    private class LoadFile implements ApiAction {

        @Override
        public String methodName() {
            return "loadFile";
        }

        @Override
        public void execute(Output output, FileInspector fileInspector) {
            boolean success = false;
            String mess = "";
            String content = "";
            String jsonParams = lastApiQuery.getParams();
            try {
                JSONObject jsonObject = (JSONObject) JSONValue.parseWithException(jsonParams);
                String fileName = jsonObject.get("FILE_NAME").toString();
                String fileBase64Content = jsonObject.get("FILE_BASE64_CONTENT").toString();
                if (fileName.length() == 0) {
                    mess = "название файла не введено";
                } else {
                    if (fileBase64Content.length() > 0) {
                        byte[] fileContent = Base64.getDecoder().decode(fileBase64Content);
                        InputStream inputStream = new ByteArrayInputStream(fileContent);
                        if (fileInspector.loadFile(fileName, inputStream)) {
                            success = true;
                            mess = "Файл успешно загружен";
                        }
                    } else {
                        mess = "содержимое файла не получено";
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            output.answer(new ApiResult(success, mess, content));
        }

        @Override
        public String info() {
            return "метод загружает файл в текущую директорию. принимает два параметра FILE_NAME и FILE_BASE64_CONTENT";
        }
    }
}
