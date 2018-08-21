package examples.SortingAlgoritms;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SortBenchMarking {

    public static void main (String[] args) {

        // Run each test more then once to get bigger numbers and less noise
        // Try playing around with this number
        int trials = 100;

        // The size of array to increment each step
        // Try playing around with this number
        int increment = 500;

        // The number of steps to run
        // Try playing around with this number
        int numSteps = 20;

        // The size of array to start with
        // Try playing around with this number
        int start = 500;

        // Rest of the method should run a loop for each of the sorting algorithm
        // and prints out timing results as described in pseudo code

        System.out.println("SizeOfArray\tbubbleSort\tInsertionSort" +
                "\tMergeSort\tQuickSort");


        Random random = new Random(20);
        List<Integer> unsortedList = new LinkedList<>();

                
        for (int arraySize = start; arraySize <= numSteps * increment; arraySize += increment) {

            System.out.print(arraySize + "\t");
            
            
            for (int i = 0; i <= arraySize; i++)
                unsortedList.add(random.nextInt(arraySize));

            
            long bubbleSortAlgoStartTime = System.nanoTime();
            for (int i = 0; i < trials; i++)
                OtherSortingAlgos.bubbleSort(unsortedList.toArray(new Integer[unsortedList.size()]));
            long bubbleSortAlgoEndTime = System.nanoTime();
            double bubbleSortAlgoEstTime = (bubbleSortAlgoEndTime - bubbleSortAlgoStartTime) / 1000000000.0;
            System.out.print(bubbleSortAlgoEstTime + "\t");


            long insertionSortAlgoStartTime = System.nanoTime();
            for (int i = 0; i < trials; i++)
                OtherSortingAlgos.insertionSort(unsortedList.toArray(new Integer[unsortedList.size()]));
            long insertionSortAlgoEndTime = System.nanoTime();
            double insertionSortAlgoEstTime = (insertionSortAlgoEndTime - insertionSortAlgoStartTime) / 1000000000.0;
            System.out.print(insertionSortAlgoEstTime + "\t");

            
            /*long selectionSortAlgoStartTime = System.nanoTime();
            for (int i = 0; i < trials; i++)
                OtherSortingAlgos.selectionSort(unsortedList.toArray(new Integer[unsortedList.size()]));
            long selectionSortAlgoEndTime = System.nanoTime();
            double selectionSortAlgoEstTime = (selectionSortAlgoEndTime - selectionSortAlgoStartTime) / 1000000000.0;*/
            

            long mergeSortAlgoStartTime = System.nanoTime();
            for (int i = 0; i < trials; i++)
                MergeSort.mergeSort(unsortedList.toArray(new Integer[unsortedList.size()]));
            long mergeSortAlgoEndTime = System.nanoTime();
            double mergeSortAlgoEstTime = (mergeSortAlgoEndTime - mergeSortAlgoStartTime) / 1000000000.0;
            System.out.print(mergeSortAlgoEstTime + "\t");


            long quickSortAlgoStartTime = System.nanoTime();
            for (int i = 0; i < trials; i++)
                QuickSort.quickSort(unsortedList.toArray(new Integer[unsortedList.size()]));
            long quickSortAlgoEndTime = System.nanoTime();
            double quickSortAlgoEstTime = (quickSortAlgoEndTime - quickSortAlgoStartTime) / 1000000000.0;
            System.out.println(quickSortAlgoEstTime);

        }

    }
}
