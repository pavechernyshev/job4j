package ru.job4j.socket.app;

import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        String ip = Application.Properties.ip;
        int port = Application.Properties.port;
        Socket socket = new Socket(InetAddress.getByName(ip), port);
        Client client = new Client();
        client.startup(socket);
    }

    public void startup(Socket socket) {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ConsoleInput consoleInput = new ConsoleInput();
            ClientApi clientApi = new ClientApi(in, out);
            MenuClient menuClient = new MenuClient(consoleInput, clientApi);
            menuClient.fillActions();
            int[] menuRange = menuClient.getRange();
            while (!menuClient.isExit()) {
                menuClient.show();
                int userInputKey = consoleInput.ask("Введите пункт меню:", menuRange);
                menuClient.select(userInputKey);
            }
            clientApi.stopServer();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
