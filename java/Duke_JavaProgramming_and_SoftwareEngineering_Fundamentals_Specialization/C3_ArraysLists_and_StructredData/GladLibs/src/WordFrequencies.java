import edu.duke.FileResource;

import java.util.ArrayList;

public class WordFrequencies {

    private ArrayList<String> myWords;
    private ArrayList<Integer> myFreqs;

    public WordFrequencies() {
        this.myWords = new ArrayList<>();
        this.myFreqs = new ArrayList<>();
    }

    public void findUnique() {
        myWords.clear(); myFreqs.clear();
        int uniques = 0;
        FileResource resource = new FileResource();
        for (String word : resource.words()) {
            word = word.toLowerCase();
            if (!myWords.contains(word)) {
                myWords.add(word);
                myFreqs.add(1);
                ++uniques;
            }
            else {
                int index = myWords.indexOf(word);
                int val = myFreqs.get(index);
                myFreqs.set(index, val + 1);
            }
        }
        System.out.println("Number of unique words are " + uniques);
    }

    public int findIndexOfMax() {
        int maxIndex = 0;
        for (int i = 0; i < myFreqs.size(); i++)
            if (myFreqs.get(i) > myFreqs.get(maxIndex))
                maxIndex = i;
        return maxIndex;
    }

    public void tester() {
        findUnique();
        int maxIndex = findIndexOfMax();
        for (int i = 0; i < myFreqs.size(); i++)
            System.out.println(myWords.get(i) + "\t" + myFreqs.get(i));
        System.out.println("The word that occurs most often and its counts are "
                            + myWords.get(maxIndex) + " " + myFreqs.get(maxIndex));
    }

    public static void main(String[] args) {
        WordFrequencies word = new WordFrequencies();
        word.tester();
    }
}
