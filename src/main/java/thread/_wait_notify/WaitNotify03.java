package thread._wait_notify;

import java.util.concurrent.TimeUnit;

/**
 * @author leofee
 * @date 2020/12/30
 */
public class WaitNotify03 extends Thread {

    public WaitNotify03() {
    }

    public WaitNotify03(Runnable target, String name) {
        super(target, name);
    }

    private static final WaitNotify03 LOCK = new WaitNotify03();

    private int count = 0;

    public static void main(String[] args) {

        new WaitNotify03(() -> {
            synchronized (LOCK) {
                if (LOCK.count < 5) {
                    System.out.println("count 当前小于 5 ，开始wait......");
                    try {
                        LOCK.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                System.out.println("count 已经等于 5 了，结束wait......");
            }
        }, "线程A").start();

        new WaitNotify03(() -> {
            synchronized (LOCK) {
                while (true) {
                    LOCK.count++;
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(LOCK.count);
                    if (LOCK.count == 5) {
                        System.out.println("count 已经等于 5 了，开始notify......");
                        LOCK.notify();
                        break;
                    }
                }
            }
        }, "线程B").start();
    }
}
