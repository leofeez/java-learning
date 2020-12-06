package thread.basic;

import java.util.concurrent.TimeUnit;

/**
 * @author leofee
 * @date 2020/12/6
 */
public class YieldExample {

    public static void main(String[] args) {
        new YieldThread().start();
        new PrintNumThread().start();
    }

    /**
     * Thread.yield() 方法，使当前线程由执行状态，变成为就绪状态(Runnable)，
     * 让出cpu时间，在下一个线程执行时候，此线程有可能被执行，也有可能没有被执行。
     */
    private static class YieldThread extends Thread {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (i % 5 == 0) {
                    Thread.yield();
                }
                System.out.println("YieldThread 正在执行, i = " + i);
            }
        }
    }


    private static class PrintNumThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("PrintNumThread 正在执行, i = " + i);
            }
        }
    }


}
