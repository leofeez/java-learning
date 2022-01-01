package jvm.t02_class_loader;

import java.lang.String;

/**
 * @author leofee
 * @date 2021/8/12
 */
public class LazyInitializing {

    public static void main(String[] args) throws ClassNotFoundException {
        // 声明P 的时候不会初始化
        P p;
        // 访问 final 变量不会初始化
        System.out.println(P.i);
        // 访问 static 会初始化
        System.out.println(P.j);

        // 子类初始化，必须先初始化父类
        System.out.println(new X());

        Class.forName("jvm.t02_class_loader.LazyInitializing$P");
    }

    static class P {
        final static int i = 8;

        static int j = 9;

        static {
            System.out.println("P");
        }
    }

    static class X extends P {
        final static int i = 8;

        static int j = 9;

        static {
            System.out.println("X");
        }
    }
}
