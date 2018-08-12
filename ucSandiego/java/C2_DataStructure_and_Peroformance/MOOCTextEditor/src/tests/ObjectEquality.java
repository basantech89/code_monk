package tests;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ObjectEquality {

    public static void main (String[] args) {
        ListNode node1 = new ListNode("the");
        ListNode node2 = new ListNode("the");
        List<ListNode> list = new LinkedList<>();
        list.add(node1);
        if (list.contains(node2)) System.out.println("Yes");
        else System.out.println("No");
    }
}

class ListNode
{
    // The word that is linking to the next words
    private String word;

    // The next words that could follow it
    private List<String> nextWords;

    ListNode(String word)
    {
        this.word = word;
        nextWords = new LinkedList<String>();
    }

    public String getWord()
    {
        return word;
    }

    public void addNextWord(String nextWord)
    {
        nextWords.add(nextWord);
    }

    public String getRandomNextWord(Random generator)
    {
        // TODO: Implement this method
        // The random number generator should be passed from
        // the MarkovTextGeneratorLoL class
        return nextWords.get(generator.nextInt(nextWords.size()));
    }

    public String toString()
    {
        String toReturn = word + ": ";
        for (String s : nextWords) {
            toReturn += s + "->";
        }
        toReturn += "\n";
        return toReturn;
    }

}
