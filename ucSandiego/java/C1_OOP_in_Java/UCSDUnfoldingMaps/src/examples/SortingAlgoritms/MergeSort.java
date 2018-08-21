package examples.SortingAlgoritms;
import java.util.*;

public class MergeSort {

    private static Integer[] sorted;

    public static void main (String[] args) {
        //Integer[] unsorted = {2, 6, 3, 5, 1};
        Integer[] unsorted = {38, 27, 43, 3, 9, 82, 10};
        mergeSort(unsorted);
        //System.out.println(Arrays.toString(unsorted));
        System.out.println(Arrays.toString(sorted));
    }

    public static void mergeSort(Integer[] unsorted) {
        sorted = new Integer[unsorted.length];
        mergeSort(unsorted, 0, unsorted.length - 1);
    }

    public static void mergeSort (Integer[] unsorted, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            // sort the left part of array
            mergeSort(unsorted, left, mid);
            // sort right part of array
            mergeSort(unsorted, mid + 1, right);
            // now merge both sides
            mergeParts(unsorted, left, mid + 1, right);
        }
    }

    private static void mergeParts (Integer[] unsorted,
                                    int left, int right, int rightEnd) {
        int leftEnd = right - 1;
        int k = left;
        int num = rightEnd - left + 1;

        while (left <= leftEnd && right <= rightEnd)
            if (unsorted[left].compareTo(unsorted[right]) <= 0)
                sorted[k++] = unsorted[left++];
            else
                sorted[k++] = unsorted[right++];

        // copy rest of left half
        while (left <= leftEnd) sorted[k++] = unsorted[left++];

        // copy rest of right half
        while (right <= rightEnd) sorted[k++] = unsorted[right++];

        for (int i = 0; i < num; i++, rightEnd--)
            unsorted[rightEnd] = sorted[rightEnd];

    }
}
