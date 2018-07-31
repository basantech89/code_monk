public class Part3 {

    public int findStopCodon(String dna, int startIndex, String stopCodon) {
        int currIndex = dna.indexOf(stopCodon, startIndex + 3);
        while (currIndex != -1) {
            if ((currIndex - startIndex) % 3 == 0) return currIndex;
            currIndex = dna.indexOf(stopCodon, ++currIndex);
        }
        return -1;
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

    public int countGenes(String dna) {
        int startIndex = 0, occurrences = 0;
        while (true) {
            String currGene = findGene(dna, startIndex);
            if (currGene.isEmpty()) {
                break;
            }
            //System.out.println(currGene);
            ++occurrences;
            startIndex = dna.indexOf(currGene, startIndex) + currGene.length();
        }
        return occurrences;
    }

    public void testCountGene() {
        System.out.println(countGenes("ATGTAAGATGCCCTAGT"));
        System.out.println(countGenes("ATGATCTAATTTATGCTGCAACGGTGAAGA"));
        System.out.println(countGenes("ATGATCATAAGAAGATAATAGAGGGCCATGTAA"));
        System.out.println(countGenes(""));
    }

    public static void main (String[] args) {
        Part3 pt = new Part3();
        pt.testCountGene();
    }
}
