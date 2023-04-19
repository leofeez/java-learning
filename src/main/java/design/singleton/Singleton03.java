package design.singleton;

import java.util.ArrayList;
import java.util.List;

/**
 * 3.枚举方式单例
 * @author leofee
 */
public class Singleton03 {
    /**
     * 构造方法私有化
     */
    private Singleton03() {}

    public enum SingleEnum {
        INSTANCE ;

        public void hello() {
            System.out.println("singleton");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            threadList.add(new Thread(() -> {
                System.out.println(SingleEnum.INSTANCE);
            }));
        }
        for (Thread thread : threadList) {
            thread.start();
        }
    }
}
