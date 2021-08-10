package thread.basic;

import java.util.concurrent.TimeUnit;

/**
 * @author leofee
 * @date 2020/12/6
 */
public class DaemonExample {

    private static int count = 0;
    private static int COUNT_DAEMON = 0;

    static DaemonThread daemonThread;

    public static void main(String[] args) throws InterruptedException {
        MainThread mainThread = new MainThread();
        mainThread.start();

//        mainThread.join();
//
//        System.out.println(mainThread.getState());
    }

    private static class MainThread extends Thread {

        @Override
        public void run() {
            super.run();
            System.out.println("主线程开始运行......");

            daemonThread = new DaemonThread();
            daemonThread.setDaemon(true);
            daemonThread.start();

            while (count < 5) {
                System.out.println(++count);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("主线程运行结束......");
        }
    }

    private static class DaemonThread extends Thread {

        @Override
        public void run() {

            System.out.println("守护线程开始运行......");

            super.run();

            while (true) {
                System.out.println("守护线程" + ++COUNT_DAEMON);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
