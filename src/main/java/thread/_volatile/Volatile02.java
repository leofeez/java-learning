package thread._volatile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author leofee
 * @date 2020/12/2
 */
public class Volatile02 extends Thread {

    public Volatile02(Runnable target, String name) {
        super(target, name);
    }

    private static volatile List<String> LIST = new ArrayList<>();

    /**
     * 实现一个容器，提供两个方法，add，size
     * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，
     * 当个数到5个时，线程2给出提示并结束
     */
    public static void main(String[] args) {

        new Volatile02(() -> {
            while (LIST.size() < 10) {
                LIST.add(UUID.randomUUID().toString());
                System.out.println(LIST.size());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();

        new Volatile02(() -> {
            while (true) {
                if (LIST.size() == 5) {
                    System.out.println(Thread.currentThread().getName() + "检测到LIST已经达到5个元素");
                    break;
                }
            }
        }, "B").start();
    }
}
