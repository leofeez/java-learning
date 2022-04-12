package design.singleton;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leofee
 */
public class Singleton04 {
    /**
     * 构造方法私有化
     */
    private Singleton04() {
    }

    /**
     * 4.静态内部类单例
     * <p>
     * 通过静态内部类的方式实现单例模式是线程安全的，同时静态内部类不会在Singleton类加载时就加载，
     * 而是在调用getInstance()方法时才进行加载，达到了懒加载的效果。
     */
    static class SingletonHolder {
        private static final Singleton04 INSTANCE = new Singleton04();
    }

    public static Singleton04 getInstance() {
        return SingletonHolder.INSTANCE;
    }


    public static void main(String[] args) throws InterruptedException {
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            threadList.add(new Thread(() -> {
                Singleton04 instance2 = Singleton04.getInstance();
                System.out.println(instance2);
            }));
        }
        for (Thread thread : threadList) {
            thread.start();
        }
    }
}
