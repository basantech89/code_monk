public class Part2 {

    public double cgRatio(String dna) {
        double cgs = 0.0;
        int startIndex = 0, indexcg = 0;
        while (indexcg != -1) {
            indexcg = findCG(dna, startIndex);
            if (indexcg != -1)
                ++cgs;
            startIndex = indexcg + 1;
        }
        return cgs / dna.length();
    }

    public int findCG(String dna, int where) {
        int indexc = dna.indexOf("C", where);
        int indexg = dna.indexOf("G", where);
        int minIndex = 0;
        if ((indexg == -1) || (indexc != -1) && (indexc < indexg))
            minIndex = indexc;
        else
            minIndex = indexg;
        return minIndex;
    }

    public int countCTG(String dna) {
        int ctgs = 0;
        int startIndex = 0, indexctg = 0;
        while (indexctg != -1) {
            indexctg = dna.indexOf("CTG", startIndex);
            if (indexctg != -1)
                ++ctgs;
            startIndex = indexctg + 1;
        }
        return ctgs;
    }

    public static void main (String[] args) {
        Part2 pt = new Part2();
        //System.out.println(pt.cgRatio("ATGCCATAG"));
        System.out.println(pt.countCTG("ATGCCTGCTGCCTGATAG"));
    }
}
