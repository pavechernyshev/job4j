package ru.job4j.list;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
public class UserConvertTest {
    @Test
    public void whenUserConvertThenHashMap() {
        UserConvert convert = new UserConvert();
        List<User> users = Arrays.asList(new User(22, "Pavel", "SPb"),
                new User(3, "Alex", "Msk"));

        HashMap<Integer, User> result = convert.process(users);
        HashMap<Integer, User> expect = new HashMap<>();
        expect.put(22, new User(22, "Pavel", "SPb"));
        expect.put(3, new User(3, "Alex", "Msk"));
        assertThat(result, is(expect));
    }
}
