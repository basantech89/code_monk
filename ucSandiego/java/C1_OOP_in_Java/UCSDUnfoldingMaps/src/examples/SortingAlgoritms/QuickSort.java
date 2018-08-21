package examples.SortingAlgoritms;

import java.util.Arrays;
import static com.intellij.util.ArrayUtil.swap;

public class QuickSort {

    //private Integer[] unsorted;

    //public QuickSort(Integer[] unsorted) { this.unsorted = unsorted; }

    public static void quickSort (Integer[] unsorted) {
        int low = 0, high = unsorted.length - 1;
        quickSort(unsorted, low, high);
    }

    public static void quickSort (Integer[] unsorted, int left, int right) {
        int i = left, j = right;
        int pivot = unsorted[i + (j - i) / 2];

        while (i <= j) {
            while (unsorted[i] < pivot)
                i++;

            while (unsorted[j] > pivot)
                j--;

            if (i <= j) {
                swap(unsorted, i, j);
                i++;
                j--;
            }
        }

        if (left < j) quickSort(unsorted, left, j);
        if (i < right) quickSort(unsorted, i, right);
    }

    public static void main (String[] args) {
        Integer[] test1 = {70, 30, 80, 110, 5, 50, 20, 60, 40, 100};
        Integer[] test2 = {3, 7, 8, 2, 3, 5, 9, 5, 4, 6};
        Integer[] test3 = {3, 7, 8, 2, 5, 1, 9, 5, 4, 6};
        Integer[] test4 = {24, 2, 45, 20, 56, 75, 2, 56, 99, 53, 12};
        Integer[] test5 = {1, 12, 5, 26, 7, 14, 3, 7, 2};
        quickSort(test5);
        System.out.println(Arrays.toString(test5));
    }

}
