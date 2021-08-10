package tree;

import com.sun.tools.javac.util.Assert;


/**
 * 二叉树
 *
 * @author leofee
 */
public class BST<V extends Comparable<V>> {

    TreeNode<V> root;

    int size;

    public int size() {
        return size;
    }

    public TreeNode<V> put(V value) {

        Assert.checkNonNull("The value of BST tree can not be null!");

        TreeNode<V> newNode = new TreeNode<>(value);

        // 根节点为null，则直接将当前节点设置为根节点
        if (root == null) {
            root = newNode;
            size = 1;
            return root;
        }

        return put(root, newNode);
    }

    public TreeNode<V>[] nodes() {
        @SuppressWarnings("unchecked")
        TreeNode<V>[] nodes = new TreeNode[size];
        if (root == null) {
            return nodes;
        }

        nodes(nodes, 0, root);

        return nodes;
    }

    private void nodes(TreeNode<V>[] nodes, int index, TreeNode<V> parent) {
        if (parent != null) {
            nodes[index] = parent;
            index++;
            nodes(nodes, index, parent.left);
            nodes(nodes, index, parent.right);
        }
    }

    private TreeNode<V> put(TreeNode<V> parent, TreeNode<V> newNode) {

        int compareTo = newNode.value.compareTo(parent.value);

        if (compareTo == 0) {
            parent.value = newNode.value;
            return parent;
        }

        if (compareTo < 0) {

            if (parent.left == null) {
                parent.left = newNode;
                newNode.parent = parent;
                size ++;
                return parent;
            } else {
                return put(parent.left, newNode);
            }
        }

        if (parent.right == null) {
            parent.right = newNode;
            newNode.parent = parent;
            size ++;
            return parent;
        } else {
            return put(parent.right, newNode);
        }
    }

    static class TreeNode<V extends Comparable<V>> {

        /**
         * 树节点的 value
         */
        V value;

        /**
         * 左子节点
         */
        TreeNode<V> left;

        /**
         * 右子节点
         */
        TreeNode<V> right;

        /**
         * 父节点
         */
        TreeNode<V> parent;

        public TreeNode(V value) {
            this.value = value;
        }

    }
}
