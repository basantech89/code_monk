package practice.MyTries;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Trie {

    private TrieNode root;
    private int size;

    public Trie() {
        this.root = new TrieNode(false, null, new HashMap<>());
        this.size = 0;
    }

    public int getSize() { return size; }

    public boolean find (String toFind) {
        if (toFind == null) throw new NullPointerException("Find: Can't find a null value");
        if (root == null) return false;
        int index = 0;
        StringBuilder returned = new StringBuilder();
        StringBuilder result = trieSearch(root, returned, toFind, index);
        return returned.equals(result);
    }

    public StringBuilder trieSearch (TrieNode node, StringBuilder returned, String toFind, int index) {

        Character toFollow = toFind.charAt(index);
        if (node == null) return new StringBuilder("Node " + toFollow + " does not exist");

        if (!node.getChildren().containsKey(toFollow)) return new StringBuilder("The key " +
                toFollow + " for node " + node + " does not exist");

        returned.append(toFollow);
        TrieNode nextNode = node.getChildren().get(toFollow);

        if (nextNode == null) return new StringBuilder(nextNode + " children node for node " + node +
                " does not exist");

        return trieSearch(nextNode, returned, toFind, ++index);

    }

    public boolean insert (String toInsert) {
        if (toInsert == null) throw new NullPointerException("Insert: Can't insert a null value");
        int index = 0;
        return insert(root, toInsert, index);
    }

    public boolean insert (TrieNode node, String toInsert, int index) {
        Character toFollow = toInsert.charAt(index);

        if (!node.getChildren().containsKey(toFollow)) {

            //System.out.println("The key " + toFollow + " doesn't exist, adding it");
            TrieNode child = new TrieNode(false, null, new HashMap<>());
            node.getChildren().put(toFollow, child);
            ++size;

            if (toFollow == toInsert.charAt(toInsert.length() - 1)) {
                child.setText(toInsert);
                child.setWord(true);
                return true;
            }
        }

        index += 1;

        if (toInsert.length() > index) {
            node = node.getChildren().get(toFollow);
            return insert(node, toInsert, index);
        }

        return false;
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
/*
    public String toString () {
        StringBuilder toReturn = new StringBuilder();
        TrieNode node = root;
        int index = -1;
        int i = -1;
        Stack<Character> childStack = new Stack<>();
        printNode(root, childStack, toReturn, index, i);
        *//*for (Character child : node.getChildren().keySet()) {
            childStack.add(child);
            toReturn.append(childStack.get(index) + " --> ");
            ++index;
            TrieNode nextNode = node.getChildren().get(child);
            if (nextNode.getChildren().keySet().size() != 0)
                printNode(nextNode, childStack, toReturn, index);
            childStack.remove(childStack.size() - 1);
            toReturn.append("\n");
        }*//*
        //return "";
        return toReturn.toString().trim();
    }

    private StringBuilder printNode (TrieNode node, Stack<Character> childStack, StringBuilder toReturn,
                                     int index, int i) {
        for (Character child : node.getChildren().keySet()) {
            childStack.add(child);
            ++index;
            TrieNode nextNode = node.getChildren().get(child);
            if (nextNode.getChildren().keySet().size() != 0) {
                i = index ;
                printNode(nextNode, childStack, toReturn, index, i);
            }
            if (i + 1 == index) {
                for (Character ch : childStack)
                    toReturn.append(ch + " --> ");
                toReturn.append("\n");
            }
            childStack.remove(childStack.size() - 1);
            --index;
        }
        return toReturn;
    }*/


    public static void main (String[] args) {
        Trie tree1 = new Trie();
        /*tree1.insert("e");
        tree1.insert("a");
        tree1.insert("c");*/
        tree1.insert("at");
        System.out.println("yo, " + tree1.insert("AT"));
        /*tree1.insert("top");
        tree1.insert("pot");
        tree1.insert("read");
        tree1.insert("monkey");
        tree1.insert("pot");
        tree1.insert("pet");
        tree1.insert("eat"); //TODO - not printed eat bcoz of eats
        tree1.insert("eats");
        tree1.insert("ear");
        tree1.printTree();*/
    }
}
