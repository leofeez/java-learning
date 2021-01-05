package thread._wait_notify;

import java.util.concurrent.TimeUnit;

/**
 * @author leofee
 * @date 2021/1/5
 */
public class WaitNotify04 extends Thread {

    private static final Object LOCK = new Object();

    public WaitNotify04(Runnable runnable, String name) {
        super(runnable, name);
    }

    /**
     * 如果正在wait()的线程遇到了异常，会直接终止线程
     * @param args
     */
    public static void main(String[] args) {
        Thread A = new WaitNotify04(() -> {
            synchronized (LOCK) {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("wait 开始......");
                    LOCK.wait();
                } catch (InterruptedException e) {
                    System.out.println("wait 时发生了异常......" + e.getMessage());
                }
            }
        }, "A");

        A.start();

        new WaitNotify04(() -> {
            synchronized (LOCK) {
                System.out.println("停止 A 线程 ......");
                A.interrupt();
            }
        }, "B").start();
    }
}
