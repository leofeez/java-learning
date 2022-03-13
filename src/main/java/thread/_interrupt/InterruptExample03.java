package thread._interrupt;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author leofee
 * @date 2020/12/4
 */
public class InterruptExample03 {

    public static void main(String[] args) throws InterruptedException {
        ExceptionThread t2 = new ExceptionThread();
        t2.start();

        TimeUnit.MILLISECONDS.sleep(500);

        // 标记线程为终止状态
        t2.interrupt();

        TimeUnit.MILLISECONDS.sleep(500);

        System.out.println("线程是否还在运行：" + t2.isAlive());
    }

    /**
     * 利用 Exception + interrupt()实现线程的终止
     */
    private static class ExceptionThread extends Thread {

        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    TimeUnit.SECONDS.sleep(1);
                }
                System.out.println(Thread.currentThread().getName() + "接收到停止信号......");
                System.out.println("线程正常结束......");
                return;
            } catch (InterruptedException e) {
                // 在 sleep 方法中抛出 InterruptException 时会清除Thread的interrupted状态
                System.out.println("线程通过exception提前结束......" + Thread.currentThread().isInterrupted());
                Thread.currentThread().interrupt();
                throw new RuntimeException("线程被动停止", e);
            }

        }
    }
}
