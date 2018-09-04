package spelling;

import java.util.*;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    

    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should convert the 
	 * string to all lower case before you insert it. 
	 * 
	 * This method adds a word by creating and linking the necessary trie nodes 
	 * into the trie, as described outlined in the videos for this week. It 
	 * should appropriately use existing nodes in the trie, only creating new 
	 * nodes when necessary. E.g. If the word "no" is already in the trie, 
	 * then adding the word "now" would add only one additional node 
	 * (for the 'w').
	 * 
	 * @return true if the word was successfully added or false if it already exists
	 * in the dictionary.
	 */
	public boolean addWord(String word)
	{
	    //TODO: Implement this method.
		if (word == null) throw new NullPointerException("AddWord: Can't insert a null value");
		if (word.isEmpty()) {
			System.out.println("AddWord: Can't return an empty string");
			return false;
		}

		word = word.toLowerCase();

		int wordIndex = 0;
		Character wordChar = word.charAt(wordIndex);

		TrieNode node = root;

		while (wordIndex <= word.length() - 1) {

			if (!node.getValidNextCharacters().contains(wordChar))
				node = node.insert(wordChar);
			else
				node = node.getChild(wordChar);

			// if we've reached at the end index of the word
			if (wordIndex == word.length() - 1 && !node.endsWord()) {
				node.setEndsWord(true);
				++size;
				return true;
			}

			++wordIndex;
			if (wordIndex <= word.length() - 1)
				wordChar = word.charAt(wordIndex);

		}

		return false; // word already exist in the trie
	}

	/**
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
	    //TODO: Implement this method
	    return size;
	}
	
	
	/** Returns whether the string is a word in the trie, using the algorithm
	 * described in the videos for this week. */
	@Override
	public boolean isWord(String s) 
	{
	    // TODO: Implement this method
		if (s == null) throw new NullPointerException("Can't find a null value");
		if (s.isEmpty()) {
			System.out.println("IsWord: Can't return an empty string");
			return false;
		}

		s = s.toLowerCase();
		TrieNode node = root;

		int wordIndex = 0;
		Character wordChar = s.charAt(wordIndex);

		while (wordIndex <= s.length() - 1) {
			// if node's children have the character at index wordIndex
			if (node.getValidNextCharacters().contains(wordChar)) {

				node = node.getChild(wordChar);

				if (wordIndex == s.length() - 1)
					return node.endsWord();

				++wordIndex;
				if (wordIndex <= s.length() - 1)
					wordChar = s.charAt(wordIndex);

			}

			else return false; // not in the dict

		}

		return false;
	}

	/** 
     * Return a list, in order of increasing (non-decreasing) word length,
     * containing the numCompletions shortest legal completions 
     * of the prefix string. All legal completions must be valid words in the 
     * dictionary. If the prefix itself is a valid word, it is included 
     * in the list of returned words. 
     * 
     * The list of completions must contain 
     * all of the shortest completions, but when there are ties, it may break 
     * them in any order. For example, if there the prefix string is "ste" and 
     * only the words "step", "stem", "stew", "steer" and "steep" are in the 
     * dictionary, when the user asks for 4 completions, the list must include 
     * "step", "stem" and "stew", but may include either the word 
     * "steer" or "steep".
     * 
     * If this string prefix is not in the trie, it returns an empty list.
     * 
     * @param prefix The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired.
     * @return A list containing the up to numCompletions best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) {
		// TODO: Implement this method
		// This method should implement the following algorithm:
		// 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
		//    empty list
		// 2. Once the stem is found, perform a breadth first search to generate completions
		//    using the following algorithm:
		//    Create a queue (LinkedList) and add the node that completes the stem to the back
		//       of the list.
		//    Create a list of completions to return (initially empty)
		//    While the queue is not empty and you don't have enough completions:
		//       remove the first Node from the queue
		//       If it is a word, add it to the completions list
		//       Add all of its child nodes to the back of the queue
		// Return the list of completions

		List<String> completions = new LinkedList<>();
		// if input is not right
		if (numCompletions <= 0 || prefix == null) return completions;

		TrieNode node = root;
		TrieNode stem = null;

		int preIndex = 0;
		Character preChar = null;
		if (!prefix.isEmpty())
			preChar = prefix.charAt(preIndex);

		// find the stem in the trie
		while (preIndex <= prefix.length() - 1)

			if (node.getValidNextCharacters().contains(preChar)) {

				node = node.getChild(preChar);

				if (preIndex == prefix.length() - 1)
					// found the stem
					stem = node;

				++preIndex;
				if (preIndex <= prefix.length() - 1)
					preChar = prefix.charAt(preIndex);
			}

			else return completions; // stem is not in dict

		// if prefix is an empty string, while loop condition will always be false
		// preChar and stem would be null
		// set stem to root node to get numCompletions words
		if (stem == null) stem = root;

		// breadth first search to  get all the completions
		Queue<TrieNode> q = new LinkedList<>();
		q.add(stem);

		// while q is not empty, removing front node and
		// adding it's children on the back
		while (!q.isEmpty() && numCompletions > completions.size()) {

			TrieNode qNode = q.remove();
			if (qNode.endsWord())
				completions.add(qNode.getText());

			q.addAll(qNode.getValidNextNodes());
		}

		return completions;
	}

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;

 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	
	public static void main(String[] args) {
 		/*AutoCompleteDictionaryTrie trie = new AutoCompleteDictionaryTrie();
 		trie.addWord("at");
 		trie.addWord("At");
 		trie.addWord("eat");
		trie.addWord("eats");
		trie.addWord("pot");
		trie.addWord("pet");
		trie.addWord("banana");
		trie.addWord("hello");
		trie.addWord("hElLo");
		trie.addWord("");
		System.out.println("trie size is " + trie.size());
 		trie.printTree();
		System.out.println("isit " + trie.isWord("helloo"));
 		System.out.println(trie.isWord("pet"));
		System.out.println(trie.isWord("pets"));
		System.out.println(trie.isWord("eat"));
		System.out.println(trie.isWord("eats"));
		System.out.println(trie.isWord("e"));
		System.out.println(trie.isWord("ea"));
		System.out.println(trie.isWord("h"));
		System.out.println(trie.isWord("banana"));*/
		AutoCompleteDictionaryTrie smallDict = new AutoCompleteDictionaryTrie();
		smallDict.addWord("Hello");
		smallDict.addWord("HElLo");
		smallDict.addWord("help");
		smallDict.addWord("he");
		smallDict.addWord("hem");
		smallDict.addWord("hot");
		smallDict.addWord("hey");
		smallDict.addWord("a");
		smallDict.addWord("subsequent");
		System.out.println("size of smallDict is " + smallDict.size());
		//smallDict.printTree();
		List<String> completions = smallDict.predictCompletions("",  4);
		System.out.println(completions);
	}
	
}