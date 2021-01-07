package thread._wait_notify;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author leofee
 * @date 2021/1/5
 */
public class WaitNotify09 extends Thread {

    private final List<String> list = new ArrayList<>();

    public WaitNotify09() {
    }

    public WaitNotify09(Runnable runnable, String name) {
        super(runnable, name);
    }

    /**
     * 在多个线程的情况，如果使用wait()，则需要使用notifyAll(),否则会产生假死，导致所有线程都在waiting状态
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {

        WaitNotify09 object = new WaitNotify09();

        WaitNotify09 producer01 = new WaitNotify09(() -> {
            while (true) {
                object.produce();
            }
        }, "生产者1");

        WaitNotify09 producer02 = new WaitNotify09(() -> {
            while (true) {
                object.produce();
            }
        }, "生产者2");

        WaitNotify09 consumer01 = new WaitNotify09(() -> {
            while (true) {
                object.consume();
            }
        }, "消费者1");

        WaitNotify09 consumer02 = new WaitNotify09(() -> {
            while (true) {
                object.consume();
            }
        }, "消费者2");

        List<Thread> threads = new ArrayList<>();
        threads.add(producer01);
        threads.add(producer02);
        threads.add(consumer01);
        threads.add(consumer02);

        threads.forEach(Thread::start);

        TimeUnit.SECONDS.sleep(10);


        threads.forEach(thread -> {
            System.out.println(thread.getName() + thread.getState());
        });
    }

    ;

    public synchronized void produce() {
        if (list.size() > 0) {
            try {
                notify();
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            list.add(UUID.randomUUID().toString());
            System.out.println(Thread.currentThread().getName() + ", 生产了 " + list.get(0));
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public synchronized void consume() {
        if (list.size() > 0) {
            String remove = list.remove(0);
            System.out.println(Thread.currentThread().getName() + ", 消费了 " + remove);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            notify();
        } else {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
