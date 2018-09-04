import edu.duke.FileResource;

public class VigenereBreakerKnownKeyLengthLanguage {

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
        int[] key = tryKeyLength(encrypted, 5, 'e');
        VigenereCipher cipher = new VigenereCipher(key);
        String decrypted = cipher.decrypt(encrypted);
        System.out.println("The decrypted message is\n" + decrypted);
    }
}
