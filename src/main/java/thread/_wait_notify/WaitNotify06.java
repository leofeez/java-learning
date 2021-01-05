package thread._wait_notify;

import java.util.concurrent.TimeUnit;

/**
 * @author leofee
 * @date 2021/1/5
 */
public class WaitNotify06 extends Thread {

    static final Object LOCK = new Object();

    public WaitNotify06(Runnable runnable, String name) {
        super(runnable, name);
    }

    /**
     * wait()方法支持指定时间
     * @param args
     */
    public static void main(String[] args) {
        new WaitNotify06(() -> {
            synchronized (LOCK) {
                try {
                    System.out.println(Thread.currentThread().getName() + ", wait开始......");
                    LOCK.wait(1000);
                    System.out.println(Thread.currentThread().getName() + ", wait结束......");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();

        new WaitNotify06(() -> {
            synchronized (LOCK) {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ", notifyAll开始......");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LOCK.notifyAll();
                System.out.println(Thread.currentThread().getName() + ", notifyAll结束......");
            }
        }, "C").start();
    }
}
