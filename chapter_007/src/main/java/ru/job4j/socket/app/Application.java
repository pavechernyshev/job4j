package ru.job4j.socket.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Application {
    public static class Properties {
        public final static String IP = "127.0.0.1";
        public final static int PORT = 5006;
    }

    private Socket socket;

    Application(Socket socket) {
        this.socket = socket;
    }

    public void startup() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        FileController fileController = new FileController();
        OutputJsonWriter outputJsonWriter = new OutputJsonWriter(out);
        Api api = new Api(outputJsonWriter, fileController);
        InputJsonReader inputJsonReader = new InputJsonReader(in);
        do {
            ApiQuery apiQuery = inputJsonReader.nextLint();
            api.select(apiQuery);
        } while (!api.isExit());
    }

    public static void main(String[] args) throws IOException {
        Socket socket = new ServerSocket(Properties.PORT).accept();
        Application application = new Application(socket);
        application.startup();
    }

}
