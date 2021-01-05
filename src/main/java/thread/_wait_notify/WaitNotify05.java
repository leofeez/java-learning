package thread._wait_notify;

import java.util.concurrent.TimeUnit;

/**
 * @author leofee
 * @date 2021/1/5
 */
public class WaitNotify05 extends Thread {

    static final Object LOCK = new Object();

    public WaitNotify05(Runnable runnable, String name) {
        super(runnable, name);
    }

    public static void main(String[] args) {
        new WaitNotify05(() -> {
            synchronized (LOCK) {
                try {
                    System.out.println(Thread.currentThread().getName() + ", wait开始......");
                    LOCK.wait();
                    System.out.println(Thread.currentThread().getName() + ", wait结束......");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();

        new WaitNotify05(() -> {
            synchronized (LOCK) {
                try {
                    System.out.println(Thread.currentThread().getName() + ", wait开始......");
                    LOCK.wait();
                    System.out.println(Thread.currentThread().getName() + ", wait结束......");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();

        new WaitNotify05(() -> {
            synchronized (LOCK) {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ", notifyAll开始......");
                LOCK.notifyAll();
                System.out.println(Thread.currentThread().getName() + ", notifyAll结束......");
            }
        }, "C").start();
    }
}
