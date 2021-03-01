package thread._wait_notify;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author leofee
 * @date 2021/1/5
 */
public class WaitNotify08 extends Thread {

    private final List<String> list = new ArrayList<>();

    public WaitNotify08() {
    }

    public WaitNotify08(Runnable runnable, String name) {
        super(runnable, name);
    }

    /**
     * 一个生产者和一个消费者交替执行
     */
    public static void main(String[] args) {

        WaitNotify08 object = new WaitNotify08();

        new WaitNotify08(() -> {
            while (true) {
                object.produce();
            }
        }, "生产者").start();

        new WaitNotify08(() -> {
            while (true) {
                object.consume();
            }
        }, "消费者").start();
    }

    public synchronized void produce() {
        while (list.size() > 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        list.add(UUID.randomUUID().toString());
        System.out.println(Thread.currentThread().getName() + ", 生产了 " + list.get(0));
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notifyAll();

    }

    public synchronized void consume() {
        while (list.size() > 0) {
            String remove = list.remove(0);
            System.out.println(Thread.currentThread().getName() + ", 消费了 " + remove);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            notifyAll();
        }
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
