package practice.MyTrees;

import static org.junit.Assert.*;
import org.junit.*;

public class BinarySearchTreeTester {

    BinarySearchTree tree;
    BinarySearchTree tree2;

    @Before
    public void setup () {
        int[] test1 = {3, 5, 1, 12, 10, 15, 13, 11, 17};
        int[] test2 = {3, 5, 1, 4, 7, 8, 9};

        TreeNode<Integer> root1 = new TreeNode<Integer>(9, null);
        tree = new BinarySearchTree(root1);
        for (int item : test1) {
            tree.insert(root1, item);
        }

        TreeNode<Integer> root2 = new TreeNode<Integer>(6, null);
        tree2 = new BinarySearchTree(root2);
        for (int item : test2) {
            tree2.insert(root2, item);
        }

        System.out.println(tree);
    }

    @Test
    public void testDelete1 () {
        tree.delete(3);
        System.out.println(tree + "Deleted 3, ");
    }

    @Test
    public void testDelete2 () {
        tree.delete(12);
        System.out.println(tree + "Deleted 12, ");
    }

    @Test
    public void testDelete3 () {
        tree.delete(10);
        System.out.println(tree + "Deleted 10, ");
    }

    @Test
    public void testDelete4 () {
        tree.delete(15);
        System.out.println(tree + "Deleted 15, ");
    }

    @Test
    public void testDelete5 () {
        tree.delete(11);
        System.out.println(tree + "Deleted 11, ");
    }

    @Test
    public void testDelete6 () {
        tree.delete(1);
        System.out.println(tree + "Deleted 1, ");
    }

}
