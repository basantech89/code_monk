import edu.duke.*;

public class WordLengths {

    public int[] countWordLengths(FileResource resource, int[] counts) {
        for (String word : resource.words()) {
            if (word.length() <= 1) continue;
            if (word.length() >= counts.length) counts[counts.length - 1] += 1;
            int lastIndex = word.length() - 1;
            // if the 1st and last chars are letters
            if (Character.isLetter(word.charAt(0))
                    && Character.isLetter(word.charAt(lastIndex)))
                counts[word.length()] += 1;
            // if the 1st or last char is letter but not both
            else if (Character.isLetter(word.charAt(0)) ||
                    Character.isLetter(word.charAt(lastIndex)))
                counts[word.length() - 1] += 1;
            // if none of them is a letter
            else
                counts[word.length() -2] += 1;
        }
        return counts;
    }

    public int indexOfMax(int[] values) {
        int maxDex = 0;
        for (int i = 0; i < values.length; i++)
            if (values[i] > values[maxDex]) maxDex = i;
        return maxDex;
    }

    public void testCountWordLengths() {
        FileResource resource = new FileResource();
        int[] counts = new int[31];
        int[] wordLength = countWordLengths(resource, counts);
        System.out.println(indexOfMax(wordLength));
    }

    public static void main(String[] args) {
        WordLengths wordLengths = new WordLengths();
        wordLengths.testCountWordLengths();
    }

}
