package design.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leofee
 */
public class BranchNode extends TreeNode {

    List<TreeNode> nodeList = new ArrayList<>();

    public BranchNode(String name) {
        super(name);
    }

    @Override
    public void nodeName() {
        System.out.println(name);
    }

    @Override
    public boolean hasChildNodes() {
        return nodeList.size() > 0;
    }

    @Override
    public List<TreeNode> listChildNodes() {
        return nodeList;
    }

    public void addNode(TreeNode node) {
        nodeList.add(node);
    }
}
