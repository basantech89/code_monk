import edu.duke.*;
import java.io.*;
import java.util.*;

public class VigenereBreaker {

    private final static String alphabet = "abcdefghijklmnopqrstuvwxyz";

    public String sliceString(String message, int whichSlice, int totalSlices) {
        StringBuilder encrypted = new StringBuilder();
        for (int i = whichSlice; i < message.length(); i += totalSlices)
            encrypted.append(message.charAt(i));
        return encrypted.toString();
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        for (int i = 0; i < klength; i++) {
            String slice = sliceString(encrypted, i, klength);
            CaesarCracker cracker = new CaesarCracker(mostCommon);
            key[i] = cracker.getKey(slice);
        }
        return key;
    }

    public void breakVigener() {
        HashMap<String, HashSet<String>> langDicts = new HashMap<>();
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            langDicts.put(f.getName(), readDictionary(fr));
        }
        FileResource resource = new FileResource();
        String encrypted = resource.asString();
        breakForAllLangs(encrypted, langDicts);
    }

    public HashSet<String> readDictionary (FileResource fr) {
        HashSet<String> dict = new HashSet<>();
        for (String line : fr.lines())
            dict.add(line.toLowerCase());
        return dict;
    }

    public int countWords (String message, HashSet<String> dict) {
        int words = 0;
        for (String word : message.split("\\W+"))
            if (dict.contains(word.toLowerCase()))
                words += 1;
        return words;
    }

    public String breakForLanguage (String encrypted, HashSet<String> dict) {
        int max = 0; String plain = ""; int[] realKey = null;
        for (int i = 1; i <= 100; i++) {
            char commonChar = mostCommonCharIn(dict);
            int[] key = tryKeyLength(encrypted, i, commonChar);
            VigenereCipher cipher = new VigenereCipher(key);
            String decrypted = cipher.decrypt(encrypted);
            int realWords = countWords(decrypted, dict);
            if (realWords > max) {
                max = realWords;
                plain = decrypted;
                realKey = key;
            }
        }
        System.out.println("The key is " + Arrays.toString(realKey));
        return plain;
    }

    public char mostCommonCharIn (HashSet<String> dict) {
        HashMap<Character, Integer> counts = new HashMap<>(26);
        for (String line : dict) {
            // adding the counts for characters in line to currCounts hashmap
            HashMap<Character, Integer> currCounts = getLineCounts(line);
            // adding the counts of characters from currCount to counts
            // to keep track of total counts of characters in a dictionary
            addCounts(counts, currCounts);
        }
        return getCommonChar(counts);
    }

    private HashMap<Character, Integer> getLineCounts (String line) {
        HashMap<Character, Integer> count = new HashMap<>();
        for (char currChar : line.toCharArray()) {
            addCharCount(currChar, count);
        }
        return count;
    }

    private void addCounts (HashMap<Character, Integer> counts,
                            HashMap<Character, Integer> currCount) {
        for (Character currChar : currCount.keySet()) {
            addCharCount(currChar, counts);
        }
    }

    private void addCharCount(char ch, HashMap<Character, Integer> counts) {
        if (!counts.containsKey(ch))
            counts.put(ch, 1);
        else
            counts.put(ch, counts.get(ch) + 1);
    }

    private char getCommonChar (HashMap<Character, Integer> counts) {
        HashMap.Entry<Character, Integer> maxEntry = null;
        for (HashMap.Entry<Character, Integer> entry : counts.entrySet())
            if (maxEntry == null || entry.getValue() > maxEntry.getValue())
                maxEntry = entry;
        return maxEntry.getKey();
    }

    private void breakForAllLangs (String encrypted,
                                   HashMap<String, HashSet<String>> languages) {
        int maxRealWords = 0; String bestLang = null;
        for (String lang : languages.keySet()) {
            int realwords = countWords(encrypted, languages.get(lang));
            if (realwords > maxRealWords) {
                maxRealWords = realwords;
                bestLang = lang;
            }
        }
        String decrypted = breakForLanguage(encrypted, languages.get(bestLang));
        System.out.println("The best language for decryption is " + bestLang +
                " and Real words are " + maxRealWords);
        System.out.println("The decrypted message is\n" + decrypted);
    }
}