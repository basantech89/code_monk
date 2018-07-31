import edu.duke.*;

public class CaesarBreaker {

    public void eyeballDecrypt(String encrypted) {
        CaesarCipher cipher = new CaesarCipher();
        for (int i = 0; i < 26; i++) {
            String plain = cipher.encrypt(encrypted, i);
            System.out.println(i + "\t" + plain);
        }
    }

    public String decrypt(String encrypted) {
        CaesarCipher cipher = new CaesarCipher();
        int dkey = getKey(encrypted);
        return cipher.encrypt(encrypted, 26 - dkey);
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

    public String decryptTwoKeys(String encrypted) {
        String evenHalf = halfOfString(encrypted, 0);
        String oddHalf = halfOfString(encrypted, 1);
        int dkey1 = getKey(evenHalf);
        int dkey2 = getKey(oddHalf);
        System.out.println("decryption key 1 is " + dkey1 + " and decryption key 2 is " + dkey2);
        CaesarCipher cipher = new CaesarCipher();
        return cipher.encryptTwoKeys(encrypted, 26 - dkey1, 26 - dkey2);
    }

    public static void main (String[] args) {
        CaesarBreaker breaker = new CaesarBreaker();
        //String encrypted = "Io iwjv jz dv bcm kjvammmikz mwju edbc twpz pvb " +
        //                      "wi awm v ncmxmqnm xvzog. TMGT TJCY!";
        //System.out.println("Encrypted message was\n" + encrypted1);
        //breaker.eyeballDecrypt(encrypted1);
        //System.out.println("Decryption starts\n" + breaker.decrypt(encrypted1));
        //System.out.println(breaker.halfOfString("Qbkm Zgis", 1));
        String encrypted = "Gwpv c vbuq pvokki yfve iqqu qc bgbgbgbgbgbgbgbgbu";
        String encrypted1 = "Top ncmy qkff vi vguv vbg ycpx";
        String encrypted2 = "Akag tjw Xibhr awoa aoee xakex znxag xwko";
        FileResource resource = new FileResource();
        String encrypted3 = resource.asString();
        String decrypted = breaker.decryptTwoKeys(encrypted2);
        System.out.println("Encrypted message was\n" + encrypted2
                        + "\nDecrypted message is\n" + decrypted);
    }
}
