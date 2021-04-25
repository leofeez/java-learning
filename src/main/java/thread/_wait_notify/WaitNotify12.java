package thread._wait_notify;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author leofee
 * @date 2021/1/16
 */
public class WaitNotify12 extends Thread {

    public WaitNotify12() {
    }

    public WaitNotify12(Runnable target) {
        super(target);
    }

    private static final List<String> LIST = new ArrayList<>();

    /**
     * 实现一个容器，提供两个方法，add,size
     * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，
     * 当个数到5个时，线程2给出提示并结束
     */
    public static void main(String[] args) {

        WaitNotify12 obj = new WaitNotify12();

        new WaitNotify12(obj::listen).start();

        new WaitNotify12(obj::add).start();
    }

    public synchronized void add() {
        for (int i = 0; i < 10; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LIST.add(UUID.randomUUID().toString());
            System.out.println(LIST.size());
            if (LIST.size() == 5) {
                try {
                    notifyAll();
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public synchronized void listen() {
        while (LIST.size() != 5) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        notifyAll();
        System.out.println("检测到LIST的元素达到5个");
    }
}
