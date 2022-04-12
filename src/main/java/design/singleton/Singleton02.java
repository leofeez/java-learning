package design.singleton;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leofee
 */
public class Singleton02 {

    private static final Object MONITOR = new Object();

    /**
     * 构造方法私有化
     */
    private Singleton02() {}

    /**
     * 2.懒汉式单例
     */
    private static volatile Singleton02 INSTANCE_2;
    public static Singleton02 getInstance2() {
        if (INSTANCE_2 == null) {
            synchronized (MONITOR) {

                if (INSTANCE_2 == null) {
                    INSTANCE_2 = new Singleton02();
                }
            }
        }
        return INSTANCE_2;
    }


    public static void main(String[] args) throws InterruptedException {
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            threadList.add(new Thread(() -> {
                Singleton02 instance2 = Singleton02.getInstance2();
                System.out.println(instance2);
            }));
        }
        for (Thread thread : threadList) {
            thread.start();
        }
    }
}
