package jvm.t05_gc;

/**
 * @author leofee
 * @date 2021/8/23
 */
public class T00_HelloGC {

    public static void main(String[] args) {
        System.out.println("当前堆空间为：" + Runtime.getRuntime().totalMemory() / 1024 / 1024 + "M");
        System.out.println("最大堆空间为：" + Runtime.getRuntime().maxMemory() / 1024 / 1024 + "M");
        System.out.println("空闲堆空间为：" + Runtime.getRuntime().freeMemory() / 1024 / 1024 + "M");

    }
}
