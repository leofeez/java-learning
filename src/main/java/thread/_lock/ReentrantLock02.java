package thread._lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author leofee
 * @date 2020/12/5
 */
public class ReentrantLock02 {

    private final static Lock LOCK = new ReentrantLock();

    public static void main(String[] args) {
        new LockThread("第一个线程").start();

        new LockThread("第二个线程").start();
    }

    static class LockThread extends Thread {

        public LockThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            // lock() 方法必须接try finally
            boolean tryLock = LOCK.tryLock();
            while (!tryLock) {
                System.out.println(Thread.currentThread().getName() + "在等待锁......");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tryLock = LOCK.tryLock();
            }

            System.out.println(Thread.currentThread().getName() + "获取到了锁.....");

            try {
                for (int i = 0; i < 5; i++) {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(Thread.currentThread().getName() + "：" + i);
                }
            }
            // 必须在finally中进行解锁
            catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                LOCK.unlock();
            }
        }

    }
}
