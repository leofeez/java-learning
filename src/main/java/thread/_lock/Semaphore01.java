package thread._lock;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Semaphore01 extends Thread {


    public Semaphore01(Runnable target) {
        super(target);
    }

    public static void main(String[] args) {

        // 线程中进行acquire后permits就会减1
        Semaphore semaphore = new Semaphore(1);

        new Semaphore01(() -> {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("T1 is running");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("T1 is end");
            semaphore.release();
        }).start();

        new Semaphore01(() -> {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("T2 is running");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("T2 is end");
            semaphore.release();
        }).start();
    }
}
