package ru.job4j.socket.bot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        String ip = "127.0.0.1";
        int port = 5000;
        Socket socket = new Socket(InetAddress.getByName(ip), port);
        Client client = new Client();
        client.startup(socket);
    }

    public void startup(Socket socket) {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner console = new Scanner(System.in);
            String userMess = null;
            do {
                userMess = console.nextLine();
                out.println(userMess);
                String str = in.readLine();
                while (!str.isEmpty()) {
                    System.out.println(str);
                    str = in.readLine();
                }
            } while (!"пока".equals(userMess));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
