public class Part3 {

    public boolean twoOccurences(String stringa, String stringb) {
        if (stringb.indexOf(stringa, stringb.indexOf(stringa) + 1) != -1) return true;
        /*int firstOccurence = stringb.indexOf(stringa);
        if (firstOccurence != -1) {
            int secondOccurrence = stringb.indexOf(stringa, firstOccurence + 1);
            if (secondOccurrence != -1) {
                //System.out.println(firstOccurence + ", " + secondOccurrence);
                return true;
            }
        }*/
        return false;
    }

    public void testing() {
        System.out.println(twoOccurences("by", "A story by Abby Long"));
        System.out.println(twoOccurences("a", "banana"));
        System.out.println(twoOccurences("atg", "ctgtatgta"));
        System.out.println(twoOccurences("c", "abcdefgah"));
        System.out.println(lastPart("an", "banana"));
        System.out.println(lastPart("zoo", "forest"));
            System.out.println(lastPart("cdef", "abcdefghijkl"));
    }

    public String lastPart(String stringa, String stringb) {
        int index = stringb.indexOf(stringa);
        if (index != -1) return stringb.substring(index + stringa.length(), stringb.length());
        return stringb;
    }

    public static void main(String[] args) {
        Part3 pt = new Part3();
        pt.testing();
    }
}
