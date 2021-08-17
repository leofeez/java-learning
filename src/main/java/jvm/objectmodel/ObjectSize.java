package jvm.objectmodel;


import object_agent.ObjectAgent;

/**
 * @author leofee
 * @date 2021/8/17
 */
public class ObjectSize {

    public static void main(String[] args) {
        Object o = new Object();

        System.out.println(ObjectAgent.sizeof(o));
    }
}
