package ru.job4j.socket.bot;

import com.google.common.base.Joiner;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientTest {
    private final String ln = System.lineSeparator();

    @Test
    public void whenExitThenExit() throws IOException {
        testInputAndExpected("пока", ln, "");
    }

    @Test
    public void whenExitOnSecondStepThenShowAndExit() throws IOException {
        testInputAndExpected(
                Joiner.on(ln).join("привет", "пока"),
                Joiner.on(ln).join("Hello, dear friend, I'm a oracle.", "", "", ""),
                Joiner.on(ln).join("Hello, dear friend, I'm a oracle.", ""));
    }

    public void testInputAndExpected(String consoleInput, String serverInput, String expected) throws IOException {
        Socket socket = mock(Socket.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(serverInput.getBytes());
        ByteArrayOutputStream consoleOutputStream = new ByteArrayOutputStream();
        PrintStream consolePrintStream = new PrintStream(consoleOutputStream);
        System.setOut(consolePrintStream);
        ByteArrayInputStream consoleInputStream = new ByteArrayInputStream(consoleInput.getBytes());
        System.setIn(consoleInputStream);
        when(socket.getInputStream()).thenReturn(inputStream);
        when(socket.getOutputStream()).thenReturn(outputStream);
        Client client = new Client();
        client.startup(socket);
        assertThat(consoleOutputStream.toString(), is(expected));
    }
}