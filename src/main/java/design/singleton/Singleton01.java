package design.singleton;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leofee
 */
public class Singleton01 {

    /**
     * 1. 构造方法私有化
     */
    private Singleton01() {
    }

    /**
     * 饿汉式单例
     * 在类加载期间就可以进行初始化
     */
    private static final Singleton01 INSTANCE = new Singleton01();

    public static Singleton01 getInstance() {
        return INSTANCE;
    }


    public static void main(String[] args) throws InterruptedException {
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            threadList.add(new Thread(() -> {
                Singleton01 instance2 = Singleton01.getInstance();
                System.out.println(instance2);
            }));
        }
        for (Thread thread : threadList) {
            thread.start();
        }
    }
}
