package practice.MyTrees;

public class TreeNode<E> {

    private E value;
    private TreeNode<E> parent;
    private TreeNode<E> left;
    private TreeNode<E> right;

    public TreeNode (E value, TreeNode<E> parent) {
        this.value = value;
        this.parent = parent;
        this.left = null;
        this.right = null;
    }

    /*public TreeNode<E> addLeftChild (E child) {
        this.left = new TreeNode<>(child, this);
        return this.left;
    }*/

    public TreeNode<E> getLeftChild () { return left; }

    public TreeNode<E> getRightChild () { return right; }

    public TreeNode<E> getParent () { return parent; }

    public E getValue () { return this.value; }

    public void setParent(TreeNode<E> parent) { this.parent = parent; }

    public void addLeftChild (TreeNode<E> left) { this.left = left; }

    public void addRightChild (TreeNode<E> right) { this.right = right; }

    public void setValue(E value) { this.value = value; }

    public void visit () {

    }
}
