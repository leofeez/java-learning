package thread._wait_notify;

/**
 * @author leofee
 * @date 2021/1/16
 */
public class WaitNotify11 extends Thread {

    private static boolean FLAG = false;

    public WaitNotify11() {
    }

    public WaitNotify11(Runnable target, String name) {
        super(target, name);
    }

    public static void main(String[] args) {
        WaitNotify11 obj = new WaitNotify11();

        for (int i = 0; i < 10; i++) {
            new WaitNotify11(() -> {
                obj.print();
            }, "A" + i).start();

            new WaitNotify11(() -> {
                obj.print2();
            }, "B" + i).start();
        }
    }

    public synchronized void print() {
        while (FLAG) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + " *****");
        }
        FLAG = true;
        notifyAll();
    }

    public synchronized void print2() {
        while (!FLAG) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + " %%%%%");
        }
        FLAG = false;
        notifyAll();
    }
}
