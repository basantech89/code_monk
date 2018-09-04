

public class WordPlay {

    public boolean isVowel (char ch) {
        String c = Character.toString(Character.toLowerCase(ch));
        String vowels = "AEIOUaeiou";
        return vowels.contains(c);
    }

    public StringBuilder replaceVowels (String phrase, char ch) {
        StringBuilder vphrase = new StringBuilder(phrase); // phrase with vowels
        for (int i = 0; i < phrase.length(); i++)
            if (isVowel(phrase.charAt(i))) vphrase.replace(i, i+1, "*");
        return vphrase;
    }

    public StringBuilder emphasize (String phrase, char ch) {
        StringBuilder vpharse = new StringBuilder(phrase);
        for (int i = 0; i < phrase.length(); i++) {
            if (Character.toLowerCase(vpharse.charAt(i)) == Character.toLowerCase(ch))
                if (i % 2 == 0) vpharse.replace(i, i+1, "*");
                else vpharse.replace(i, i+1, "+");
        }
        return vpharse;
    }

    public static void main (String[] args) {
        WordPlay play = new WordPlay();
        //System.out.println(play.isVowel('F'));
        //System.out.println(play.replaceVowels("HEllO World", '*'));
        //System.out.println(play.emphasize("dna ctgaaactga", 'a'));
        System.out.println(play.emphasize("Mary Bella Abracadabra", 'a'));
    }
}
