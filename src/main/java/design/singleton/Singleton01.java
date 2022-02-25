package design.singleton;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leofee
 */
public class Singleton01 {

    private static final Object MONITOR = new Object();

    /**
     * 构造方法私有化
     */
    private Singleton01() {}

    /**
     * 1.饿汉式单例
     */
    private static final Singleton01 INSTANCE = new Singleton01();
    public static Singleton01 getInstance() {
        return INSTANCE;
    }

    /**
     * 2.懒汉式单例
     */
    private static volatile Singleton01 INSTANCE_2;
    public static Singleton01 getInstance2() {
        if (INSTANCE_2 == null) {
            synchronized (MONITOR) {

                if (INSTANCE_2 == null) {
                    INSTANCE_2 = new Singleton01();
                }
            }
        }
        return INSTANCE_2;
    }

    /**
     * 3.枚举方式单例
     */
    public enum SingleEnum {
        INSTANCE ;

        public void hello() {
            System.out.println("singleton");
        }
    }

    /**
     * 4.静态内部类单例
     *
     * 通过静态内部类的方式实现单例模式是线程安全的，同时静态内部类不会在Singleton类加载时就加载，
     * 而是在调用getInstance()方法时才进行加载，达到了懒加载的效果。
     */
    static class SingletonHolder {
        private static final Singleton01 INSTANCE = new Singleton01();
    }
    public static Singleton01 getInstance3() {
        return SingletonHolder.INSTANCE;
    }



    public static void main(String[] args) throws InterruptedException {
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            threadList.add(new Thread(() -> {
                Singleton01 instance2 = Singleton01.getInstance2();
                System.out.println(instance2);
            }));
        }
        for (Thread thread : threadList) {
            thread.start();
        }

        SingleEnum.INSTANCE.hello();
    }
}
