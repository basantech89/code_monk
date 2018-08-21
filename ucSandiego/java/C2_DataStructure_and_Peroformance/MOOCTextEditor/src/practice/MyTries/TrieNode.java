package practice.MyTries;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TrieNode {

    private boolean isWord;
    private String text;
    HashMap<Character, TrieNode> children;

    public TrieNode(boolean isWord, String text, HashMap<Character, TrieNode> children) {
        this.isWord = isWord;
        this.text = text;
        this.children = children;
    }

    public boolean isWord() {
        return isWord;
    }

    public void setWord(boolean word) {
        isWord = word;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Map<Character, TrieNode> getChildren() {
        return children;
    }

    public void setChildren(HashMap<Character, TrieNode> children) {
        this.children = children;
    }

    /** Return the set of characters that have links from this node */
    public Set<Character> getValidNextCharacters()
    {
        return children.keySet();
    }

    public TrieNode getChild(Character c)
    {
        return children.get(c);
    }

}
