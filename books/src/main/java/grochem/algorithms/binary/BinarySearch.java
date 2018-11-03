package grochem.algorithms.binary;

public class BinarySearch {
    private int operations;

    public int search(int[] sortData, int search) {
        this.operations = 0;
        int res = -1;
        int low = 0;
        int top = sortData.length - 1;
        int mid;
        while (low <= top) {
            this.operations++;
            mid = (top + low) / 2;
            int guess = sortData[mid];
            if (guess == search) {
                res = mid;
                break;
            } else if (guess > search) {
                top = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return res;
    }

    public int getCountOperations() {
        return operations;
    }
}
