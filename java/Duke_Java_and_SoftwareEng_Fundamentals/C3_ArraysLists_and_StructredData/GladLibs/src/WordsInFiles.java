import edu.duke.DirectoryResource;
import edu.duke.FileResource;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WordsInFiles {

    private Map<String, ArrayList<String>> wordInFiles;

    public WordsInFiles() {
        wordInFiles = new HashMap<>();
    }

    public void addWordsFromFile(File f) {
        FileResource resource = new FileResource(f);
        for (String word : resource.words()) {
            if (!wordInFiles.containsKey(word)) {
                ArrayList<String> files = new ArrayList<>();
                files.add(f.getName());
                wordInFiles.put(word, files);
            }
            else {
                ArrayList<String> files = wordInFiles.get(word);
                if (!files.contains(f.getName()))
                    files.add(f.getName());
            }
        }
    }

    public void buildWordFileMap () {
        wordInFiles.clear();
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles())
            addWordsFromFile(f);
    }

    public int maxNumber () {
        int max = 0;
        for (ArrayList<String> files : wordInFiles.values())
            if (files.size() > max)
                max = files.size();
        return max;
    }

    public ArrayList<String> wordsInNumFiles (int number) {
        ArrayList<String> words = new ArrayList<>();
        for (String word : wordInFiles.keySet())
            if (wordInFiles.get(word).size() == number)
                words.add(word);
        return words;
    }

    public void printFilesIn (String word) {
        if (wordInFiles.containsKey(word)) {
            System.out.print("\"" + word + "\" appears in files: ");
            for (String fileName : wordInFiles.get(word))
                System.out.print(fileName + ", ");
            System.out.println();
        }
    }

    public void tester () {
        buildWordFileMap();
        int maxNum = maxNumber();
        int noOfFile = 4;
        ArrayList<String> words = wordsInNumFiles(noOfFile);
        System.out.print("The greatest number of files a word appear in is "
                + maxNum + ", and these words are ");
        int counts = 0;
        for (String word : words) {
            System.out.print("\"" + word + "\" ");
            counts += 1;
        }
        System.out.println("\nNo of words that appear in "+ noOfFile + " files are " + counts);
        System.out.println();
        //for (String word : words)
           // printFilesIn(word);
        printFilesIn("tree");
    }

    public static void main (String[] args) {
        WordsInFiles words = new WordsInFiles();
        words.tester();
    }

}

