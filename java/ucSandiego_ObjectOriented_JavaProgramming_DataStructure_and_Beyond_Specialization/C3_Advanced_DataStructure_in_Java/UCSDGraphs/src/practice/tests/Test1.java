package practice.tests;

import java.util.HashMap;
import java.util.HashSet;

public class Test1 {

    public static void main (String[] args) {
        HashMap<HashSet<Integer>, String> test = new HashMap<>();
        HashSet<Integer> testSet = new HashSet<>();
        testSet.add(1);
        testSet.add(2);
        test.put(testSet, "test");
        System.out.println(test);
        if (test.containsKey(testSet))
            System.out.println("true");
    }
}
