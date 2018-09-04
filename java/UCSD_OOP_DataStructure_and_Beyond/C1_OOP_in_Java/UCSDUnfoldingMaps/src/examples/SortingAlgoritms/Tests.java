package examples.SortingAlgoritms;

import java.util.Arrays;

public class Tests {

    public static Integer[] unsorted = {7, 6, 5, 4, 3, 2, 1};

    public static void main (String[] args) {
        OtherSortingAlgos.insertionSort(unsorted);
        System.out.println(Arrays.toString(unsorted));
    }
}
