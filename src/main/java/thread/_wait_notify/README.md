## 线程间的通信
线程之间通信的方式：
- 管道
- 消息队列
- 共享内存
- 信号量
- Socket

### wait(), notify()

wait()：作用是使当前线程进行等待。

notify()：作用是通知那些正在等待该对象锁的线程，如果有多个线程等待，则由线程规划器随机挑选一个呈wait状态的线程，使他获取该对象的锁，需要
注意的是，在执行notify()当前线程不会立即释放锁，而是要等退出当前synchronized方法或者代码块，当前线程才会释放锁，其他wait线程才能真正
获取到锁。

notifyAll():作用是通知所有正在等待该线程的对象锁的线程。

wait() 和 notify()方法调用前都需要先获得锁，否则会抛出`IllegalMonitorStateException`。

wait(time)支持指定时间，但是前提是必须是能够拿到锁才会继续执行。

notify方法不能比wait()方法先执行，否则wait的线程不能被正常唤醒，可以设置wait的时间。

在多个线程的情况，如果使用wait()，则需要使用notifyAll(),否则会产生假死，导致所有线程都在waiting状态。

对于执行的前置条件判断应该使用while去判断，如下：
 ```java
    while (waitCondition) {
        wait();
    }
    // 不满足wait条件的处理
```
如果不采用while的判断而采用if去判断，会导致线程被唤醒后不经过waitCondition就直接执行了后置的代码，会出现不是预期的结果。

### 通过管道进行线程间通信
pipedStream 是一种特殊的流，用于在不同线程之间实现线程间传输数据，一个线程发送数据到输出管道，另一个线程从管道中读取数据。
- PipedInputStream 和 PipedOutputStream
- PipedReader 和 PipedWriter