package thread._lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLock01 extends Thread {

    static CountDownLatch countDownLatch = new CountDownLatch(20);

    static int count = 0;

    // 传统的
    static Lock reentrantLock = new ReentrantLock();

    static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    // 读锁
    static Lock readLock = readWriteLock.readLock();
    // 写锁
    static Lock writeLock = readWriteLock.writeLock();


    public ReadWriteLock01(Runnable target, String name) {
        super(target, name);
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] readThreads = new Thread[18];
        for (int i = 0; i < 18; i++) {
            readThreads[i] = new ReadWriteLock01(() -> {
                read(readLock);
            }, "读线程" + i);
        }

        Thread[] writeThreads = new Thread[2];
        for (int i = 0; i < 2; i++) {
            writeThreads[i] = new ReadWriteLock01(() -> {
                write(writeLock);
            }, "写线程" + i);
        }


        for (Thread readThread : readThreads) {
            readThread.start();
        }

        for (Thread writeThread : writeThreads) {
            writeThread.start();
        }


        countDownLatch.await();

        System.out.println();
        System.out.printf("最终的值为%s", count);

    }

    public static void read(Lock lock) {
        try {
            lock.lock();

            System.out.println(Thread.currentThread().getName() + " reading");

            count++;

            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            countDownLatch.countDown();
            lock.unlock();
        }
    }

    public static void write(Lock lock) {
        try {
            lock.lock();

            System.out.println(Thread.currentThread().getName() + " writing");

            Thread.sleep(1000);

            count++;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            countDownLatch.countDown();
            lock.unlock();
        }
    }
}
