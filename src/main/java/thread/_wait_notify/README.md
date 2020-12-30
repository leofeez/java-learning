## 线程间的通信


### wait(), notify()

wait()：作用是使当前线程进行等待。

notify()：作用是通知那些正在等待该对象锁的线程，如果有多个线程等待，则由线程规划器随机挑选一个呈wait状态的线程，使他获取该对象的锁，需要
注意的是，在执行notify()当前线程不会立即释放锁，而是要等退出当前synchronized方法或者代码块，当前线程才会释放锁，其他wait线程才能真正
获取到锁。

wait() 和 notify()方法调用前都需要先获得锁，否则会抛出`IllegalMonitorStateException`