package thread._interrupt;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author leofee
 * @date 2020/12/2
 */
public class InterruptExample04 {

    public static void main(String[] args) throws InterruptedException {

        MyThread myThread = new MyThread("named thread");

        myThread.start();
        TimeUnit.SECONDS.sleep(5);
        System.out.println(myThread.getName() + " ready to interrupt");
        myThread.interrupt();
    }


    private static class MyThread extends Thread {

        public MyThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            boolean parkedOnce = false;
            int i = 0;
            for (; ; ) {
                System.out.println(Thread.currentThread().getName() + "的 i = " + i++);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (i > 3 && !parkedOnce) {
                    System.out.println(Thread.currentThread().getName() + " park ");
                    LockSupport.park(this);
                    parkedOnce = true;
                    System.out.println(Thread.currentThread().getName() + " continue ");
                }
            }
        }
    }
}
