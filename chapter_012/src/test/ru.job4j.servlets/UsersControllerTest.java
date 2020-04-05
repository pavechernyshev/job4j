package ru.job4j.servlets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.logic.ValidateService;
import ru.job4j.logic.ValidateStub;
import ru.job4j.persistent.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ValidateService.class)
public class UsersControllerTest {

    @Test
    public void whenAddUserThenStoreIt() throws ServletException, IOException {
        Store validate = new ValidateStub();
        PowerMock.mockStatic(ValidateService.class);
        Mockito.when(ValidateService.getINSTANCE()).thenReturn(validate);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("name")).thenReturn("Petr Arsentev");
        new UsersController().doPost(req, resp);
        assertThat(validate.findAll().iterator().next().getName(), is("Petr Arsentev"));
    }
}