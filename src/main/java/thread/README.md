## 概念
- 进程：进程是操作系统的基础，是一次程序的运行，是一个程序及其数据在处理机上顺序执行时所发生的活动，是程序在一个数据集合上运行的过程，
它是系统进行分配和调度的一个独立单元。
- 线程：线程是在进程中独立运行的子任务。
- 多线程：多线程是异步的，千万不要把在IDE中代码的顺序当做是线程的执行顺序，线程被调用的时机是随机的。
## 线程的创建
- 继承Thread，重写run()方法
- 实现Runnable，实现run()方法, 推荐，因为java只能单继承，所以实现接口可以避免局限性
- 利用lambda表达式，实现run()方法/利用线程池 `Executors.newCachedThread()`

线程的启动：new Thread(runnable).start();
如果多次调用start()则会抛出 IllegalThreadStateException;

## sleep yield join
- sleep: 线程休眠，`Thread.sleep(1000)/ TimeUnit.SECONDS.sleep(1)`
- yield: 使当前线程由执行状态，变成为就绪状态(Runnable)，让出cpu时间，在下一个线程执行时候，此线程有可能被执行，也有可能没有被执行。
- join : 当我们调用某个线程的这个方法时，这个方法会挂起调用线程，直到被调用线程结束执行，调用线程才会继续执行。

线程的六种状态：
- new
- runnable:
- waiting:
- time_waiting
- blocked
- terminated

## synchronized
synchronized 是可重入锁，在同步方法中调用另外一个同步方法并不会死锁，因为两次获取的锁都是同一个。
程序出现Exception则会释放锁。
写方法加synchronized，但是读方法不加synchronized则容易产生脏读。

synchronized用在方法上和synchronized(this){整个方法}是同等效果。

synchronized(Object): 不能用String常量，Integer, Long, 字符串会存在常量池中
并且锁住的对象不能指向别的实例，所以以对象作为锁的时候必须加final防止指向的地址发生改变。

JDK 早期的时候，synchronized 是重量级的锁

### 原理


锁升级
synchronized 默认情况下，使用偏向锁，如果有其他线程争用，升级为自旋锁，类似于while(i< 10) i++, 自旋10次
如果此时还是无法获取到锁，则升级为重量级锁 OS锁
改进后的synchronized并不比Automatic差

偏向锁:

自旋锁，会占用CPU时间，不经过内核态，效率高，适用于加锁的执行时间短，线程数不能多
OS锁（系统锁），适合执行时间长，线程数多

## 停止线程
- 使用退出标记，使线程正常退出，也就是当run方法完成后线程终止。
- 使用stop()强行终止线程，不推荐！stop() 和 suspend() 以及resume() 都是过期作废的方法。
- 使用interrupt()，该方法仅仅是在当前线程打了一个停止的标记，并不是真正的停止。


## volatile
作用如下：
- 保证线程可见性：每个线程都有自己的工作空间
    + MESI 缓存一致性协议
- 禁止指令重排序
    + DCL单例
    
## CAS 
Compare And Swap 又称乐观锁
cas(expected, update);
- AtomicInteger
- LongAdder

## LOCK 

### ReentrantLock

### CyclicBarrier

### Phaser

### ReadWriteLock
- 共享锁

- 排他锁