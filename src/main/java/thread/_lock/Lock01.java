package thread._lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author leofee
 * @date 2020/12/5
 */
public class Lock01 {

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
            LOCK.lock();
            try {
                for (int i = 0; i < 5; i++) {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(Thread.currentThread().getName() + "：" + i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 必须在finally中进行解锁
            finally {
                LOCK.unlock();
            }
        }
    }
}
