package thread._lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 利用 {@link Condition} 实现交替打印
 */
public class Condition02 extends Thread {

    static boolean flag = true;

    static ReentrantLock lock = new ReentrantLock();

    static Condition condition = lock.newCondition();

    public Condition02(Runnable target) {
        super(target);
    }

    public static void main(String[] args) {
        new Condition02(() -> {
            for (; ; ) {
                A();
            }
        }).start();
        new Condition02(() -> {
            for (; ; ) {
                B();
            }
        }).start();
    }

    public static void A() {
        lock.lock();

        try {
            while (!flag) {
                condition.await();
            }
            System.out.println("A");
            TimeUnit.SECONDS.sleep(1);
            flag = false;
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void B() {
        lock.lock();
        try {
            while(flag) {
                condition.await();
            }
            System.out.println("B");
            TimeUnit.SECONDS.sleep(1);
            flag = true;
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
