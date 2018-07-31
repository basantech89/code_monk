import edu.duke.FileResource;

import java.util.Arrays;
import java.util.HashSet;

public class VigenereBreakerUnknownKeyLength {

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

    public void breakVigenere () {
        FileResource resource = new FileResource();
        String encrypted = resource.asString();
        //System.out.println("The Encrypted message is\n" + encrypted);
        FileResource fr = new FileResource("dictionaries/English");
        HashSet<String> dict = readDictionary(fr);
        String decrypted = breakForLanguage(encrypted, dict);
        System.out.println("The decrypted message is\n" + decrypted);
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
            int[] key = tryKeyLength(encrypted, i, 'e');
            VigenereCipher cipher = new VigenereCipher(key);
            String decrypted = cipher.decrypt(encrypted);
            int realWords = countWords(decrypted, dict);
            if (realWords > max) {
                max = realWords;
                plain = decrypted;
                realKey = key;
            }
        }
        System.out.println("The key is " + Arrays.toString(realKey) +
                "\nThe key size is " + realKey.length +
                "\nThe file contains " + max + " valid words out of " +
                plain.split("\\W+").length + " words");
        return plain;
    }
}
