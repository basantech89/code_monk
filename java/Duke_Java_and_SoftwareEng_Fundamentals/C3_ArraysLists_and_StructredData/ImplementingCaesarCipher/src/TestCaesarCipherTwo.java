import edu.duke.*;

public class TestCaesarCipherTwo {

    public void simpleTests() {
        FileResource resource = new FileResource();
        //String message = resource.asString();
        String encrypted = resource.asString();
        //String message = "Can you imagine life WITHOUT the " +
        //                      "internet AND computers in your pocket?";
        //String encrypted = "Hfs cpwewloj loks cd Hoto kyg Cyy.";
        //String encrypted = "Aal uttx hm aal Qtct Fhljha pl Wbdl. Pvxvxlx!";
        CaesarCipherTwo cipherTwo = new CaesarCipherTwo(14, 24);
        //String encrypted = cipherTwo.encrypt(message);
        //System.out.println("The Message is\n" + message + "\nThe Encrypted message is\n"
          //      + encrypted);
        //System.out.println("The Decrypted message is\n" + cipherTwo.decrypt(encrypted));
        System.out.println("The Decrypted message is\n" + cipherTwo.breakCaesarCipher(encrypted));
    }

    public static void main(String[] args) {
        TestCaesarCipherTwo test = new TestCaesarCipherTwo();
        test.simpleTests();
    }
}
