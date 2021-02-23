package thread._lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author leofee
 * @date 2020/12/5
 */
public class Lock03 {

    private final static Lock LOCK = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        new LockThread("第一个线程").start();

        LockThread thread = new LockThread("第二个线程");
        thread.start();

        TimeUnit.SECONDS.sleep(3);
        thread.interrupt();
    }

    static class LockThread extends Thread {

        public LockThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            // lock() 方法必须接try finally
            boolean tryLock = false;
            try {
                tryLock = LOCK.tryLock(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                // 当线程被标记为interrupt时抛出该异常
//                e.printStackTrace();
                System.out.println(Thread.currentThread().getName() + "已经被interrupt了，直接退出了......");
                return;
            }

            if (!tryLock) {
                System.out.println(Thread.currentThread().getName() + "已经等了 5s 还是没拿到锁，直接退出了......");
                return;
            }

            System.out.println(Thread.currentThread().getName() + "获取到了锁.....");

            boolean tryLock2 = LOCK.tryLock();
            System.out.println("ReentrantLock 是否是可重入锁" + tryLock2);

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
                if (tryLock) {
                    LOCK.unlock();
                }
            }
        }

    }
}
