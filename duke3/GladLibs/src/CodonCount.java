import edu.duke.FileResource;

import java.util.HashMap;
import java.util.Map;

public class CodonCount {

    private Map<String, Integer> codonCounts;

    public CodonCount () {
        codonCounts = new HashMap<>();
    }

    public void buildCodon (int start, String dna) {
        codonCounts.clear();
        // read a new reading frame on each iteration
        for (int i = start; i <= dna.length() - 3; i += 3) {
            String key = dna.substring(i, i + 3);
            if (!codonCounts.containsKey(key))
                codonCounts.put(key, 1);
            else
                codonCounts.put(key, codonCounts.get(key) + 1);
        }
    }

    public String getMostCommonCodon () {
        int max = 0;
        String commonCodon = "";
        for (String codon : codonCounts.keySet())
            if (codonCounts.get(codon) > max) {
                max = codonCounts.get(codon);
                commonCodon = codon;
            }
        return commonCodon;
    }

    public void printCodonCount (int start, int end) {
        for (Map.Entry<String, Integer> codon : codonCounts.entrySet())
            if (codon.getValue() >= start && codon.getValue() <= end)
                System.out.println("codon " + codon.getKey() + " counts " + codon.getValue());
    }

    public void tester () {
        FileResource resource = new FileResource();
        String dna = resource.asString().toUpperCase().trim();
        for (int i = 0; i <= 2; i++) {
            buildCodon(i, dna);
            String commonCodon = getMostCommonCodon();
            System.out.println("Reading frame starting with " + i + " result in "
                    + codonCounts.size() + " unique codons\nand most common codon is "
                    + commonCodon + " with count " + codonCounts.get(commonCodon));
            System.out.println("Counts of codons between 1 and 5 inclusive are");
            printCodonCount(7, 7);
            System.out.println("\n");
        }
    }

    public static void main (String[] args) {
        CodonCount codon = new CodonCount();
        codon.tester();
    }

}
