import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class A2ParsingWeatherData {

    public CSVRecord coldestHourInFile(CSVParser parser) {
        // store with lowestSoFar as nothing
        CSVRecord lowestSoFar = null;
        // for each row in the CSV file
        for (CSVRecord currentRow : parser) {
            if (Double.parseDouble(currentRow.get("TemperatureF")) != -9999.0)
                lowestSoFar = getLowestOfTwo(currentRow, lowestSoFar);
        }
        return lowestSoFar;
    }

    public void testColdestHourInFile () {
        FileResource fr = new FileResource();
        CSVRecord lowest = coldestHourInFile(fr.getCSVParser());
        System.out.println("coldest temperature was " + lowest.get("TemperatureF")
                + " at " + lowest.get("DateUTC"));
    }

    public CSVRecord ColdestInManyDays () {
        CSVRecord lowestSoFar = null;
        DirectoryResource dr = new DirectoryResource();
        // iterate over files
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVRecord currentRow = coldestHourInFile(fr.getCSVParser());
            lowestSoFar = getLowestOfTwo(currentRow, lowestSoFar);
        }
        return lowestSoFar;
    }

    public CSVRecord getLowestOfTwo (CSVRecord currentRow, CSVRecord lowestSoFar) {
        if (lowestSoFar == null)
            lowestSoFar = currentRow;
        else {
            double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
            double lowestTemp = Double.parseDouble(lowestSoFar.get("TemperatureF"));
            // check if current temp is < lowestSoFar's
            if (currentTemp < lowestTemp)
                lowestSoFar = currentRow;
        }
        return lowestSoFar;
    }

    public CSVRecord lowestHumidityInFile(CSVParser parser) {
        // store with lowestHumidity as nothing
        CSVRecord lowestHumidity = null;
        // for each row in the CSV file
        for (CSVRecord currentRow : parser) {
            if (!currentRow.get("Humidity").equals("N/A"))
                lowestHumidity = getLowestHumidityOfTwo(currentRow, lowestHumidity);
        }
        return lowestHumidity;
    }

    public CSVRecord getLowestHumidityOfTwo (CSVRecord currentRow, CSVRecord lowestSoFar) {
        if (lowestSoFar == null) {
            lowestSoFar = currentRow;
        } else {
            int currentHumidity = Integer.parseInt(currentRow.get("Humidity"));
            int lowestHumidity = Integer.parseInt(lowestSoFar.get("Humidity"));
            // check if current humidity is < lowestSoFar's
            if (currentHumidity < lowestHumidity)
                lowestSoFar = currentRow;
        }
        return lowestSoFar;
    }

    public void testLowestHumidityInFile() {
        FileResource fr = new FileResource();
        CSVRecord csv = lowestHumidityInFile(fr.getCSVParser());
        System.out.println("Lowest Humidity was " + csv.get("Humidity") + " at "
                            + csv.get("DateUTC"));
    }

    public CSVRecord lowestHumidityInManyFiles () {
        CSVRecord lowestSoFar = null;
        DirectoryResource dr = new DirectoryResource();
        // iterate over files
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVRecord currentRow = lowestHumidityInFile(fr.getCSVParser());
            lowestSoFar = getLowestHumidityOfTwo(currentRow, lowestSoFar);
        }
        return lowestSoFar;
    }

    public void testLowestHumidityInManyFiles() {
        CSVRecord lowestHumidity = lowestHumidityInManyFiles();
        System.out.println("Lowest Humidity was " + lowestHumidity.get("Humidity")
                                + " at " + lowestHumidity.get("DateUTC"));
    }

    public double averageTemperatureInFile (CSVParser parser) {
        double sum = 0.0;
        int lines = 0;
        // for each row in the CSV file
        for (CSVRecord currentRow : parser) {
            lines += 1;
            double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
            if (currentTemp != -9999.0)
                sum += currentTemp;
        }
        return sum / lines;
    }

    public void testAverageTemperatureInFile () {
        FileResource fr = new FileResource();
        double avg = averageTemperatureInFile(fr.getCSVParser());
        System.out.println("Average temperature in file is " + avg);
    }

    public double averageTemperatureWithHighHumidityInFile (CSVParser parser, int value) {
        double sum = 0.0;
        int lines = 0;
        // for each row in the CSV file
        for (CSVRecord currentRow : parser) {
            if (Integer.parseInt(currentRow.get("Humidity")) >= value) {
                lines += 1;
                double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                if (currentTemp != -9999.0)
                    sum += currentTemp;
            }
        }
        if (lines == 0) return 0.0;
        return sum / lines;
    }

    public void testAverageTemperatureWithHighHumidityInFile () {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        double avg = averageTemperatureWithHighHumidityInFile(parser, 80);
        if (avg == 0.0)
            System.out.println("No temperature with that humidity");
        else
            System.out.println("Average temp with high humidity is " + avg);
    }

    public String fileWithColdestTemperature () {
        CSVRecord lowestSoFar = null;
        File fname = null;
        DirectoryResource dr = new DirectoryResource();
        // iterate over files
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVRecord currentRow = coldestHourInFile(fr.getCSVParser());
            if (lowestSoFar == null)
                lowestSoFar = currentRow;
            else {
                double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                double lowestTemp = Double.parseDouble(lowestSoFar.get("TemperatureF"));
                // check if current temp is < lowestSoFar's
                if (currentTemp < lowestTemp) {
                    lowestSoFar = currentRow;
                    fname = f;
                }
            }
        }
        printColdestDay(fname, lowestSoFar);
        return fname.getName();
    }

    public void printColdestDay (File fname, CSVRecord lowestSoFar) {
        System.out.println("Coldest day was in file " + fname.getName());
        System.out.println("Coldest temperature on that day was " +
                lowestSoFar.get("TemperatureF"));
        System.out.println("All the temperatures on the coldest day were:");
        FileResource fr = new FileResource(fname);
        for (CSVRecord record : fr.getCSVParser())
            System.out.println(record.get("DateUTC") + " " + record.get("TemperatureF"));
    }


    public void testColdestInManyDays () {
        CSVRecord lowest = ColdestInManyDays();
        System.out.println("coldest temperature was " + lowest.get("TemperatureF") + " at "
                + lowest.get("DateUTC"));
    }

    public static void main (String[] args) {
        A2ParsingWeatherData min = new A2ParsingWeatherData();
        //min.testColdestDayInFile();
        min.testColdestInManyDays();
        //min.fileWithColdestTemperature();
        //min.testLowestHumidityInFile();
        //min.testLowestHumidityInManyFiles();
        //min.testAverageTemperatureInFile();
        //min.testAverageTemperatureWithHighHumidityInFile();
    }
}
