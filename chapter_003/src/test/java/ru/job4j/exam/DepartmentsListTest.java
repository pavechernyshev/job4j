package ru.job4j.exam;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
public class DepartmentsListTest {
    private DepartmentsList dl = new DepartmentsList();


    @Test
    public void whenSortedListTheSortList() {
        DepartmentsList depParser = new DepartmentsList();
        String[] deps = new String[]{
                "K1\\SK1\\SSK1",
                "K1\\SK1\\SSK2",
                "K2\\SK1\\SSK2",
                "K2\\SK1\\SSK1"
        };
        String[] actual = depParser.parse(deps);
        String[] expected = new String[]{
                "K1",
                "K1\\SK1",
                "K1\\SK1\\SSK1",
                "K1\\SK1\\SSK2",
                "K2",
                "K2\\SK1",
                "K2\\SK1\\SSK1",
                "K2\\SK1\\SSK2"
        };
        assertThat(actual, is(expected));
    }

    @Test
    public void whenSortedListTheReSortList() {
        DepartmentsList depParser = new DepartmentsList();
        String[] deps = new String[]{
                "K1\\SK1\\SSK1",
                "K1\\SK1\\SSK2",
                "K2\\SK1\\SSK2",
                "K2\\SK1\\SSK1"
        };
        depParser.parse(deps);
        String[] actual = depParser.resort();
        String[] expected = new String[]{
                "K2",
                "K2\\SK1",
                "K2\\SK1\\SSK2",
                "K2\\SK1\\SSK1",
                "K1",
                "K1\\SK1",
                "K1\\SK1\\SSK2",
                "K1\\SK1\\SSK1"
        };
        assertThat(actual, is(expected));
    }
}
