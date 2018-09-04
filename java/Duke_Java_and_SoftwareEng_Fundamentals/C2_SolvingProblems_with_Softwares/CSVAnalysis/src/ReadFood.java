/**
 * @author Basant
 * @version July 25, 2018
 * */

import edu.duke.*;
import org.apache.commons.csv.*;

public class ReadFood {
    public void readFood() {
        FileResource fr = new FileResource("foods.csv");
        CSVParser parser = fr.getCSVParser();
        for (CSVRecord record : parser) {
            System.out.print(record.get("Name") + " ");
            System.out.print(record.get("Fav Food") + " ");
            System.out.println(record.get("Fav Color"));
        }
    }

    public void listExports(CSVParser parser, String exportOfInterest) {
        // for each row in the CSV file
        for (CSVRecord record : parser) {
            // look at the exports column
            String export = record.get("Exports");
            // check if it contains exportOfInterest
            if (export.contains(exportOfInterest))
                // if so, write down the country from that row
                System.out.println(record.get("Country"));
        }
    }
    
    public void whoExportsItem(String item) {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        listExports(parser, item);
    }

    public static void main(String[] args) {
        ReadFood part = new ReadFood();
        //part.readFood();
        part.whoExportsItem("coffee");
    }
}
