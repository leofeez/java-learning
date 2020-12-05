package thread._interrupt;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author leofee
 * @date 2020/12/4
 */
public class InterruptExample02 {

    static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
//        BreakThread t = new BreakThread();
//        t.start();
//
//        TimeUnit.SECONDS.sleep(1);
//
//        // 标记线程为终止状态
//        t.interrupt();
//
//        TimeUnit.SECONDS.sleep(1);
//
//        System.out.println("线程是否还在运行：" + t.isAlive());

        ExceptionThread t2 = new ExceptionThread();
        t2.start();

        TimeUnit.SECONDS.sleep(1);

        // 标记线程为终止状态
        t2.interrupt();

        TimeUnit.SECONDS.sleep(1);

        System.out.println("线程是否还在运行：" + t2.isAlive());
    }

    /**
     * 利用break+interrupt 实现线程结束
     */
    private static class BreakThread extends Thread {

        @Override
        public void run() {
            System.out.println("线程开始执行......");
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("接收到停止信号......");
                    break;
                }
                System.out.println(count.incrementAndGet());
            }
        }
    }

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
