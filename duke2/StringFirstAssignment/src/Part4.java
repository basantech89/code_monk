import edu.duke.*;
import org.apache.commons.csv.*;

public class Part4 {

    public void findWebLinks() {
        URLResource page = new URLResource("http://www.dukelearntoprogram.com/course2/data/manylinks.html");
        for (String word : page.words()) {
            if (word.toLowerCase().contains("youtube.com")) {
                int startIndex = word.indexOf("\"");
                int stopIndex = word.indexOf("\"", startIndex + 1);
                System.out.println(word.substring(startIndex + 1, stopIndex));
            }
        }
    }

    public static void main(String[] args) {
        Part4 pt = new Part4();
        pt.findWebLinks();
    }
}
