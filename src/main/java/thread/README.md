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

线程的优先级
线程的优先级分为1~10个等级

特性：
- 继承性：比如A线程启动B线程，则B线程的优先级与A是一样的。
- 规则性：线程优先级高的，CPU会尽量将执行资源让给优先级比较高的线程。
- 随机性：高优先级的线程不一定会比低优先级的线程先执行完。

守护线程Daemon线程：是一种特殊的线程，当进程中不存在非守护线程，则守护线程会自动销毁，最典型的如GC垃圾回收线程。

## 停止线程 interrupt
真正的线程停止包含：
- 当run() 方法执行完毕
- 使用interrupt() 方法再配合其他方式停止，如 break, return , exception。
- 使用stop() 方法，但是这个方法已经是过期作废的方法了，不推荐使用，会导致某些清理性的工作无法完成，
也会导致对锁住的对象提前解锁产生数据的不一致性。

interrupt()，该方法仅仅是在当前线程打了一个停止的标记，并不是真正的停止，所以是需要通过其他的方式配合来实现真正的线程终止，
具体方法如下：
- interrupt() + break;
- interrupt() + return;
- interrupt() + throw new InterruptedException();

## 暂停线程 

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

### Semaphore
限流，类似于车道和收费站


## AQS

VarHandle：
- 普通属性原子性操作；
- 比反射快，直接操纵二进制码

## ThreadLocal
