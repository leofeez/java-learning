package thread._wait_notify;

import java.util.concurrent.TimeUnit;

/**
 * @author leofee
 * @date 2021/1/5
 */
public class WaitNotify07 extends Thread {

    static final Object LOCK = new Object();

    public WaitNotify07(Runnable runnable, String name) {
        super(runnable, name);
    }

    /**
     * wait()方法支持指定时间
     * @param args
     */
    public static void main(String[] args) {
        new WaitNotify07(() -> {
            synchronized (LOCK) {
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

        new WaitNotify07(() -> {
            synchronized (LOCK) {
                try {
                    System.out.println(Thread.currentThread().getName() + ", wait开始......");
                    // 如果 notify方法比wait方法先执行，则wait的线程会无法得到唤醒，将持续阻塞
                    LOCK.wait();
                    /*LOCK.wait(1000);*/
                    System.out.println(Thread.currentThread().getName() + ", wait结束......");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();
    }
}
