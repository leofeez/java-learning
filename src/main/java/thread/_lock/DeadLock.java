package thread._lock;

import java.util.concurrent.TimeUnit;

/**
 * @author leofee
 * @date 2021/10/29
 */
public class DeadLock {

    static final Object MONITOR_1 = new Object();
    static final Object MONITOR_2 = new Object();




    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            printA();
        }, "DeadLock-A").start();


        new Thread(() -> {
            printB();
        }, "DeadLock-B").start();
    }

    public static void printA() {
        synchronized (MONITOR_1) {
            System.out.println("A");
            sleep();

            synchronized (MONITOR_2) {

            }
        }


    }

    public static void printB() {
        synchronized (MONITOR_2) {
            System.out.println("B");
            sleep();

            synchronized (MONITOR_1) {

            }
        }
    }

    static void sleep() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
