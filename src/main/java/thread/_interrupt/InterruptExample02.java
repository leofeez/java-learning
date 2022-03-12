package thread._interrupt;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author leofee
 * @date 2020/12/4
 */
public class InterruptExample02 {

    static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        BreakThread t = new BreakThread();
        t.start();

        TimeUnit.SECONDS.sleep(1);

        // 标记线程为终止状态
        t.interrupt();

        TimeUnit.SECONDS.sleep(1);

        System.out.println("线程是否还在运行：" + t.isAlive());
    }

    /**
     * 利用break+interrupt 实现线程结束
     */
    private static class BreakThread extends Thread {

        @Override
        public void run() {
            System.out.println("线程开始执行......");
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println(count.incrementAndGet());
            }
            System.out.println("接收到停止信号......");
            return;
        }
    }
}
