package ru.job4j.socket.bot;

import com.google.common.base.Joiner;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServerTest {
    private final String ln = System.lineSeparator();

    @Test
    public void whenExitThenExit() throws IOException {
        testInputAndExpected("пока", ln);
    }

    @Test
    public void whenHelloThenGrateOracle() throws IOException {
        testInputAndExpected(
                Joiner.on(ln).join("привет", "пока"),
                Joiner.on(ln).join("Hello, dear friend, I'm a oracle.", "", "", "")
        );
    }

    @Test
    public void whenHowAreYouThenFineOracle() throws IOException {
        testInputAndExpected(
                Joiner.on(ln).join("как дела?", "пока"),
                Joiner.on(ln).join("У меня все отлично!!", "А у Вас как?", "", "", "")
        );
    }


    public void testInputAndExpected(String input, String expected) throws IOException {
        Socket socket = mock(Socket.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        when(socket.getInputStream()).thenReturn(inputStream);
        when(socket.getOutputStream()).thenReturn(outputStream);
        Server server = new Server();
        server.startup(socket);
        assertThat(outputStream.toString(), is(expected));
    }


}