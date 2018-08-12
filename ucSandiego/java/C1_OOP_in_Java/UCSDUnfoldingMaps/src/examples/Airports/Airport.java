package examples.Airports;
import static com.intellij.util.ArrayUtil.swap;

public class Airport implements Comparable<Airport> {

    private String city, country, code;

    public Airport(String city, String country, String code) {
        this.city = city;
        this.country = country;
        this.code = code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int compareTo (Airport other) {
        return other.getCity().compareTo(this.getCity());
    }

    public static String findAirportCodeLinearSearch (Airport[] airports, String toFind) {
        int idx = 0;
        while (idx < airports.length)
            if (toFind.equals(airports[idx].getCity()))
                return airports[idx].getCode();
            idx += 1;
        return null;
    }

    public static String findAirportCodeBinarySearch (Airport[] airports, String toFind) {
        int low = 0, high = airports.length - 1, mid;
        while (low <= high) {
            mid = low + (high - low) / 2;
            int compare = toFind.compareTo(airports[mid].getCity());
            if (compare > 0)
                low = mid + 1;
            else if (compare < 0)
                high = mid - 1;
            else return airports[mid].getCode();
        }
        return null;
    }

    public static int[] selectionShort (int[] vals) {
        for (int i = 0; i < vals.length - 1; i++) {
            int indexMin = i;
            for (int j = i + 1; j < vals.length; j++)
                if (vals[indexMin] > vals[j]) indexMin = j;
            swap(vals, i, indexMin);
        }
        return vals;
    }

    public static int[] insertionShort (int[] vals) {
        int currIdx = 0;
        for (int pos = 1; pos < vals.length; pos++) {
            currIdx = pos;
            while (currIdx > 0 && vals[currIdx] < vals[currIdx - 1]) {
                swap(vals, currIdx, currIdx - 1);
                currIdx -= 1;
            }
        }
        return vals;
    }

    /**
     * Worst case and average case complexity is O(n^2)
     * best case complexity is O(n)
     * */
    public static int[] bubbleSort (int[] vals) {
        int swaps;
        while (true) {
            swaps = 0;
            // loop to mergeSort the array
            for (int i = 0; i < vals.length - 1; i++)
                if (vals[i] > vals[i + 1]) {
                    swaps += 1;
                    swap(vals, i, i + 1);
                }
            // array is fully sorted now, so return the sorted array
            if (swaps == 0) return vals;
        }
    }
}
