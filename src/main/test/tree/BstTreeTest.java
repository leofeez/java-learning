package tree;

/**
 * @author leofee
 */
public class BstTreeTest {

    public static void main(String[] args) {
        BST<Integer> tree = new BST<>();

        tree.put(5);
        tree.put(4);
        tree.put(3);
        tree.put(6);
        tree.put(7);
        tree.put(8);

        BST.TreeNode<Integer>[] nodes = tree.nodes();

        for (BST.TreeNode<Integer> node : nodes) {
            System.out.println(node.value);
        }
    }
}
