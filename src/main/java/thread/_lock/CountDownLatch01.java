package thread._lock;

import java.util.concurrent.CountDownLatch;

/**
 * countDownLatch这个类使一个线程等待其他线程各自执行完毕后再执行。
 * 是通过一个计数器来实现的，计数器的初始值是线程的数量。
 * 每当一个线程执行完毕后，计数器的值就-1，当计数器的值为0时，
 * 表示所有线程都执行完毕，然后在闭锁上等待的线程就可以恢复工作了。
 */
public class CountDownLatch01 extends Thread {

    public CountDownLatch01(Runnable target) {
        super(target);
    }

    public static void main(String[] args) {
        CountDownLatch01[] threads = new CountDownLatch01[10];
        final CountDownLatch latch = new CountDownLatch(threads.length);
        for (int i = 0; i < 10; i++) {
            threads[i] = new CountDownLatch01(() -> {
                try {
                    System.out.println("子线程" + Thread.currentThread().getName() + "开始执行");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();//当前线程调用此方法，则计数减一
            });
        }

        for (CountDownLatch01 thread : threads) {
            thread.start();
        }
        try {
            latch.await();//阻塞当前线程，直到计数器的值为0
            System.out.println("主线程" + Thread.currentThread().getName() + "结束...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
