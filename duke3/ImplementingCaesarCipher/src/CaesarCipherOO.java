public class CaesarCipherOO {

    private String alphabet;
    private String shiftedAlphabet;
    private int mainKey;

    public CaesarCipherOO(int key) {
        this.alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        this.shiftedAlphabet = alphabet.substring(key, 26) + alphabet.substring(0, key)
                + alphabet.substring(key + 26, 52) + alphabet.substring(26, key + 26);
        this.mainKey = key;
    }

    public String encrypt (String input) {
        StringBuilder encrypt = new StringBuilder(input);
        for (int i = 0; i < input.length(); i++) {
            char currChar = input.charAt(i);
            int currCharIndex = alphabet.indexOf(currChar);
            if (currCharIndex != -1) {
                char replaceWith = shiftedAlphabet.charAt(currCharIndex);
                encrypt.setCharAt(i, replaceWith);
            }
        }
        return encrypt.toString();
    }

    public void eyeballDecrypt(String encrypted) {
        for (int i = 0; i < 26; i++) {
            CaesarCipherOO cipher = new CaesarCipherOO(i);
            String plain = cipher.encrypt(encrypted);
            System.out.println(i + "\t" + plain);
        }
    }

    public String decrypt(String encrypted) {
        CaesarCipherOO cipher = new CaesarCipherOO(26 - mainKey);
        return cipher.encrypt(encrypted);
    }

    public String breakCaesarCipher(String encrypted) {
        int dkey = getKey(encrypted);
        CaesarCipherOO cipher = new CaesarCipherOO(26 - dkey);
        return cipher.encrypt(encrypted);
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

    public static void main (String[] args) {
        CaesarCipherOO cipher = new CaesarCipherOO(22);
        //FileResource fr = new FileResource();
        int key = 23;
        //String message = fr.asString();
        //String encrypted = cipher.encrypt(message, key);
        //System.out.println(cipher.encrypt("FIRST LEGION ATTACK EAST FLANK!", 23));
        //System.out.println(cipher.encrypt("At noon be in the conference room with " +
        //          "your hat on for a surprise party. YELL LOUD!", 15));
        //System.out.println(cipher.encrypt("First Legion", 23));
        //System.out.println(cipher.encrypt("First Legion", 17));
        //System.out.println(cipher.encryptTwoKeys("First Legion", 23, 17));
        String message = "At noon be in the conference room with your hat " +
                "on for a surprise party. YELL LOUD!";
        int key1= 8;
        int key2 = 21;
        String encrypted = cipher.encrypt(message);
        //System.out.println("Key is " + key + "\n" + "Message is\n" + message
        //                        + "\nEncrypted Message is\n"  + encrypted);
    }
}