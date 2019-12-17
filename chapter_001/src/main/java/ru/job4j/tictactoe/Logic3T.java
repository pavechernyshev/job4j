package ru.job4j.tictactoe;
import ru.job4j.array.MatrixCheck;

public class Logic3T {
    private final Figure3T[][] table;

    public Logic3T(Figure3T[][] table) {
        this.table = table;
    }

    public boolean isWinnerX() {
        return this.checkMatrix(this.transformToBoolean(this.table, true));
    }

    public boolean isWinnerO() {
        return this.checkMatrix(this.transformToBoolean(this.table, false));
    }

    public boolean hasGap() {
        boolean isHasGap = false;
        for (Figure3T[] row: this.table) {
            for (Figure3T item: row) {
                if (item.isEmpty()) {
                    isHasGap = true;
                    break;
                }
            }
        }
        return isHasGap;
    }

    private boolean checkMatrix(boolean[][] table) {
        boolean result = false;
        MatrixCheck matrixCheck = new MatrixCheck();
        if (matrixCheck.hasFillOneDiagonal(table, true)) {
            result = true;
        } else {
            if (matrixCheck.hasFillOneHorizontalOrVertical(table, true)) {
                result = true;
            }
        }
        return result;
    }

    private  boolean[][] transformToBoolean(Figure3T[][] table, boolean isCheckX) {
        int height = table.length;
        int width = table[0].length;
        boolean[][] res = new boolean[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (isCheckX) {
                    if (table[row][col].hasMarkX()) {
                        res[row][col] = true;
                    } else {
                        res[row][col] = false;
                    }
                } else {
                    if (table[row][col].hasMarkO()) {
                        res[row][col] = true;
                    } else {
                        res[row][col] = false;
                    }
                }
            }
        }
        return res;
    }
}
