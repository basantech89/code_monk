import edu.duke.FileResource;

import java.util.ArrayList;

public class CharactersInPlay {

    private ArrayList<String> speakers;
    private ArrayList<Integer> counts;

    public CharactersInPlay() {
        this.speakers = new ArrayList<>();
        this.counts = new ArrayList<>();
    }

    public void update (String person) {
        person = person.toLowerCase();
        if (!speakers.contains(person)) {
            speakers.add(person);
            counts.add(1);
        }
        else {
            int index = speakers.indexOf(person);
            int val = counts.get(index);
            counts.set(index, val + 1);
        }
    }

    public void findAllCharacters () {
        speakers.clear(); counts.clear();
        FileResource resource = new FileResource();
        for (String line : resource.lines()) {
            int index = line.indexOf(".");
            if (index != -1)
                update(line.substring(0, index));
        }
    }

    public void charactersWithNumParts (int num1, int num2) {
        int parts;
        for (int i = 0; i < speakers.size(); i++) {
            parts = counts.get(i);
            if ((parts >= num1) && (parts <= num2))
                System.out.println(speakers.get(i) + "\t" + parts);
        }
    }

    public void tester () {
        findAllCharacters();
        //for (int i = 0; i < speakers.size(); i++)
          //  if (counts.get(i) > 10)
            //    System.out.println(speakers.get(i) + "\t" + counts.get(i));
        charactersWithNumParts(10, 15);
    }

    public static void main (String[] args) {
        CharactersInPlay play = new CharactersInPlay();
        play.tester();
    }
}
