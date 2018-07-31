public class TestCaesarCipherOO {

    public void simpleTests() {
        //FileResource resource = new FileResource();
        //String message = resource.asString();
        String message1 = "Can you imagine life WITHOUT the internet AN" +
                "D computers in your pocket?";
        CaesarCipherOO cipher = new CaesarCipherOO(15);
        System.out.println("The message is\n" + message1);
        String encrypted = cipher.encrypt(message1);
        System.out.println("Encrypted message is\n" + encrypted);
        System.out.println("Decrypted message is\n" + cipher.decrypt(encrypted));
    }

    public static void main (String[] args) {
        TestCaesarCipherOO test = new TestCaesarCipherOO();
        test.simpleTests();
    }

}
