package thread._lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Condition01 extends Thread {

    static ReentrantLock lock = new ReentrantLock();

    static Condition condition = lock.newCondition();

    public Condition01(Runnable target) {
        super(target);
    }

    public static void main(String[] args) throws InterruptedException {
        new Condition01(Condition01::await).start();

        TimeUnit.SECONDS.sleep(1);

        new Condition01(Condition01::signal).start();
    }

    public static void await() {
        lock.lock();
        try {
            System.out.println("await start");
            condition.await();
            System.out.println("await end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void signal() {
        lock.lock();
        try {
            System.out.println("signal start");
            condition.signal();
            System.out.println("signal end");
        } finally {
            lock.unlock();
        }
    }
}
