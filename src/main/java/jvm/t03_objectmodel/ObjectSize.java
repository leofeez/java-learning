package jvm.t03_objectmodel;


//import object_agent.ObjectAgent;

/**
 * @author leofee
 * @date 2021/8/17
 */
public class ObjectSize {

    public static void main(String[] args) {
        Object o = new Object();
        System.out.println("new Object() 对象大小为：" + ObjectAgent.sizeof(o));

        // -XX:+UserCompressedClassPointers 为16
        // -XX:-UserCompressedClassPointers 为24
        T t = new T();
        System.out.println("new T() 对象大小为：" + ObjectAgent.sizeof(t));

        // -XX:+UserCompressedClassPointers 为24
        // -XX:-UserCompressedClassPointers 为32
        // -XX:-UserCompressedClassPointers -XX:-UseCompressedOops 为 40
        P p = new P();
        System.out.println("new P() 对象大小为：" + ObjectAgent.sizeof(p));
    }

    public static class T {
        // mark word 8 字节
        // class pointer 压缩为4字节，不压缩为8字节

        // int 类型为 4 个字节
        int a;

        // padding 对其填充为 8 字节倍数
    }

    /**
     * -XX:+UserCompressedClassPointers 为24
     * -XX:-UserCompressedClassPointers 为32
     * -XX:-UserCompressedClassPointers -XX:-UseCompressedOops 为 40
     */
    public static class P {
        // mark word 8 字节
        // class pointer 压缩为4字节，不压缩为8字节

        // int 类型为 4 个字节
        int a;
        int b;
        int c;

        // String 为引用类型，开启oops压缩占4个字节，正常占8个字节
        String s = "s";

        // padding 对其填充为 8 字节倍数
    }
}
