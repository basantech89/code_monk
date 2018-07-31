import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class BabyNames {

    public static final String fpath = "/home/luv/IdeaProjects/duke1/" +
            "MiniProjectBabyNames/src/us_babynames/us_babynames_by_year/";
    public static final String fpath_small = "/home/luv/IdeaProjects/duke1/" +
            "MiniProjectBabyNames/src/us_babynames_small/";

    public int getRank (String name, int year, String gender) {
        int rank = 0;
        // File to choose
        String fname = fpath + "yob" + year + ".csv";
        FileResource fr = new FileResource(fname);
        // For every record in csv file
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) {
                // Start ranking for given gender
                rank += 1;
                // IF found the given name in the csv file, return the rank
                if (rec.get(0).equals(name)) return rank;
            }
        }
        return -1; // the name is not in the csv file
    }

    public String getName (int rank, int year, String gender) {
        int line = 0;
        String fname = fpath + "yob" + year + ".csv";
        FileResource fr = new FileResource(fname);
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) {
                line += 1;
                if (rank == line) return rec.get(0);
            }
        }
        return "NO NAME";
    }

    public void whatIsNameInYear (String name, int year, int newYear, String gender) {
        int rank = getRank(name, year, gender);
        if (rank != -1) {
            String newName = getName(rank, newYear, gender);
            if (newName.equals("NO NAME"))
                System.out.println(" Rank " + rank + " is not found for "
                        + name + " in year " + newYear);
            else
                System.out.println(name + " born in " + year + " would be " + newName
                                    + " if she was born in " + newYear);
        }
        else
            System.out.println("No Rank is found for Name " + name);
    }

    public int getRank (String name, String gender, FileResource fr) {
        int rank = 0;
        // For every record in csv file
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) {
                // Start ranking for given gender
                rank += 1;
                // IF found the given name in the csv file, return the rank
                if (rec.get(0).equals(name)) return rank;
            }
        }
        return -1; // the name is not in the csv file
    }

    public int yearOfHighestRank (String name, String gender) {
        int highestRank = 0;
        File fname = null;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            int currRank = getRank(name, gender, fr);
            if (currRank != -1) {
                if (highestRank == 0 || highestRank > currRank) {
                    highestRank = currRank;
                    fname = f;
                }
            }
        }
        if (highestRank == 0) {
            System.out.println("Name " + name + " and Gender " + gender
                    + " are not in any selected files");
            return -1;
        }
        else {
            int year = Integer.parseInt(fname.getName().substring(3, 7));
            System.out.println("The year of highest rank for " + name + " is " + year
                                + " and the rank is " + highestRank);
            return year;
        }
    }

    public double getAverageRank (String name, String gender) {
        int sumRanks = 0, files = 0;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            int currRank = getRank(name, gender, fr);
            if (currRank != -1)
                sumRanks += currRank;
            files += 1;
        }
        if (sumRanks == 0) return -1.0;
        return (double)sumRanks / files;
    }

    public int getTotalBirthsRankedHigher (String name, int year, String gender) {
        int birthsRankedHigher = 0;
        int givenRank = getRank(name, year, gender);
        if (givenRank != -1) {
            String fname = fpath + "yob" + year + ".csv";
            FileResource fr = new FileResource(fname);
            for (CSVRecord rec : fr.getCSVParser(false)) {
                if (rec.get(1).equals(gender))
                    if (givenRank > getRank(rec.get(0), year, gender))
                        birthsRankedHigher += Integer.parseInt(rec.get(2));
            }
            System.out.println("Total births with rank greater then " + name + " having " +
                    "rank " + givenRank + " are " + birthsRankedHigher);
            return birthsRankedHigher;
        }
        else
            System.out.println("Give parameter " + name + " has no rank");
        return -1;
    }

    public void totalBirths (FileResource fr) {
        int totalBirths = 0, totalBoys = 0, totalGirls = 0;
        for (CSVRecord record : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(record.get(2));
            totalBirths += numBorn;
            if (record.get(1).equals("M"))
                totalBoys += numBorn;
            else
                totalGirls += numBorn;
        }
        System.out.println("Total Births in this year " + totalBirths);
        System.out.println("Total boys born in this year " + totalBoys);
        System.out.println("Total girls born in this year " + totalGirls);
    }

    public void tester() {
        FileResource fr = new FileResource();
        totalBirths(fr);
        //System.out.println(getRank("Frank", 1971, "M"));
        //System.out.println(getName(450, 1982, "M"));
        //whatIsNameInYear("Owen", 1974, 2014, "M");
        //yearOfHighestRank("Mich", "M");
        //System.out.println("Average rank is " + getAverageRank("Robert", "M"));
        //getTotalBirthsRankedHigher("Drew", 1990, "M");
    }

    public void readOneFile (int year) {
        String fname = "data/yob" + year + ".txt";
        FileResource fr= new FileResource();
        for (CSVRecord record : fr.getCSVParser(false)) {

        }
    }

    public static void main (String[] args) {
        BabyNames names = new BabyNames();
        names.tester();
    }

}
