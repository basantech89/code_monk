import edu.duke.*;
import org.apache.commons.csv.*;

public class A1ParsingExportData {

    public void tester () {
        FileResource fr = new FileResource("exportdata.csv");
        CSVParser parser = fr.getCSVParser();
        System.out.println(countryInfo(parser, "Nauru"));
        parser = fr.getCSVParser();
        listExportsTwoProducts(parser, "cotton", "flowers");
        parser = fr.getCSVParser();
        System.out.println(numberOfExports(parser, "cocoa"));
        parser = fr.getCSVParser();
        bigExports(parser, "$999,999,999,999");
    }

    public String countryInfo (CSVParser parser, String country) {
        for (CSVRecord record : parser) {
            if (record.get("Country").equals(country))
                return country + ": " + record.get("Exports") + ": "
                        + record.get("Value (dollars)");
        }
        return "NOT FOUND";
    }

    public void listExportsTwoProducts (CSVParser parser, String exportItem1, String exportItem2) {
        for (CSVRecord record : parser) {
            String export = record.get("Exports");
            if (export.contains(exportItem1) && export.contains(exportItem2))
                System.out.println(record.get("Country"));
        }
    }

    public int numberOfExports (CSVParser parser, String exportItem) {
        int exportingCountries = 0;
        for (CSVRecord record : parser) {
            String export = record.get("Exports");
            if (export.contains(exportItem))
                exportingCountries += 1;
        }
        return exportingCountries;
    }

    public void bigExports (CSVParser parser, String amount) {
        for (CSVRecord record : parser) {
            String value = record.get("Value (dollars)");
            if (value.length() > amount.length())
                System.out.println(record.get("Country") + " " + value);
        }
    }

    public static void main (String[] args) {
        A1ParsingExportData a1 = new A1ParsingExportData();
        a1.tester();
    }
}
