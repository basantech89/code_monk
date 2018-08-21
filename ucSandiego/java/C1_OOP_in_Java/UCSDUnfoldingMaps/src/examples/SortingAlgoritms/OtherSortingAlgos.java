package examples.SortingAlgoritms;


import static com.intellij.util.ArrayUtil.swap;

public class OtherSortingAlgos {

    public static Integer[] selectionSort (Integer[] vals) {
        for (int i = 0; i < vals.length - 1; i++) {
            int indexMin = i;
            for (int j = i + 1; j < vals.length; j++)
                if (vals[indexMin] > vals[j]) indexMin = j;
            swap(vals, i, indexMin);
        }
        return vals;
    }

    public static Integer[] insertionSort (Integer[] vals) {
        int currIdx = 0;
        for (int pos = 1; pos < vals.length; pos++) {
            currIdx = pos;
            while (currIdx > 0 && vals[currIdx] < vals[currIdx - 1]) {
                swap(vals, currIdx, currIdx - 1);
                currIdx -= 1;
            }
        }
        return vals;
    }

    /**
     * Worst case and average case complexity is O(n^2)
     * best case complexity is O(n)
     * */
    public static Integer[] bubbleSort (Integer[] vals) {
        int swaps;
        while (true) {
            swaps = 0;
            // loop to mergeSort the array
            for (int i = 0; i < vals.length - 1; i++)
                if (vals[i] > vals[i + 1]) {
                    swaps += 1;
                    swap(vals, i, i + 1);
                }
            // array is fully sorted now, so return the sorted array
            if (swaps == 0) return vals;
        }
    }
}
