package ru.job4j.exam;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
public class DepartmentsListTest {
    private DepartmentsList dl = new DepartmentsList();

    @Before
    public void fill() {
        this.dl.add("K1\\SK1");
        this.dl.add("K1\\SK2");
        this.dl.add("K1\\SK1\\SSK2");
        this.dl.add("K1\\SK1\\SSK1");
        this.dl.add("K2");
        this.dl.add("K2\\SK1\\SSK1");
        this.dl.add("K2\\SK1\\SSK2");
    }

    @Test
    public void whenSortedListTheSortList() {
        List<String> expect = Arrays.asList(
                "K1",
                "K1\\SK1",
                "K1\\SK1\\SSK1",
                "K1\\SK1\\SSK2",
                "K1\\SK2",
                "K2",
                "K2\\SK1",
                "K2\\SK1\\SSK1",
                "K2\\SK1\\SSK2"
        );
        assertThat(dl.sort(), is(expect));
    }

    @Test
    public void whenResortedListTheResortList() {
        List<String> expect = Arrays.asList(
                "K2",
                "K2\\SK1",
                "K2\\SK1\\SSK2",
                "K2\\SK1\\SSK1",
                "K1",
                "K1\\SK2",
                "K1\\SK1",
                "K1\\SK1\\SSK2",
                "K1\\SK1\\SSK1"
        );
        assertThat(dl.resort(), is(expect));
    }
}
