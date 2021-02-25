package thread._lock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrier01 extends Thread {

    public CyclicBarrier01(Runnable target) {
        super(target);
    }

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(20, () -> {
            System.out.println("已经增加到20的整数倍了");
        });

        for (int i = 0; i < 100; i++) {
            new CyclicBarrier01(() -> {
                try {
                    // 每个线程到这都进行等待，当满足barrier的数量执行barrier的Runnable
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
