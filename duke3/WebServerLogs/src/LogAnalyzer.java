
/**
 * Write a description of class LogAnalyzer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.FileResource;

import java.util.ArrayList;
import java.util.HashMap;

public class LogAnalyzer
{
     private ArrayList<LogEntry> records;
     
     public LogAnalyzer() {
         records = new ArrayList<>();
     }
        
     public void readFile(String filename) {
         FileResource resource = new FileResource(filename);
         for (String line : resource.lines())
             records.add(WebLogParser.parseEntry(line));
     }

     public int containUniqueIPs () {
         ArrayList<String> uniqueIPs = new ArrayList<>();
         for (LogEntry entry : records) {
             String ip = entry.getIpAddress();
             if (!uniqueIPs.contains(ip))
                 uniqueIPs.add(ip);
         }
         return uniqueIPs.size();
     }

     public void printAllHigherThanNum (int num) {
         ArrayList<Integer> uniqueCodes = new ArrayList<>();
         for (LogEntry entry : records) {
             int code = entry.getStatusCode();
             if (entry.getStatusCode() > num) {
                 System.out.println(entry);
                 if (!uniqueCodes.contains(code))
                 uniqueCodes.add(code);
             }
         }
         System.out.println(uniqueCodes);
     }

     public ArrayList<String> uniqueIPVisitsOnDay (String someday) {
         ArrayList<String> uniqueIPs = new ArrayList<>();
         for (LogEntry entry : records) {
             String ip = entry.getIpAddress();
             if (!uniqueIPs.contains(ip)) {
                 getUniqueIPsOnDay(entry, uniqueIPs, someday, ip);
             }
         }
         return uniqueIPs;
     }

     private void getUniqueIPsOnDay (LogEntry entry, ArrayList<String> uniqueIPs,
                                     String someday, String ip) {
         // get the time of access
         String time = entry.getAccessTime().toString();
         // check if that time matches someday
         // will match time in MMM DD format
         if (time.substring(4, 10).equals(someday))
             uniqueIPs.add(ip);
     }

     public int countUniqueIPsInRange (int low, int high) {
         int ips = 0;
         ArrayList<String> uniqueIPs = new ArrayList<>();
         for (LogEntry entry : records) {
             int statusCode = entry.getStatusCode();
             if (statusCode >= low && statusCode <= high)
                 ips = processUniqueIPsInRange(entry, uniqueIPs, ips);
         }
         return ips;
     }

     private int processUniqueIPsInRange (LogEntry entry, ArrayList<String> uniqueIPs,
                                           int ips) {
         String ip = entry.getIpAddress();
         if (!uniqueIPs.contains(ip)) {
             uniqueIPs.add(ip);
             ips += 1;
         }
         return ips;
     }

     public HashMap<String, Integer> countVisitsPerIP () {
         HashMap<String, Integer> counts = new HashMap<>();
         for (LogEntry entry : records) {
             String ip = entry.getIpAddress();
             if (!counts.containsKey(ip)) counts.put(ip, 1);
             else counts.put(ip, counts.get(ip) + 1);
         }
         return counts;
     }

     public int mostNumberVisitsByIP (HashMap<String, Integer> counts) {
         int max = 0;
         for (String ip : counts.keySet()) {
             int visits = counts.get(ip);
             if (visits > max) max = visits;
         }
         return max;
     }

     public ArrayList<String> iPsMostVisits (HashMap<String, Integer> counts) {
         ArrayList<String> mostVisitedIPs = new ArrayList<>();
         int max = mostNumberVisitsByIP(counts);
         for (String ip : counts.keySet())
             if (counts.get(ip) == max)
                 mostVisitedIPs.add(ip);
         return mostVisitedIPs;
     }

     public HashMap<String, ArrayList<String>> iPsForDays () {
         HashMap<String, ArrayList<String>> iPDays = new HashMap<>();
         for (LogEntry entry : records) {
             // get the day for the entry in "MMM DD" format
             String day = entry.getAccessTime().toString().substring(4, 10);
             String ip = entry.getIpAddress();
             processIPsForDays(iPDays, day, ip);
         }
         return iPDays;
     }

     private void processIPsForDays(HashMap<String, ArrayList<String>> iPDays,
                                    String day, String ip) {
         if (!iPDays.containsKey(day)) {
             ArrayList<String> ips = new ArrayList<>();
             ips.add(ip);
             iPDays.put(day, ips);
         }
         else
             iPDays.get(day).add(ip);
     }

     public String dayWithMostIPVisits (HashMap<String, ArrayList<String>> iPdays) {
         int max = 0; String day = "";
         for (String currDay : iPdays.keySet()) {
             int ips = iPdays.get(currDay).size();
             if (ips > max) {
                 max = ips;
                 day = currDay;
             }
         }
         return day;
     }

     public ArrayList<String> iPsWithMostVisitsOnDay (HashMap<String, ArrayList<String>>
                                                              iPdays, String day) {
         HashMap<String, Integer> counts;
         counts = countIPs(iPdays.get(day));
         return iPsMostVisits(counts);
     }

     private HashMap<String, Integer> countIPs(ArrayList<String> ips) {
         HashMap<String, Integer> counts = new HashMap<>();
         ArrayList<String> seen = new ArrayList<>();
         for (String ip : ips) {
             if (!seen.contains(ip)) {
                 seen.add(ip);
                 counts.put(ip, 1);
             }
             else
                 counts.put(ip, counts.get(ip) + 1);
         }
         return counts;
     }
        
     public void printAll() {
         for (LogEntry le : records) {
             System.out.println(le);
         }
     }
     
     
}
