package practice.MyTrees;

import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree<E extends Comparable<? super E>> {

    TreeNode<E> root;

        private void preOrder (TreeNode<E> node) {
        if (node != null) {
            node.visit();
            preOrder(node.getLeftChild());
            preOrder(node.getRightChild());
        }
    }

    public void preOrder () { this.preOrder(root); }

    // breadth first search
    public StringBuilder levelOrder(TreeNode<E> root) {
        StringBuilder path = new StringBuilder();
        Queue<TreeNode<E>> q = new LinkedList<TreeNode<E>>();
        q.add(root);
        while (!q.isEmpty()) {
            // while the queue is not empty, remove the first element
            // and add children of removed element at the last
            TreeNode<E>  curr = q.remove();
            if (curr != null) {
                path.append(curr.getValue() + " ");
                curr.visit();
                q.add(curr.getLeftChild());
                q.add(curr.getRightChild());
            }
        }
        return path;
    }
}
