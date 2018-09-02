package practice.MyTrees;


import com.sun.xml.internal.messaging.saaj.soap.impl.TreeException;

import java.util.NoSuchElementException;

public class BinarySearchTree<E extends Comparable<? super E>> {

    TreeNode<E> root;

    public BinarySearchTree(TreeNode<E> root) { this.root = root; }

    private boolean BinarySearch (TreeNode<E> node, E toFind) {
        if (node == null) return false; // toFind not found in the tree

        int comp = toFind.compareTo(node.getValue());

        if (comp < 0)
            return BinarySearch(node.getLeftChild(), toFind);

        else if (comp > 0)
            return BinarySearch(node.getRightChild(), toFind);

        else return true;
    }

    public boolean BinarySearch (E toFind) {
        if (toFind == null) throw new NullPointerException("Can't accept a null value");
        if (root == null) throw new TreeException("Tree hasn't yet built");
        if (root.getValue() == null) throw new NullPointerException("Value of root node is null");
        return BinarySearch(root, toFind);
    }

    public boolean BinarySearchIntertive (E toFind) {
        TreeNode<E> curr = root;

        while (curr != null) {
            int comp = toFind.compareTo(curr.getValue());
            if (comp < 0)
                curr = curr.getLeftChild();
            else if (comp > 0)
                curr = curr.getRightChild();
            else
                return true;
        }

        return false;
    }

    public boolean insert (TreeNode<E> node, E toInsert) {
        if (toInsert == null) throw new NullPointerException("Insert: Can't insert a null value");

        TreeNode<E> child;
        int comp = toInsert.compareTo(node.getValue());

        if (comp < 0) {
            child = node.getLeftChild();
            if (child == null)
                node.addLeftChild(new TreeNode<E>(toInsert, node));
        }

        else if (comp > 0) {
            child = node.getRightChild();
            if (child == null)
                node.addRightChild(new TreeNode<E>(toInsert, node));
        }

        else {
            System.out.println("Node " + node.getValue() + " is already in the tree");
            return false;
        }

        if (child != null) insert(child, toInsert);
        return child == null;
    }

    /** Delete the node
     *  Any node may fall into 3 cases
     *  1. If leaf node
     *  2. If only one child
     *  3. If node has 2 children */
    public TreeNode<E> delete (E toDelete) {
        if (toDelete == null)
            throw new NullPointerException("Delete: Can't accept a null value");

        TreeNode<E> nodeToDelete = findNode(root, toDelete);

        if (nodeToDelete == null)
            throw new NoSuchElementException("Delete: Value to be deleted doesn't exist");

        TreeNode<E> child = null;
        TreeNode<E> left = nodeToDelete.getLeftChild();
        TreeNode<E> right = nodeToDelete.getRightChild();

        // if deleteNode has both children
        if (left != null && right != null) {
            // find smallest value in right subtree
            TreeNode<E> smallestNode = smallestInRightSubTree (right);

            // value of smallest node
            E smallestVal = smallestNode.getValue();

            // delete the smallest right subtree node
            delete(smallestVal);

            // replace deleted element's value with smallest right subtree value
            nodeToDelete.setValue(smallestVal);

            return nodeToDelete;
        }

        // if left child is null
        else if (left != null) {
            child = left;
            left.setParent(nodeToDelete.getParent());
            nodeToDelete.addLeftChild(null);
        }

        // if right child is null
        else if (right != null) {
            child = right;
            right.setParent(nodeToDelete.getParent());
            nodeToDelete.addRightChild(null);
        }

        deleteNode(nodeToDelete, child);
        return nodeToDelete;
    }

    /** Delete the node if it isn't the root node */
    private void deleteNode (TreeNode<E> nodeToDelete, TreeNode<E> child) {
        TreeNode<E> parent = nodeToDelete.getParent();

        // if it's not the root node
        if (parent != null) {
            // delete parent's link
            int comp = nodeToDelete.getValue().compareTo(parent.getValue());
            if (comp < 0)
                parent.addLeftChild(child);

            else if (comp > 0)
                parent.addRightChild(child);

            // delete it's link to parent
            nodeToDelete.setParent(null);
        }

        nodeToDelete.setValue(null);
    }

    private TreeNode<E> findNode (TreeNode<E> node, E toFind) {
        if (node != null) {
            int comp = toFind.compareTo(node.getValue());
            if (comp < 0) return findNode(node.getLeftChild(), toFind);
            else if (comp > 0) return findNode(node.getRightChild(), toFind);
            else return node;
        }
        return null;
    }

    private TreeNode<E> smallestInRightSubTree (TreeNode<E> node) {
        TreeNode<E> smallestNode = node;
        E smallest = node.getValue();

        while (node != null) {
            int comp = smallest.compareTo(node.getValue());
            if (comp > 0) {
                smallestNode = node;
                smallest = node.getValue();
            }
            node = node.getLeftChild();
        }
        return smallestNode;
    }

    public String toString () {
        BinaryTree<E> traversal = new BinaryTree<>();
        StringBuilder path = traversal.levelOrder(root);
        return path.toString();
    }

    public static void main (String[] args) {
        int[] test1 = {3, 5, 1, 12, 10, 15, 13, 11, 17};
        TreeNode<Integer> root1 = new TreeNode<Integer>(9, null);
        BinarySearchTree tree = new BinarySearchTree(root1);
        for (int item : test1) {
            tree.insert(root1, item);
        }
        System.out.println(tree);
        System.out.println(tree.BinarySearch(4));
        tree.insert(root1, 9);
        System.out.println(tree);
        tree.delete(9);
        System.out.println(tree);

        int[] test2 = {3, 5, 1, 4, 7, 8, 9};
        TreeNode<Integer> root2 = new TreeNode<Integer>(6, null);
        BinarySearchTree tree2 = new BinarySearchTree(root2);
        for (int item : test2) {
            tree2.insert(root2, item);
        }
        System.out.println(tree2);
        tree2.delete(3);
        System.out.println(tree2);
        int[] test3 = {10, 30, 5, 15, 3, 6, 25, 40, 22, 29, 24, 28, 35, 50, 45};
        TreeNode<Integer> root3 = new TreeNode<Integer>(20, null);
        BinarySearchTree tree3 = new BinarySearchTree(root3);
        for (int item : test3) {
            tree3.insert(root3, item);
        }
        System.out.println(tree3);
        tree3.delete(20);
        System.out.println(tree3);
    }
}
