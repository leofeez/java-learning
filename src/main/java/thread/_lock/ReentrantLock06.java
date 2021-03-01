package thread._lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLock06 extends Thread {

    public ReentrantLock06(Runnable target) {
        super(target);
    }

//    static Lock lock = new ReentrantLock();
    static Lock lock = new MyReentrantLock();

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new ReentrantLock06(() -> {
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName());
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }).start();
        }
    }
}
