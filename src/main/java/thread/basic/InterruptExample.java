package thread.basic;

/**
 * @author leofee
 * @date 2020/12/2
 */
public class InterruptExample {

    public static void main(String[] args) {

        MyThread myThread = new MyThread("named thread");

        myThread.start();
        myThread.interrupt();
        System.out.println(Thread.interrupted());
        System.out.println(myThread.isInterrupted());

        // 标记线程为终止状态
        Thread.currentThread().interrupt();
        // isInterrupted 不会清除状态标记
        System.out.println(Thread.currentThread().isInterrupted());
        // interrupted 会对线程的状态标记进行清除
        System.out.println(Thread.interrupted());
        System.out.println(Thread.interrupted());

    }


    private static class MyThread extends Thread {

        public MyThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            System.out.println(this.getName());
            System.out.println(Thread.currentThread().getName());
        }
    }
}
