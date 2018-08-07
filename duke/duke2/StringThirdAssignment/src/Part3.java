import edu.duke.FileResource;
import edu.duke.StorageResource;

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

    public void processGenes(StorageResource sr) {

        int lenGenes = 0, cgGenes = 0, longestGeneLength = 0;
        String longestGene = "";
        int longest = 60, totalGenes = 0;
        for (String gene : sr.data()) {
            ++totalGenes;
            int geneLength = gene.length();
            if (geneLength > longest) {
                System.out.println("Gene of length greater than " + longest + " is " + gene);
                ++lenGenes;
            }
            if (cgRatio(gene) > 0.35) {
                System.out.println("Gene with C-G ratio higher then 0.35 " + gene);
                ++cgGenes;
            }
            if (geneLength > longestGeneLength) {
                longestGeneLength = geneLength;
                longestGene = gene;
            }
        }
        System.out.println("Genes whose length is greater then " + longest + " are " + lenGenes);
        System.out.println("Genes whose C-G ratio is higher then 0.35 are " + cgGenes);
        System.out.println("Longest gene is " + longestGene);
        System.out.println("Length of the longest gene is " + longestGene.length());
        System.out.println("Total Genes in this DNA are " + totalGenes);
    }

    public void testProcessGenes() {
        String dna1 = "ATGJIKLOYTHGTAGATGJRGTAAATGMCNFRJPLRKGJNDHTAG"; // genes length > 9 = 2
        String dna2 = "ATGTAGATGTAA"; // no gene > 9
        String dna3 = "ATGCCCGHYJDKGGCCCCTAAATGGGGCCCGGGTAGATGKFJCCCGUICCIGCC"; // higher cg ratio
        String dna4 = "ATGHFJITKTAAATGFHDCGCHFJSTHGCTGAATGSGHDCJCGHTAG"; // lower CG ratio
        String dna5 = "ATGJFHDVHNFJTAG";
        FileResource fr = new FileResource("brca1line.fa");
        String dna = fr.asString().toUpperCase();
        FileResource fr1 = new FileResource("GRch38dnapart.fa");
        String dna6 = fr1.asString().toUpperCase();
        StorageResource sr1 = getAllGenes(dna1);
        StorageResource sr2 = getAllGenes(dna2);
        StorageResource sr3 = getAllGenes(dna3);
        StorageResource sr4 = getAllGenes(dna4);
        StorageResource sr5 = getAllGenes(dna5);
        StorageResource sr6 = getAllGenes(dna);
        StorageResource sr7 = getAllGenes(dna6);
        /*System.out.println("Processing for DNA " + dna1);
        processGenes(sr1);
        System.out.println("Processing for DNA " + dna2);
        processGenes(sr2);
        System.out.println("Processing for DNA " + dna3);
        processGenes(sr3);
        System.out.println("Processing for DNA " + dna4);
        processGenes(sr4);
        System.out.println("Processing for DNA " + dna5);
        processGenes(sr5);
        System.out.println("Processing for the long DNA");
        processGenes(sr6);*/
        processGenes(sr7);
        System.out.println("The Codon CTG appears " + countCTG(dna6) + " times in this DNA");
    }

    public void testOn(String dna) {
        System.out.println("Testing printAllGenes on " + dna);
        //printAllGenes(dna);
        StorageResource sr = getAllGenes(dna);
        for (String gene : sr.data())
            System.out.println(gene);
    }

    public static void main(String[] args) {
        Part3 pt = new Part3();
        //pt.testFindStopCodon();
        //pt.testFindGene();
        //pt.testOn("ATGATCTAATTTATGCTGCAACGGTGAAGA");
        //pt.testOn("");
        //pt.testOn("ATGATCATAAGAAGATAATAGAGGGCCATGTAA");
        //pt.testOn("AATGCTAACTAGCTGACTAAT");
        pt.testProcessGenes();
    }

}
