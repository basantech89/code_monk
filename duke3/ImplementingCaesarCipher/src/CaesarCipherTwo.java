public class CaesarCipherTwo {

    private String alphabet;
    private String shiftedAlphabet1;
    private String shiftedAlphabet2;
    private int mainKey1;
    private int mainKey2;

    public CaesarCipherTwo(int key1, int key2) {

        this.alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        this.shiftedAlphabet1 = alphabet.substring(key1, 26) + alphabet.substring(0, key1)
                + alphabet.substring(key1 + 26, 52) + alphabet.substring(26, key1 + 26);
        this.shiftedAlphabet2 = alphabet.substring(key2, 26) + alphabet.substring(0, key2)
                + alphabet.substring(key2 + 26, 52) + alphabet.substring(26, key2 + 26);
        mainKey1 = key1;
        mainKey2 = key2;
    }

    public String encrypt (String input) {

        StringBuilder encrypt = new StringBuilder(input);
        for (int i = 0; i < input.length(); i++) {
            char currChar = input.charAt(i);
            int currCharIndex = alphabet.indexOf(currChar);
            if (currCharIndex != -1) {
                char replaceWith;
                if (i % 2 == 0)
                    replaceWith = shiftedAlphabet1.charAt(currCharIndex);
                else
                    replaceWith = shiftedAlphabet2.charAt(currCharIndex);
                encrypt.setCharAt(i, replaceWith);
            }
        }
        return encrypt.toString();
    }

    public String decrypt(String encrypted) {
        String evenHalf = halfOfString(encrypted, 0);
        String oddHalf = halfOfString(encrypted, 1);
        System.out.println("key 1 is " + (mainKey1) +
                " and key 2 is " + (mainKey2));
        CaesarCipherTwo cipher2 = new CaesarCipherTwo(26 - mainKey1, 26 - mainKey2);
        return cipher2.encrypt(encrypted);
    }

    public String breakCaesarCipher(String encrypted) {
        String evenHalf = halfOfString(encrypted, 0);
        String oddHalf = halfOfString(encrypted, 1);
        int dkey1 = getKey(evenHalf);
        int dkey2 = getKey(oddHalf);
        System.out.println("key 1 is " + (dkey1) +
                " and key 2 is " + (dkey2));
        CaesarCipherTwo cipher2 = new CaesarCipherTwo(26 - dkey1, 26 - dkey2);
        return cipher2.encrypt(encrypted);
    }

    public int[] countLetters(String encrypted) {
        String alpha = "abcdefghijklmnopqrstuvwxyz";
        int[] counts = new int[26];
        for (int i = 0; i < encrypted.length(); i++) {
            char ch = Character.toLowerCase(encrypted.charAt(i));
            int dex = alpha.indexOf(ch);
            if (dex != -1) counts[dex] += 1;
        }
        return counts;
    }

    public int maxIndex(int[] vals) {
        int maxDex = 0;
        for (int i = 0; i < vals.length; i++)
            if (vals[i] > vals[maxDex]) maxDex = i;
        return maxDex;
    }

    public String halfOfString (String message, int start) {
        StringBuilder half = new StringBuilder();
        for (int i = start; i < message.length(); i += 2)
            half.append(message.charAt(i));
        return half.toString();
    }

    public int getKey (String encrypted) {
        int freqIndex = 4;
        int[] freqs = countLetters(encrypted);
        int maxDex = maxIndex(freqs);
        int dkey = maxDex - freqIndex;
        if (maxDex < freqIndex) dkey = 26 - (freqIndex - maxDex);
        return dkey;
    }

}
