package container.list;

import java.util.LinkedList;

/**
 * @author leofee
 */
public class LinkedList01 {

    public static void main(String[] args) {
        LinkedList<String> linkedList = new LinkedList<>();

        // add 是在list 后面追加元素
        linkedList.add("a");
        linkedList.add("b");
        linkedList.add("c");
        linkedList.add("d");

        // 从头部取出元素
        String peek = linkedList.peek();

        linkedList.poll();
    }
}
