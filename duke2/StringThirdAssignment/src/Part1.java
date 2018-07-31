import edu.duke.*;

public class Part1 {
    public int findStopCodon(String dna, int startIndex, String stopCodon) {
        int currIndex = dna.indexOf(stopCodon, startIndex + 3);
        while (currIndex != -1) {
            if ((currIndex - startIndex) % 3 == 0) return currIndex;
            currIndex = dna.indexOf(stopCodon, ++currIndex);
        }
        return -1;
    }

    public void testFindStopCodon() {
        String dna1 = "ATCTAGCTCTAA";   // no ATG
        String dna2 = "CTATGATACGA";    // no TAA
        String dna3 = "ATCGTACGGAT";    // no ATG or TAA
        String dna4 = "ATCATGGATGTAA";  // both ATG and TAA
        String dna5 = "ATGCTAGATTAA";   // both ATG and TAA with substring of multiple of 3
        String dna6 = "ATGCGAGTAA";     // both ATG and TAA with substring not multiple of 3
        String dna7 = "AAATGCCCTAACTAGATTAAGAAACC";
        String dna8 = "CTGATGAGTCGCTAA";    // both ATG and TAA with substring of multiple of 3

        System.out.println(findStopCodon(dna1, 2, "TAA"));
        System.out.println(findStopCodon(dna2, 0, "TAA"));
        System.out.println(findStopCodon(dna3, 0, "TAA"));
        System.out.println(findStopCodon(dna4, 3, "TAA"));
        System.out.println(findStopCodon(dna5, 0, "TAA"));
        System.out.println(findStopCodon(dna6, 0, "TAA"));
        System.out.println(findStopCodon(dna7, 2, "TAA"));
        System.out.println(findStopCodon(dna8, 3, "TAA"));

    }

    public String findGene(String dna, int where) {
        int atgIndex = dna.indexOf("ATG", where);
        if (atgIndex == -1) return "";
        int taaIndex = findStopCodon(dna, atgIndex, "TAA");
        int tagIndex = findStopCodon(dna, atgIndex, "TAG");
        int tgaIndex = findStopCodon(dna, atgIndex, "TGA");
        int minIndex = 0;
        if (taaIndex == -1 || (tgaIndex != -1 && tgaIndex < taaIndex))
            minIndex = tgaIndex;
        else
            minIndex = taaIndex;
        if (minIndex == -1 || (tagIndex != -1 && tagIndex < minIndex))
            minIndex = tagIndex;
        if (minIndex == -1) return "";
        return dna.substring(atgIndex, minIndex + 3);
    }

    public void testFindGene() {
        String dna1 = "ERGHIELRGHERLI"; // no ATG
        String dna2 = "DGATGMVNFHGDTEGHYTAAJGJG"; // ATG with one valid codon at index 17
        String dna3 = "FVATGLKJTAGVNFJUITAAVGBVHJJDBTGA";   // ATG with three valid codon, min at 8
        String dna4 = "DVATGNJKJGTTAJNHFGHTAG"; // ATG with no valid codon
        String dna5 = "GDATGVNGJGKIRYTAGJKFTAAHFTAG"; // ATG with one valid codon at 14
        System.out.println("dna 1 " + dna1);
        System.out.println(findGene(dna1, 4));
        System.out.println("dna 2 " + dna2);
        System.out.println(findGene(dna2, 2));
        System.out.println("dna 3 " + dna3);
        System.out.println(findGene(dna3, 2));
        System.out.println("dna 5 " + dna5);
        System.out.println(findGene(dna5, 2));
        System.out.println("dna 4 " + dna4);
        System.out.println(findGene(dna4, 2));
    }

    public void printAllGenes(String dna) {
        int startIndex = 0;
        while (true) {
            String currGene = findGene(dna, startIndex);
            if (currGene.isEmpty()) {
                break;
            }
            System.out.println(currGene);
            startIndex = dna.indexOf(currGene, startIndex) + currGene.length();
        }
    }

    public StorageResource getAllGenes(String dna) {
        StorageResource sr = new StorageResource();
        int startIndex = 0;
        while (true) {
            String currGene = findGene(dna, startIndex);
            if (currGene.isEmpty()) {
                break;
            }
            sr.add(currGene);
            startIndex = dna.indexOf(currGene, startIndex) + currGene.length();
        }
        return sr;
    }

    public void testOn(String dna) {
        System.out.println("Testing printAllGenes on " + dna);
        //printAllGenes(dna);
        StorageResource sr = getAllGenes(dna);
        for (String gene : sr.data())
            System.out.println(gene);
    }

    public static void main(String[] args) {
        Part1 pt = new Part1();
        //pt.testFindStopCodon();
        //pt.testFindGene();
        pt.testOn("ATGATCTAATTTATGCTGCAACGGTGAAGA");
        pt.testOn("");
        pt.testOn("ATGATCATAAGAAGATAATAGAGGGCCATGTAA");
        pt.testOn("AATGCTAACTAGCTGACTAAT");
    }
}
