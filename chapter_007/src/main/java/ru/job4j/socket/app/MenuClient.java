package ru.job4j.socket.app;

import com.google.common.base.Joiner;
import com.sun.demo.jvmti.hprof.Tracker;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MenuClient {

    private InputClient input; //Композиция
    private ClientApi clientApi; //Композиция
    private ArrayList<UserAction> userActions = new ArrayList<>(); //Агрегация
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

    private void setExit(boolean exit) {
        this.exit = exit;
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
            String mess = null;
            try {
                mess = clientApi.goUp();
                System.out.println(mess);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
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
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
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
            if (!dirName.contains(" ")){
                try {
                    String mess = clientApi.goToDir(dirName);
                    System.out.println(mess);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
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
            String targetFileDir = input.ask("введите полный путь к дириктории, куда будет сохранен файл");
            File dir = new File(targetFileDir);
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

        }

        @Override
        public String info() {
            return String.format("%s - чтобы загрузить файл", this.key());
        }
    }

    private class Exit implements UserAction {

        @Override
        public int key() {
            return 5;
        }

        @Override
        public void execute(InputClient input, ClientApi clientApi) {
            setExit(true);
        }

        @Override
        public String info() {
            return String.format("%s - чтобы выйти из программы", this.key());
        }
    }
}
