package thread._lock;

import java.util.concurrent.CountDownLatch;

/**
 * 模拟并发请求，当
 */
public class CountDownLatch02 extends Thread {

    static final CountDownLatch START = new CountDownLatch(1);

    static final CountDownLatch END = new CountDownLatch(10);

    public CountDownLatch02(Runnable target) {
        super(target);
    }

    public static void main(String[] args) {
        CountDownLatch02[] threads = new CountDownLatch02[10];

        for (int i = 0; i < 10; i++) {
            threads[i] = new CountDownLatch02(() -> {
                System.out.println("子线程" + Thread.currentThread().getName() + "开始等待");
                // 所有线程在此等待
                try {
                    START.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 执行业务代码
                try {
                    System.out.println("子线程" + Thread.currentThread().getName() + "开始执行");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 计数器减1，表示当前线程执行完毕
                finally {
                    END.countDown();
                }

            });
        }

        for (CountDownLatch02 thread : threads) {
            thread.start();
        }
        try {
            // 开始通知所有线程开始执行业务代码
            START.countDown();

            // 阻塞住线程，直到计数器的值为0
            END.await();
            System.out.println("主线程" + Thread.currentThread().getName() + "结束...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
