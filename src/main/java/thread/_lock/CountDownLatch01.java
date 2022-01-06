package thread._lock;

import java.util.concurrent.CountDownLatch;

/**
 * countDownLatch这个类使一个线程等待其他线程各自执行完毕后再执行。
 * 是通过一个计数器来实现的，计数器的初始值是线程的数量。
 * 每当一个线程执行完毕后，计数器的值就-1，当计数器的值为0时，
 * 表示所有线程都执行完毕，然后在闭锁上等待的线程就可以恢复工作了。
 */
public class CountDownLatch01 extends Thread {

    public CountDownLatch01(Runnable target, String name) {
        super(target, name);
    }

    public static void main(String[] args) {
        CountDownLatch01[] threads = new CountDownLatch01[10];
        final CountDownLatch latch = new CountDownLatch(threads.length);
        System.out.println("大巴车一共有 " + latch.getCount() + " 个座位");
        for (int i = 0; i < 10; i++) {
            threads[i] = new CountDownLatch01(() -> {
                System.out.println(Thread.currentThread().getName() + "已经上车，并占了一个位置");
                latch.countDown();//当前线程调用此方法，则计数减一
            }, "乘客 " + i);
        }

        for (CountDownLatch01 thread : threads) {
            thread.start();
        }
        try {
            latch.await();//阻塞当前线程，直到计数器的值为0
            System.out.println("剩余座位 " + latch.getCount() + " 个座位，坐稳了，要发车了...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
