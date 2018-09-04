public class Part2 {

    //public final static String startCodon = "ATG";
    //public final static String stopCodon = "TAA";

    public String findSimpleGene(String dna, String startCodon, String stopCodon) {
        // Index of start codon
        int startIndex = dna.indexOf(startCodon);
        if (startIndex == -1) return "";    // no ATG
        int stopIndex = dna.indexOf(stopCodon, startIndex + 3);
        if (stopIndex == -1) return ""; // no TAA
        if ((stopIndex - startIndex) % 3 == 0)  // check for valid Gene
            return dna.substring(startIndex, stopIndex + 3);
        return "";
    }

    public void testSimpleGene() {
        String dna1 = "ATCTAGCTCTAA";   // no ATG
        String dna2 = "CTATGATACGA";    // no TAA
        String dna3 = "ATCGTACGGAT";    // no ATG or TAA
        String dna4 = "ATCATGGATGTAA";  // both ATG and TAA
        String dna5 = "ATGCTAGATTAA";   // both ATG and TAA with substring of multiple of 3
        String dna6 = "ATGCGAGTAA";     // both ATG and TAA with substring not multiple of 3
        // For each DNA string
        System.out.println("DNA " + dna1);
        System.out.println(" Gene " + findSimpleGene(dna1, "ATG", "TAA"));
        System.out.println("DNA " + dna2);
        System.out.println(" Gene " + findSimpleGene(dna2, "ATG", "TAA"));
        System.out.println("DNA " + dna3);
        System.out.println(" Gene " + findSimpleGene(dna3, "ATG", "TAA"));
        System.out.println("DNA " + dna4);
        System.out.println(" Gene " + findSimpleGene(dna4, "ATG", "TAA"));
        System.out.println("DNA " + dna5);
        System.out.println(" Gene " + findSimpleGene(dna5, "ATG", "TAA"));
        System.out.println("DNA " + dna6);
        System.out.println(" Gene " + findSimpleGene(dna6, "ATG", "TAA"));
    }

    public static void main(String[] args) {
        Part1 pt1 = new Part1();
        pt1.testSimpleGene();
    }
}
