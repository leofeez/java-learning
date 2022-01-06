package thread._lock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class CyclicBarrier01 extends Thread {

    public CyclicBarrier01(Runnable target, String name) {
        super(target, name);
    }

    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(20, () -> {
            System.out.println("乘客满了，开始发车......");
        });

        for (int i = 0; i < 100; i++) {
            TimeUnit.MILLISECONDS.sleep(500);
            new CyclicBarrier01(() -> {
                try {
                    // 每个线程到这都进行等待，当满足barrier的数量执行barrier的Runnable
                    System.out.println(Thread.currentThread().getName() + " 已经上车");
                    cyclicBarrier.await();
                    System.out.println(Thread.currentThread().getName() + " 已经在车上欣赏沿途风景了");
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, "thread-" + i).start();
        }
    }
}
