package thread.basic;

import java.util.concurrent.TimeUnit;

/**
 * @author leofee
 * @date 2020/12/6
 */
public class DaemonExample {

    private static int count = 0;

    public static void main(String[] args) throws InterruptedException {

        DaemonThread daemonThread = new DaemonThread();
        daemonThread.setDaemon(true);
        daemonThread.start();


        Thread.sleep(10000);

        System.out.println("主线程结束了，守护线程也会自动销毁......");

    }

    private static class DaemonThread extends Thread {

        @Override
        public void run() {
            super.run();

            while (true) {
                System.out.println(++count);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
