package thread._lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 模拟{@link java.util.concurrent.locks.ReentrantLock}
 */
public class MyReentrantLock implements Lock {

    private final Sync sync;

    private static class Sync extends AbstractQueuedSynchronizer {

        /**
         * 尝试获取锁
         * @param acquires
         * @return {@code true} 表示可以拿到锁，否则返回 {@code false}
         */
        @Override
        protected boolean tryAcquire(int acquires) {
            // 预先判断 state 是否为0
            // 这里之所以可以这样判断是因为 state 用了volatile修饰，可以保证多线程之间的可见性
            // 如果为0才可以进行CAS设置
            if (getState() == 0) {
                if (compareAndSetState(0, acquires)) {
                    setExclusiveOwnerThread(Thread.currentThread());
                    return true;
                }
            }
            // 判断当前线程是否是拥有独占锁的线程，是，则可重入
            else if (Thread.currentThread() == getExclusiveOwnerThread()) {
                setState(getState() + acquires);
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            // 防止当前持有锁的线程和当前释放锁的线程不一致
            if (Thread.currentThread() != getExclusiveOwnerThread()) {
                throw new IllegalMonitorStateException();
            }
            if (getState() - arg == 0) {
                setExclusiveOwnerThread(null);
                setState(0);
                return true;
            }
            return false;
        }

        /**
         * 一种便捷的方式获取到{@link Condition}
         * @return ConditionObject
         * @see java.util.concurrent.locks.AbstractQueuedSynchronizer.ConditionObject
         */
        public ConditionObject newCondition() {
            return new ConditionObject();
        }

        /**
         * 上锁
         */
        public void lock() {
            if (getState() == 0) {
                if (compareAndSetState(0, 1)) {
                    setExclusiveOwnerThread(Thread.currentThread());
                    return;
                }
            }
            acquire(1);
        }


    }

    public MyReentrantLock() {
        sync = new Sync();
    }


    @Override
    public void lock() {
        sync.lock();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
