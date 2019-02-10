package ru.job4j.socket.app;

import com.google.common.base.Joiner;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;

public class MenuClient {

    private InputClient input; //Композиция
    private ClientApi clientApi; //Композиция
    private ArrayList<UserAction> userActions = new ArrayList<>(); //Агрегация
    private final String pathToProject = System.getProperty("user.dir");
    private boolean exit = false;


    public MenuClient(InputClient input, ClientApi clientApi) {
        this.input = input;
        this.clientApi = clientApi;
    }

    public void select(int key) {
        boolean found = false;
        for (UserAction action: this.userActions) {
            if (action != null && action.key() == key) {
                action.execute(this.input, this.clientApi);
                found = true;
            }
        }
        if (!found) {
            printNotFoundMenuItemMess();
        }
    }

    public int[] getRange() {
        int[] range = new int[this.userActions.size()];
        int count = 0;
        for (UserAction action: this.userActions) {
            range[count++] = action.key();
        }
        return range;
    }

    public void fillActions() {
        this.userActions.add(new GoUp());
        this.userActions.add(new GetCurDirContent());
        this.userActions.add(new GoToDir());
        this.userActions.add(new GetFile());
        this.userActions.add(new LoadFile());
        this.userActions.add(new ShowMenu());
        this.userActions.add(new Exit());
    }

    public void show() {
        printMenuHead();
        for (UserAction action: this.userActions) {
            if (action != null) {
                System.out.println(action.info());
            }
        }
    }

    public boolean isExit() {
        return exit;
    }

    private void setExit() {
        this.exit = true;
    }

    private void printMenuHead() {
        print("Меню.");
    }

    private void printNotFoundMenuItemMess() {
        print("Пункт меню не найден");
    }

    private void print(String s) {
        Consumer<String> consumer = System.out::println;
        consumer.accept(s);
    }


    private class GoUp implements UserAction {

        @Override
        public int key() {
            return 0;
        }

        @Override
        public void execute(InputClient input, ClientApi clientApi) {
            String mess;
            try {
                mess = clientApi.goUp();
                System.out.println(mess);
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String info() {
            return String.format("%s - чтобы перейти на директорию выше", this.key());
        }
    }


    private class GetCurDirContent implements UserAction {
        @Override
        public int key() {
            return 1;
        }

        @Override
        public void execute(InputClient input, ClientApi clientApi) {
            try {
                String list = clientApi.getCurDirContent();
                System.out.println(list);
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String info() {
            return String.format("%s - чтобы получить список содержимого текущей директории", this.key());
        }
    }

    private class GoToDir implements UserAction {
        @Override
        public int key() {
            return 2;
        }

        @Override
        public void execute(InputClient input, ClientApi clientApi) {
            String dirName = input.ask("введите имя директории:");
            if (!dirName.contains(" ")) {
                try {
                    String mess = clientApi.goToDir(dirName);
                    System.out.println(mess);
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Имя директории не должно содержать пробелов");
            }
        }

        @Override
        public String info() {
            return String.format("%s - чтобы перейти на указанную директорию", this.key());
        }
    }

    private class GetFile implements UserAction {
        @Override
        public int key() {
            return 3;
        }

        @Override
        public void execute(InputClient input, ClientApi clientApi) {
            String fileName = input.ask("введите имя файла:");
            fileName = fileName.trim();
            final String pathToDownloadDir = Joiner.on("\\").join(
                    System.getProperty("user.dir"),
                    "src\\main\\java\\ru\\job4j\\socket\\app\\downloads"
            );
            File dir = new File(pathToDownloadDir);
            if (dir.exists() && dir.isDirectory()) {
                String fullFilePath = Joiner.on("\\").join(dir.getPath(), fileName);
                try {
                    String mess = clientApi.getFile(fileName, fullFilePath);
                    System.out.println(mess);
                } catch (IOException | ParseException e) {
                    System.out.println("файл не был скачен");
                    e.printStackTrace();
                }
            } else {
                System.out.println("Не удалось найти директорию с таким именем");
            }
        }

        @Override
        public String info() {
            return String.format("%s - чтобы скачать файл", this.key());
        }
    }

    private class LoadFile implements UserAction {
        @Override
        public int key() {
            return 4;
        }

        @Override
        public void execute(InputClient input, ClientApi clientApi) {
            String filePath = input.ask("Введите путь к файлу:");
            if (filePath.length() > 0) {
                String fullFileName;
                if (filePath.substring(0, 1).equals("\\")) {
                    fullFileName = String.format("%s%s", pathToProject, filePath);
                } else {
                    fullFileName = Joiner.on("\\").join(pathToProject, filePath);
                }
                try {
                    ApiResult apiResult = clientApi.loadFile(fullFileName);
                    System.out.println(apiResult.getMess());
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Путь к файлу не введен");
            }
        }

        @Override
        public String info() {
            return String.format("%s - чтобы загрузить файл", this.key());
        }
    }

    private class ShowMenu implements UserAction {

        @Override
        public int key() {
            return 5;
        }

        @Override
        public void execute(InputClient input, ClientApi clientApi) {
            show();
        }

        @Override
        public String info() {
            return String.format("%s - чтобы показать меню", this.key());
        }
    }

    private class Exit implements UserAction {


        @Override
        public int key() {
            return 6;
        }

        @Override
        public void execute(InputClient input, ClientApi clientApi) {
            setExit();
        }
        @Override
        public String info() {
            return String.format("%s - чтобы выйти из программы", this.key());
        }

    }
}
