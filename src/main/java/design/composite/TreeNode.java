package design.composite;

import java.util.List;

/**
 * @author leofee
 */
public abstract class TreeNode {

    protected String name;

    public TreeNode(String name) {
        this.name = name;
    }

    /**
     * 节点名称
     */
    public abstract void nodeName();

    /**
     * 是否包含子节点
     *
     * @return true 是 / false 否
     */
    public abstract boolean hasChildNodes();

    /**
     * 子节点清单
     * @return 子节点清单
     */
    public abstract List<TreeNode> listChildNodes();
}
