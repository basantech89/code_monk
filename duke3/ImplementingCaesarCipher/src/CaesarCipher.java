import edu.duke.*;

public class CaesarCipher {

    public String encrypt (String input, int key) {
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String shiftedAlphabet = alphabet.substring(key, 26) + alphabet.substring(0, key)
                            + alphabet.substring(key + 26, 52) + alphabet.substring(26, key + 26);
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

    public String encryptTwoKeys (String input, int key1, int key2) {

        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String shiftedAlphabet1 = alphabet.substring(key1, 26) + alphabet.substring(0, key1)
                + alphabet.substring(key1 + 26, 52) + alphabet.substring(26, key1 + 26);
        String shiftedAlphabet2 = alphabet.substring(key2, 26) + alphabet.substring(0, key2)
                + alphabet.substring(key2 + 26, 52) + alphabet.substring(26, key2 + 26);
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

    public static void main (String[] args) {
        CaesarCipher cipher = new CaesarCipher();
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
        String encrypted = cipher.encryptTwoKeys(message, key1, key2);
        System.out.println("Key is " + key + "\n" + "Message is\n" + message
                                + "\nEncrypted Message is\n"  + encrypted);
    }
}