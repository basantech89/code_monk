import edu.duke.FileResource;

public class Tester {

    private static String source = "testing/";
    private static String source2 = "messages/";

    public static String getPlain () {
        String file1 = "titus-small.txt";
        FileResource resource = new FileResource(source + file1);
        String plain = resource.asString();
        return plain;
    }

    public static String getEncrypted () {
        String file1 = "titus-small_key5.txt";
        String file2 = "oslusiadas_key17.txt";
        String file3 = "athens_keyflute.txt";
        String file4 = "secretmessage1.txt";
        FileResource resource = new FileResource(source2 + file4);
        String encrypted1 = resource.asString();
        String plain = getPlain();
        CaesarCipher cipher = new CaesarCipher(17);
        String encrypted = cipher.encrypt(plain);
        return encrypted1;
    }

    public void testCaesarCipher () {
        String encrypted = getEncrypted();
        CaesarCipher cipher = new CaesarCipher(17);
        String decrypted = cipher.decrypt(encrypted);
        System.out.println(decrypted);
    }

    public void testCaesarCracker () {
        String encrypted = getEncrypted();
        CaesarCracker cracker = new CaesarCracker('a');
        int key = cracker.getKey(encrypted);
        System.out.println("The key is " + key);
        String decrypted = cracker.decrypt(encrypted);
        System.out.println("The decrypted message is\n" + decrypted);
    }

    public void testVigenereCipher () {
        int[] rome = {17, 14, 12, 4};
        VigenereCipher cipher = new VigenereCipher(rome);
        String plain = getPlain();
        String encrypted = cipher.encrypt(plain);
        System.out.println("Encrypted message is\n" + encrypted);
        String decrypted = cipher.decrypt(encrypted);
        System.out.println("Decrypted message is\n" + decrypted);
    }

    public void testVigenereBreaker () {
        VigenereBreaker breaker = new VigenereBreaker();
        /*String slice = breaker.sliceString("abcdefghijklm", 0, 3);
        System.out.println(slice);
        String slice2 = breaker.sliceString("abcdefghijklm", 1, 3);
        System.out.println(slice2);
        String slice3 = breaker.sliceString("abcdefghijklm", 1, 4);
        System.out.println(slice3);
        String slice4 = breaker.sliceString("abcdefghijklm", 4, 5);
        System.out.println(slice4);*/
        //String encrypted = getEncrypted();
        //int[] key = breaker.tryKeyLength(encrypted, 4, 'e');
        //System.out.println(Arrays.toString(key));
        breaker.breakVigener();
    }

    public static void main (String[] args) {
        Tester test = new Tester();
        //test.testCaesarCipher();
        //test.testCaesarCracker();
        //test.testVigenereCipher();
        test.testVigenereBreaker();
    }
}
