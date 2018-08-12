
/**
 * Write a description of class Tester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class Tester {

    private String source = "data";

    public void testLogEntry() {
        LogEntry le = new LogEntry("1.2.3.4", new Date(), "example request", 200, 500);
        System.out.println(le);
        LogEntry le2 = new LogEntry("1.2.100.4", new Date(), "example request 2", 300, 400);
        System.out.println(le2);
    }
    
    public void testLogAnalyzer() {
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile(source + "/short-test_log");
        analyzer.printAll();
    }

    public void testUniqueIPs () {
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile(source + "/short-test_log");
        System.out.println("Unique IPs are " + analyzer.containUniqueIPs());
    }

    public void testOthers() {
        LogAnalyzer analyzer = new LogAnalyzer();
        //analyzer.readFile(source + "/weblog3-short_log");
        analyzer.readFile(source + "/weblog1_log");
        //analyzer.printAllHigherThanNum(400);
        //System.out.println(analyzer.countUniqueIPsInRange(200, 299));
        //System.out.println(analyzer.uniqueIPVisitsOnDay("Mar 24").size());
        //System.out.println(analyzer.countVisitsPerIP());
        //HashMap<String, Integer> counts = analyzer.countVisitsPerIP();
        //System.out.println(analyzer.mostNumberVisitsByIP(counts));
        //System.out.println(analyzer.iPsMostVisits(counts));
        //System.out.println(analyzer.iPsForDays());
        HashMap<String, ArrayList<String>> iPdays = analyzer.iPsForDays();
        //System.out.println(analyzer.dayWithMostIPVisits(iPdays));
        System.out.println(analyzer.iPsWithMostVisitsOnDay(iPdays, "Mar 17"));
    }

    public static void main (String[] args) {
        Tester test = new Tester();
        //test.testLogAnalyzer();
        //test.testUniqueIPs();
        test.testOthers();
    }
}
