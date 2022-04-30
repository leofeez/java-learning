package design.composite;

import java.util.Collections;
import java.util.List;

/**
 * @author leofee
 */
public class LeafNode extends TreeNode {

    public LeafNode(String name) {
        super(name);
    }

    @Override
    public void nodeName() {
        System.out.println(name);
    }

    @Override
    public boolean hasChildNodes() {
        return false;
    }

    @Override
    public List<TreeNode> listChildNodes() {
        return Collections.EMPTY_LIST;
    }
}
