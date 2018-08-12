package textgen;

import java.util.*;

/**
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList;

	// The starting "word"
	private String starter;

	// The random number generator
	private Random rnGenerator;

	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}


	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		// TODO: Implement this method
		// shouldn't accept a null or empty string
		if (sourceText == null || sourceText.length() <= 0)
			throw new NullPointerException("Can't accept a null or empty string");

		String[] words = sourceText.split("\\s+");
		// if source text is just one word then nextWords list
		// should be empty
		if (words.length == 1) {
			wordList.add(new ListNode(words[0]));
			return;
		}

		LinkedList<String> seen = new LinkedList<>();
		for (int i = 0; i < words.length; i++)
			// if we see the word first time, add it to wordList
			if (!seen.contains(words[i])) {
				seen.add(words[i]);
				ListNode node = new ListNode(words[i]);
				wordList.add(node);
				// add it's next word to nextWords list from ListNode class
				addWord(node, words, i);
			}
			// otherwise, just add it's next word to nextWords list
			else
				for (ListNode node : wordList)
					if (node.getWord().equals(words[i]))
						addWord(node, words, i);
	}

	/** Add the next word to nextWords list for this word */
	private void addWord(ListNode node, String[] words, int index) {
		// if this is the last word at this index
		// add the first word to nextWords list for this word
		if (index == words.length - 1) node.addNextWord(words[0]);
		else node.addNextWord(words[index + 1]);
	}

	/**
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
	    // TODO: Implement this method
		if (numWords <= 0)
			throw new IndexOutOfBoundsException("Can't accept zero or negative value");

		// If the generator hasn't been trained on any data
		// return the empty string
		if (wordList.size() == 0) return "";

		// If trained on only 1 word, return that word
		if (wordList.size() == 1) return wordList.get(0).getWord();

		ListNode node = wordList.get(0);
		StringBuilder markovText = new StringBuilder(node.getWord());

		for (int i = 1; i < numWords; i++) {
			// generate a random word from nextWords list
			String word = node.getRandomNextWord(rnGenerator);
			markovText.append(" " + word);

			for (ListNode thisNode : wordList)
				// changing the state(node) to last randomly chose word
				if (thisNode.getWord().equals(word))
					node = thisNode;
		}
		return markovText.toString();
	}


	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}

	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		// TODO: Implement this method.
		wordList.clear();
		train(sourceText);
	}

	// TODO: Add any private helper methods you need here.


	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		//gen.train("");
		//gen.train("  a  ");
		//System.out.println(gen);
		//System.out.println(gen.generateText(20));
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
	}

}

/** Links a word to the next words in the list
 * You should use this class in your implementation. */
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


