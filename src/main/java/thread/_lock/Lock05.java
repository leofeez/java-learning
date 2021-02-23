package thread._lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Lock05 extends Thread {
    /** ReentrantLock可以指定为公平锁*/
    static Lock LOCK = new ReentrantLock();

    public Lock05(String name, Runnable target) {
        super(target, name);
    }

    public static void main(String[] args) throws InterruptedException {
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            threadList.add(new Lock05("第" + i + "个线程", Lock05::lock));
        }

        for (Thread thread : threadList) {
            thread.start();
        }

        Thread.sleep(500);

        new Lock05("第11个线程", Lock05::lock).start();

    }

    public static void lock() {
        LOCK.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "获取锁");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LOCK.unlock();
        }
    }
}
