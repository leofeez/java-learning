package thread._abc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class T01_Condition {
    static Thread t1;
    static Thread t2;
    static Thread t3;


    /**
     * 实现线程顺序执行
     */
    public static void main(String[] args) throws InterruptedException {
        method01();
    }

    /**
     * 利用 Condition
     */

    static volatile int order = 1;

    static void method01() {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        Condition condition2 = lock.newCondition();
        Condition condition3 = lock.newCondition();

        t1 = new Thread(() -> {
            lock.lock();
            try {
                while (order != 1) {
                    condition.await();
                }
                System.out.println("第一个线程");
                order = 2;
                condition2.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        t2 = new Thread(() -> {
            lock.lock();
            try {
                while (order != 2) {
                    condition2.await();
                }
                System.out.println("第二个线程");
                order = 3;
                condition3.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        t3 = new Thread(() -> {
            lock.lock();
            try {
                while (order != 3) {
                    condition3.await();
                }
                System.out.println("第三个线程");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        t3.start();
        t2.start();
        t1.start();
    }
}
