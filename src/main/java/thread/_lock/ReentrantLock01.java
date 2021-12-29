package thread._lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author leofee
 * @date 2020/12/5
 */
public class ReentrantLock01 {

    private final static Lock LOCK = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        LockThread thread1 = new LockThread("第一个线程");
        thread1.start();

        LockThread thread2 = new LockThread("第二个线程");
        thread2.start();

        TimeUnit.SECONDS.sleep(3);

        thread1.interrupt();

    }

    static class LockThread extends Thread {

        public LockThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            // lock() 方法必须接try finally
            try {
                LOCK.lockInterruptibly();
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " 发生异常" + e.getMessage());
                e.printStackTrace();
            }
//            LOCK.lock();
            try {
                for (int i = 0; i < 10; i++) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "：" + i);
                }
            }
            // 必须在finally中进行解锁
            finally {
                LOCK.unlock();
            }
        }
    }
}
