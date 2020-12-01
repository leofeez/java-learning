package thread;

import java.util.concurrent.TimeUnit;

/**
 * @author leofee
 * @date 2020/12/1
 */
public class Sleep_Yield_Join_Thread {

    private static Thread normalThead;

    private static Thread sleepThread;

    public static void main(String[] args) {

//        new SleepThread().start();
//        new YieldThread().start();
//        new PrintNumThread().start();


        Thread joinThread = new JoinThread();
        normalThead = new NormalThread();
        sleepThread = new SleepThread();
        joinThread.start();
        normalThead.start();
        sleepThread.start();
    }

    private static class SleepThread extends Thread {

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("SleepThread " + Thread.currentThread().getName() + " 开始执行");
        }
    }

    /**
     * Thread.yield() 方法，使当前线程由执行状态，变成为就绪状态(Runnable)，
     * 让出cpu时间，在下一个线程执行时候，此线程有可能被执行，也有可能没有被执行。
     */
    private static class YieldThread extends Thread {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                if (i % 5 == 0) {
                    Thread.yield();
                }
                System.out.println("YieldThread " + Thread.currentThread().getName() + " 正在执行, i = " + i);
            }
        }
    }

    private static class PrintNumThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println("PrintNumThread " + Thread.currentThread().getName() + " 正在执行, i = " + i);
            }
        }
    }

    private static class NormalThread extends Thread {
        @Override
        public void run() {
            System.out.println("NormalThread " + Thread.currentThread().getName() + " 正在执行");
        }
    }

    /**
     * 当我们调用某个线程的这个方法时，这个方法会挂起调用线程，直到被调用线程结束执行，调用线程才会继续执行。
     */
    private static class JoinThread extends Thread {
        @Override
        public void run() {
            try {
                System.out.println("先让 NormalThread 线程先执行");
                normalThead.join();
                System.out.println("回到 JoinThread, NormalThread 线程状态为" + normalThead.getState());

                System.out.println("再让 SleepThread 线程先执行500ms");
                sleepThread.join(500);
                System.out.println("回到 JoinThread, SleepThread 线程状态为" + sleepThread.getState());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Join2Thread " + Thread.currentThread().getName() + " 正在执行");
        }
    }
}
