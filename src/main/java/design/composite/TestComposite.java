package design.composite;

/**
 * 组合模式
 *
 * @author leofee
 */
public class TestComposite {

    public static void main(String[] args) {
        BranchNode root = new BranchNode("root");

        BranchNode branchNodeA = new BranchNode("A");
        BranchNode branchNodeB = new BranchNode("B");
        BranchNode branchNodeC = new BranchNode("C");
        root.addNode(branchNodeA);
        root.addNode(branchNodeB);
        root.addNode(branchNodeC);

        BranchNode branchNode = new BranchNode("a");
        branchNode.addNode(new LeafNode("1"));
        branchNode.addNode(new LeafNode("2"));
        branchNodeA.addNode(branchNode);
        branchNodeB.addNode(new LeafNode("b"));
        branchNodeC.addNode(new LeafNode("c"));

        printTree(root, 0);
    }

    public static void printTree(TreeNode root, int depth) {
        for (int i = 0; i < depth; i++) {
            if (i == 0) {
                System.out.print("|__");
            } else {
                System.out.print("__");
            }
        }
        root.nodeName();
        if (root.hasChildNodes()) {
            for (TreeNode childNode : root.listChildNodes()) {
                printTree(childNode, depth + 1);
            }
        }
    }
}
