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
- new：当创建线程时，线程就处于new新建状态
- runnable:就绪状态，当创建的线程调用start()方法表示线程处于就绪状态，等待系统调度。
- running: 运行状态，就绪状态的线程获得CPU资源时进入运行状态，Running和Runnable状态可以互相切换，有可能线程Running一段时间后被某个高
优先级的线程强占了CPU的资源，此时线程就从Running状态转变为Runnable状态。
- waiting: 调用wait()，当前线程放弃CPU资源，进度线程等待池中，这种状态线程无法自动唤醒，需要等待其他线程调用notify()或者notifyAll()，
还有就是join()，sleep()方法或者遇到一个阻塞的IO操作。
- time_waiting: join(time), sleep(time)
- blocked：阻塞状态，线程尝试获取同步锁，若同步锁正在被其他线程持有，从而进入阻塞状态。
- terminated：指线程执行完了或者线程执行时发生了异常

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

## 死锁
在设计程序的时候一定要避免双方互相持有对方的锁的情况。

## synchronized
如果某一个资源被多个线程共享，为了避免因为资源抢占导致资源数据错乱，即线程安全问题，这时候需要对线程进行同步，
那么synchronized就是实现线程同步的关键字，可以说在并发控制中是必不可少的部分。

具体见[synchronized原理分析](https://github.com/leofeez/java-learning/blob/master/src/main/java/thread/_synchronized/README.md)

## 锁的分类

锁升级
synchronized 默认情况下，使用偏向锁，如果有其他线程争用，升级为自旋锁，类似于while(i< 10) i++, 自旋10次
如果此时还是无法获取到锁，则升级为重量级锁 OS锁
改进后的synchronized并不比Atomic差

偏向锁:

自旋锁，会占用CPU时间，不经过内核态，效率高，适用于加锁的执行时间短，线程数不能多
OS锁（系统锁），适合执行时间长，线程数多

## volatile
作用如下：
- 保证线程可见性：由于每个线程都有自己的工作空间，对于线程共享的变量每个线程会从主存中拷贝一份到当前线程的工作空间内，
大致可分为几个步骤：
    * read和load阶段：从主存中复制变量到当前线程工作空间
    * use和assign阶段：执行代码，改变共享变量
    * store和write：用工作空间内的数据刷新到主存
而由于load，use，assign这三步并不是原子性的操作，就比如 i++，其实可以看作为 i = i + 1;
    + MESI 缓存一致性协议
- 禁止指令重排序
    + DCL单例
    
volatile最大的缺点就是无法保证原子性。

synchronized与volatile的区别如下：
- volatile是线程同步的轻量级实现，所以volatile的性能肯定是比synchronized好，volatile只能修饰变量，
而synchronized可以修饰方法，代码块，在新的JDK中也对synchronized进行了优化，性能也不差。
- 多线程之间使用volatile并不会阻塞线程，synchronized会阻塞线程。
- volatile能保证数据的可见性，但是不能保证原子性，而synchronized可以保证原子性，也可以间接的保证可见性，因为
它会将私有内存和公共内存中的数据进行同步。
- volatile 只是实现可见性，synchronized解决的是线程之间对同一个资源的操作的同步性。
    
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
ThreadLocal叫做线程变量，意思是ThreadLocal中填充的变量属于当前线程，该变量对其他线程而言是隔离的。
ThreadLocal为变量在每个线程中都创建了一个副本，那么每个线程可以访问自己内部的副本变量。

用途：
- 线程间的数据隔离，实现线程安全：如SimpleDateFormat
- 用于存储事务信息，如Spring的声明式事务，保证拿到的都是同一个Connection从而形成一个完整的事务。
- 对象跨层传递：如存储用户信息，UserInfo，防止层与层之间多余的传递。

具体分析见[ThreadLocal源码分析](https://github.com/leofeez/java-learning/blob/master/src/main/java/thread/_threadlocal/README.md)

### Reference
- 强引用：
- 软引用：一个对象在被软引用指向的时候，只有内存不够了才会进行回收，一般用作缓存，如从内存中读取大图片。
- 弱引用：弱引用一遇到gc就会被回收，如ThreadLocalMap中的Entry就是继承于WeakReference
- 虚引用：用于操作堆外内存，

### 内存泄漏，内存溢出