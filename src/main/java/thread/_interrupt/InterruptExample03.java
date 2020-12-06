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
                for (int i = 0; i < 1000000; i++) {
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println(Thread.currentThread().getName() + "接收到停止信号......");
                        throw new InterruptedException();
                    }
                    System.out.println(i);
                }
                System.out.println("线程正常结束......");
            } catch (InterruptedException e) {
                System.out.println("线程通过exception提前结束......");
            }

        }
    }
}
