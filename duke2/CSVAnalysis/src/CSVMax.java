import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class CSVMax {

    public CSVRecord hottestHourInFile(CSVParser parser) {
        // store with largestSoFar as nothing
        CSVRecord largestSoFar = null;
        // for each row in the CSV file
        for (CSVRecord currentRow : parser)
            largestSoFar = getLargestOfTwo(currentRow, largestSoFar);
        return largestSoFar;
    }

    public void testHottestDayInFile () {
        FileResource fr = new FileResource("/home/luv/IdeaProjects/duke1/CSVAnalysis/src/nc_weather/2015/weather-2015-01-01.csv");
        CSVRecord largest = hottestHourInFile(fr.getCSVParser());
        System.out.println("hottest temperature was " + largest.get("TemperatureF")
                + " at " + largest.get("TimeEST"));
    }

    public CSVRecord HottestInManyDays () {
        CSVRecord largestSoFar = null;
        DirectoryResource dr = new DirectoryResource();
        // iterate over files
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVRecord currentRow = hottestHourInFile(fr.getCSVParser());
            largestSoFar = getLargestOfTwo(currentRow, largestSoFar);
        }
        return largestSoFar;
    }

    public CSVRecord getLargestOfTwo (CSVRecord currentRow, CSVRecord largestSoFar) {
        if (largestSoFar == null)
            largestSoFar = currentRow;
        else {
            double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
            double largestTemp = Double.parseDouble(largestSoFar.get("TemperatureF"));
            // check if current temp is > largestSoFar's
            if (currentTemp > largestTemp)
                largestSoFar = currentRow;
        }
        return largestSoFar;
    }
    public void testHottestInManyDays () {
        CSVRecord largest = HottestInManyDays();
        System.out.println("hottest temperature was " + largest.get("TemperatureF") + " at "
                + largest.get("DateUTC"));
    }

    public static void main (String[] args) {
        CSVMax max = new CSVMax();
        //max.testHottestDayInFile();
        max.testHottestInManyDays();
    }
}
