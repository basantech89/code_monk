public class Part2 {

    public int howMany(String stringa, String stringb) {
        int currIndex = 0, occurrences = 0;
        while (true) {
            currIndex = stringb.indexOf(stringa, currIndex);
            if (currIndex == -1) break;
            occurrences += 1;
            currIndex += stringa.length();
        }
        return occurrences;
    }

    public void testHowMany() {
        System.out.println(howMany("GAA", "ATGAACGAATTGAATC"));
        System.out.println(howMany("AA", "ATAAAA"));
    }

    public static void main (String[] args) {
        Part2 pt = new Part2();
        pt.testHowMany();
    }
}
