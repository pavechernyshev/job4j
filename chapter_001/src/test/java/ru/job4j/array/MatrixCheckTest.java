package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Pavel Chernyshev (titan100695@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class MatrixCheckTest {
    @Test
    public void whenDataMonoByTrueThenTrue() {
        MatrixCheck check = new MatrixCheck();
        boolean[][] input = new boolean[][] {
                {true, true, true},
                {false, true, true},
                {true, false, true}
        };
        boolean result = check.mono(input);
        assertThat(result, is(true));
    }

   @Test
    public void whenDataMonoByFourFalseThenTrue() {
        MatrixCheck check = new MatrixCheck();
        boolean[][] input = new boolean[][] {
                {false, true, true, false},
                {false, false, false, false},
                {true, false, false, false},
                {false, false, true, false},
        };
        boolean result = check.mono(input);
        assertThat(result, is(true));
    }

   @Test
    public void whenDataNotMonoByFourFalseThenTrue() {
        MatrixCheck check = new MatrixCheck();
        boolean[][] input = new boolean[][] {
                {false, true, true, false},
                {false, false, true, false},
                {true, false, false, false},
                {false, false, true, false},
        };
        boolean result = check.mono(input);
        assertThat(result, is(false));
    }

    @Test
    public void whenDataNotMonoByTrueThenFalse() {
        MatrixCheck check = new MatrixCheck();
        boolean[][] input = new boolean[][] {
                {true, true, false},
                {false, false, true},
                {true, false, true}
        };
        boolean result = check.mono(input);
        assertThat(result, is(false));
    }

    @Test
    public void whenDataHasFillOneDiagonalByTrueCheckTrueThenTrue() {
        MatrixCheck check = new MatrixCheck();
        boolean[][] input = new boolean[][] {
                {true, true, false},
                {false, true, true},
                {true, false, true}
        };
        boolean result = check.hasFillOneDiagonal(input, true);
        assertThat(result, is(true));
    }

    @Test
    public void whenDataHasFillOneDiagonalByTrueCheckFalseThenFalse() {
        MatrixCheck check = new MatrixCheck();
        boolean[][] input = new boolean[][] {
                {true, true, false},
                {false, true, true},
                {true, false, true}
        };
        boolean result = check.hasFillOneDiagonal(input, false);
        assertThat(result, is(false));
    }

    @Test
    public void whenDataHasFillOneDiagonalByFalseCheckFalseThenTrue() {
        MatrixCheck check = new MatrixCheck();
        boolean[][] input = new boolean[][] {
                {true, true, false},
                {false, false, true},
                {false, false, true}
        };
        boolean result = check.hasFillOneDiagonal(input, false);
        assertThat(result, is(true));
    }

    @Test
    public void whenHasFillOneHorizontalNotHaveCheckFalseThenFalse() {
        MatrixCheck check = new MatrixCheck();
        boolean[][] input = new boolean[][] {
                {true, true, true},
                {false, false, true},
        };
        boolean result = check.hasFillOneHorizontal(input, false);
        assertThat(result, is(false));
    }

    @Test
    public void whenHasFillOneHorizontalHaveCheckFalseThenTrue() {
        MatrixCheck check = new MatrixCheck();
        boolean[][] input = new boolean[][] {
                {true, true, false},
                {false, false, false},
                {false, false, true}
        };
        boolean result = check.hasFillOneHorizontal(input, false);
        assertThat(result, is(true));
    }

    @Test
    public void whenHasFillOneHorizontalHaveCheckTrueThenTrue() {
        MatrixCheck check = new MatrixCheck();
        boolean[][] input = new boolean[][] {
                {true, true, true},
                {false, false, true},
                {false, false, true}
        };
        boolean result = check.hasFillOneHorizontal(input, true);
        assertThat(result, is(true));
    }

    @Test
    public void whenHasFillOneVerticalHaveCheckTrueThenTrue() {
        MatrixCheck check = new MatrixCheck();
        boolean[][] input = new boolean[][] {
                {false, true, true},
                {false, false, true},
                {true, false, true}
        };
        boolean result = check.hasFillOneVertical(input, true);
        assertThat(result, is(true));
    }

    @Test
    public void whenHasFillOneVerticalHaveCheckFalseThenTrue() {
        MatrixCheck check = new MatrixCheck();
        boolean[][] input = new boolean[][] {
                {false, true, false},
                {false, false, true},
                {false, false, true}
        };
        boolean result = check.hasFillOneVertical(input, false);
        assertThat(result, is(true));
    }

    @Test
    public void whenHasFillOneVerticalNotHaveCheckFalseThenFalse() {
        MatrixCheck check = new MatrixCheck();
        boolean[][] input = new boolean[][] {
                {true, true, true},
                {false, false, true},
        };
        boolean result = check.hasFillOneVertical(input, false);
        assertThat(result, is(false));
    }

    @Test
    public void whenHasFillOneVerticalNotHaveCheckTrueThenFalse() {
        MatrixCheck check = new MatrixCheck();
        boolean[][] input = new boolean[][] {
                {true, true, false},
                {false, false, true},
        };
        boolean result = check.hasFillOneVertical(input, true);
        assertThat(result, is(false));
    }

    @Test
    public void whenHasFillOneHorizontalOrVerticalNotHaveCheckTrueThenFalse() {
        MatrixCheck check = new MatrixCheck();
        boolean[][] input = new boolean[][] {
                {true, true, false},
                {false, false, true},
                {true, false, true},
        };
        boolean result = check.hasFillOneHorizontalOrVertical(input, true);
        assertThat(result, is(false));
    }

    @Test
    public void whenHasFillOneHorizontalOrVerticalNotHaveCheckFalseThenFalse() {
        MatrixCheck check = new MatrixCheck();
        boolean[][] input = new boolean[][] {
                {true, true, false},
                {false, false, true},
                {true, false, true},
        };
        boolean result = check.hasFillOneHorizontalOrVertical(input, false);
        assertThat(result, is(false));
    }

    @Test
    public void whenHasFillOneHorizontalOrVerticalHaveCheckFalseThenTrue() {
        MatrixCheck check = new MatrixCheck();
        boolean[][] input = new boolean[][] {
                {true, true, false},
                {false, false, false},
                {true, false, true},
        };
        boolean result = check.hasFillOneHorizontalOrVertical(input, false);
        assertThat(result, is(true));
    }

    @Test
    public void whenHasFillOneHorizontalOrVerticalHaveCheckTrueThenTrue() {
        MatrixCheck check = new MatrixCheck();
        boolean[][] input = new boolean[][] {
                {true, true, false},
                {false, false, false},
                {true, true, true},
        };
        boolean result = check.hasFillOneHorizontalOrVertical(input, true);
        assertThat(result, is(true));
    }
}
