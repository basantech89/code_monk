import edu.duke.*;

public class Examples {

    public void countShakespeare() {
        String[] plays = {"caesar.txt", "errors.txt", "hamlet.txt", "likeit.txt",
                            "macbeth.txt", "romeo.txt"};
        // get the 20 common words
        String[] common = getCommon();
        // array having counters for each of the word of 20 common words
        int[] counts = new int[common.length];
        // for each play, count how many times 20 common words appear in the play
        for (int i = 0; i < plays.length; i++) {
            FileResource resource = new FileResource("commonWords/" + plays[i]);
            countWords(resource, common, counts);
            System.out.println("done with " + plays[i]);
        }
        for (int i = 0; i < common.length; i++)
            System.out.println(common[i] + "\t" + counts[i]);
    }

    public String[] getCommon() {
        FileResource resource = new FileResource("commonWords/common.txt");
        String[] common = new String[20];
        int index = 0;
        for (String word : resource.words()) {
            common[index] = word;
            index += 1;
        }
        return common;
    }

    public void countWords(FileResource resource, String[] common, int[] counts) {
        for (String word : resource.words()) {
            word = word.toLowerCase();
            int index = indexOf(common, word);
            // the index of word in common array will be used
            // as the index for counts array
            if (index != -1) counts[index] += 1;
        }
    }

    public int indexOf(String[] common, String word) {
        for (int i = 0; i < common.length; i++)
            if (common[i].equals(word)) return i;
        return -1;
    }


    public static void main(String[] args) {
        Examples example = new Examples();
        example.countShakespeare();
    }
}
