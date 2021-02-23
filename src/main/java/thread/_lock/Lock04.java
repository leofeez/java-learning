package thread._lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Lock04 extends Thread {

    static Lock LOCK = new ReentrantLock();

    public Lock04(Runnable target, String name) {
        super(target, name);
    }

    public static void main(String[] args) throws InterruptedException {
        Lock04 thread = new Lock04(Lock04::lock, "线程1");
        thread.start();
        Thread.sleep(2000);
        thread.interrupt();
    }

    static void lock() {
        try {
            LOCK.lock();
//            LOCK.lockInterruptibly();
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LOCK.unlock();
        }
    }
}
