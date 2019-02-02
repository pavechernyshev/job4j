package ru.job4j.socket.bot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        int port = 5000;
        Server server = new Server();
        Socket socket = new ServerSocket(port).accept();
        server.startup(socket);
    }

    public void startup(Socket socket) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            String ask = null;
            do {
                System.out.println("wait command ...");
                ask = in.readLine();
                System.out.println(ask);
                if ("привет".equals(ask)) {
                    out.println("Hello, dear friend, I'm a oracle.");
                    out.println();
                } else if ("как дела?".equals(ask)) {
                    out.println("У меня все отлично!!");
                    out.println("А у Вас как?");
                    out.println();
                } else {
                    out.println();
                }
            } while (!"пока".equals(ask));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
