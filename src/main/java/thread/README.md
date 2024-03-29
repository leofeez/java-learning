## 概念
- 进程：进程是操作系统的基础，是一次程序的运行，是一个程序及其数据在处理机上顺序执行时所发生的活动，是程序在一个数据集合上运行的过程，
它是系统进行分配资源（独立的内存空间）的一个基本单位。
- 线程：线程是在进程中独立运行的子任务，是CPU调度的基本单位，共享进程的内存空间。
- 纤程：由于线程和操作系统的线程（LWP Light weight process）其实是对应的，JVM是运行在用户态的进程，JVM创建线程，需要经过用户态到内核态的切换，
  而纤程即相当于是线程程中的线程，纤程是属于用户态的，不需要经过内核，所以就避免了从用户态到内核态的切换，由JVM进行调度，并且占用空间少，大小为4K，
- 多线程：多线程是异步的，千万不要把在IDE中代码的顺序当做是线程的执行顺序，线程被调用的时机是随机的。
  多线程是通过线程轮流切换并分配处理器执行时间的方式来实现的。
  
进程和线程有什么区别?
进程是一个程序的运行起来的状态，线程是进程中不同的执行路径。
进程是操作系统OS分配资源的基本单位，当一个程序运行起来，OS需要给进程分配独立的内存空间，线程是执行调度的基本单位，线程执行调度共享进程的内存空间
没有自己的独立的内存空间。

---
### 进程调度算法
在不同的操作系统中，CPU时间片的竞争有不同的策略实现，比如Unix系统使用的是时间片算法，而Windows则属于抢占式的。

- 时间片算法，所有的进程排成一个队列。操作系统按照他们的顺序，给每个进程分配一段时间，即该进程允许运行的时间。
如果在 时间片结束时进程还在运行，则CPU将被剥夺并分配给另一个进程。如果进程在时间片结束前阻塞或结束，则CPU当即进行切换。
调度程 序所要做的就是维护一张就绪进程列表，，当进程用完它的时间片后，它被移到队列的末尾。

- 抢占式算法，操作系统会根据他们的优先级、饥饿时间（已经多长时间没有使用过 CPU 了），计算出一个总的优先级来，而这个总优先级最高的会优先使用CPU资源
当进程执行完毕或者自己主动挂起后，操作系统就会重新计算一次所有进程的总优先级，然后再挑一个优先级最高的把 CPU 控制权交给他。
就是说如果一个进程得到了 CPU 时间，除非它自己放弃使用 CPU ，否则将完全霸占 CPU（当代操作系统有一定的机制去检测进程占用CPU的情况，如果时间过长
会强制将该进程挂起）。
  
进程类型：
- IO 密集型：大部分时间用于等待IO
- CPU密集型：大部分时间用于闷头计算

Linux2.6 采用CFS(Completely Fair Scheduler)，完全公平算法：
按优先级分配时间片的比例，记录每个进程执行的时间，如果有一个进程执行时间不到他应该分配CPU时间片的比例，会优先执行。

## 线程的创建
- 继承Thread，重写run()方法
- 实现Runnable，实现run()方法, 推荐，因为java只能单继承，所以实现接口可以避免局限性
- 利用lambda表达式，实现run()方法/利用线程池 `Executors.newCachedThread()`

线程的启动：new Thread(runnable).start();
如果多次调用start()则会抛出 IllegalThreadStateException;

## 线程的五种状态：
- new：当创建线程时，线程就处于new新建状态，此时线程还没有开始执行运行线程中的代码。
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

## sleep yield join
- sleep: 线程休眠，`Thread.sleep(1000)/ TimeUnit.SECONDS.sleep(1)`
- yield: 使当前线程由执行状态，变成为就绪状态(Runnable)，让出cpu时间，在下一个线程执行时候，此线程有可能被执行，也有可能没有被执行。
- join : 当我们调用某个线程的这个方法时，这个方法会挂起调用线程，直到被调用线程结束执行，调用线程才会继续执行。在很多情况下，主线程创建了子线程，如果子线程需要进行大量耗时的计算，而主线程需要等待子线程的执行完成之后才能执行，这时候就可利用join()。

join()方法内部使用了wait()，也就是说join()方法会释放锁。但是这里需要注意的是，**释放的锁对象必须是线程实例**，其他的对象实例是不会释放锁的。

Thread.sleep(0) 和 yield() 有什么区别？
其实这两个方法的实现是依赖于JVM虚拟机具体的实现策略，目前两种方式都可以让当前线程让出CPU时间片。



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

具体见[synchronized原理分析](/thread/_synchronized/README.md)

对象的结构一共由4部分组成，markword, 类型指针，实例数据，padding(对齐填充)可以利用JOL类库显示

## volatile
由于当代CPU运算的速度很快，而运算的变量由于存在物理内存，CPU每次读取写入都要经过内存，但是内存读取的速度满足不了CPU的运算速度，所以在CPU中
又内置了高速缓存区，CPU在运算时，会先去主存中读取对应的数据放到高速缓存区中进行计算，这种方案在单核CPU的情况下是没有问题的，关键在于当代的CPU
都是多核，每个核都有自己的高速缓存区，而主存的数据是共享的，当多个处理器使用到了共享的数据时，所以就会有缓存一致性的问题。

所以为了解决这个问题，就需要处理器去遵循一定的协议，比如Intel CPU的MESI缓存一致性协议，该协议约定，当多核CPU中的处理器读取了主存中的共享
数据，并产生修改之后写回到主存中后，需要通知其他也读取了该共享变量的处理器当前读取的共享数据已经失效，这样其他核就会从主存中重新读取。

在JVM中，多线程之间的数据共享就和上述的情况很相似，两个线程之间共享的变量如果要保证结果的一致性就需要利用volatile关键字，作用如下：

- 保证线程可见性：由于每个线程都有自己的工作空间，对于线程共享的变量每个线程会从主存中拷贝一份到当前线程的工作空间内，线程只能访问自己工作空间的内存，Java的内存模型中定义了8中原子性的操作，大致可分为：
  
  * read和load阶段：从主存中复制变量到当前线程工作空间
    * use和assign阶段：执行代码，改变共享变量，assign赋值给变量副本
    * store和write：用工作空间内的数据刷新到主存
      而由于load，use，assign这三步并不是原子性的操作，就比如 i++，其实可以看作为 读取i的值，修改i的值，写回i的值这三步进行的。
  
- 禁止指令重排序：处理器为了提高程序运行效率，可能会对输入代码进行优化，它不保证程序中各个语句的执行先后顺序同代码中的顺序一致，但是它会保证程序最终执行结果和代码顺序执行的结果是一致的

  ```java
  volatile boolean isOk = false;
  
  // A线程
  A.init();
  isOk = true;
  
  // B线程
  while (!isOk) {
    sleep();
  }
  B.init();
  
  ```

  上面这个例子如果不用volatile修饰，则jvm进行指令重排序之后可能会导致isOk的赋值在A.init()之前，导致B线程和A线程之间的依赖关系发生错误。

  禁止指令重排序是通过内存屏障实现，

  指令重排序遵循的两大原则：

  - **as-if-serial规则**：as-if-serial规则是指不管如何重排序（编译器与处理器为了提高并行度），（单线程）程序的结果不能被改变。这是编译器、Runtime、处理器必须遵守的语义。

  - **happens-before规则**：
    * **程序顺序规则**：一个线程中的每个操作，happens-before于线程中的任意后续操作。
    * **监视器锁规则**：一个锁的解锁，happens-before于随后对这个锁的加锁。
    * **volatile变量规则**：对一个volatile域的写，happens-before于任意后续对这个volatile域的读。
    * **传递性**：如果（A）happens-before（B），且（B）happens-before（C），那么（A）happens-before（C）。
    * **线程start()规则**：主线程A启动线程B，线程B中可以看到主线程启动B之前的操作。也就是start() happens-before 线程B中的操作。
    * **线程join()规则**：主线程A等待子线程B完成，当子线程B执行完毕后，主线程A可以看到线程B的所有操作。也就是说，子线程B中的任意操作，happens-before join()的返回。
    * **中断规则**：一个线程调用另一个线程的interrupt，happens-before于被中断的线程发现中断。
    * **终结规则**：一个对象的构造函数的结束，happens-before于这个对象finalizer的开始。

**概念**：前一个操作的结果可以被后续的操作获取。讲直白点就是前面一个操作把变量a赋值为1，那后面一个操作肯定能知道a已经变成了1。

- happens-before（先行发生）规则如下：

虽然，（1）-happensbefore ->（2）,（2）-happens before->（3），但是计算顺序(1)(2)(3)与(2)(1)(3)对于l、w、area变量的结果并无区别。编译器、Runtime在优化时可以根据情况重排序（1）与（2），而丝毫不影响程序的结果。

+ DCL单例

**volatile最大的缺点就是无法保证原子性。**

synchronized与volatile的区别如下：

- volatile是线程同步的轻量级实现，所以volatile的性能肯定是比synchronized好，volatile只能修饰变量，
  而synchronized可以修饰方法，代码块，在新的JDK中也对synchronized进行了优化，性能也不差。
- 多线程之间使用volatile并不会阻塞线程，synchronized会阻塞线程。
- volatile能保证数据的可见性，但是不能保证原子性，而synchronized可以保证原子性，也可以间接的保证可见性，因为它会将私有内存和公共内存中的数据进行同步。
- volatile 只是实现可见性，synchronized解决的是线程之间对同一个资源的操作的同步性。

## 线程间的通信



## 锁的分类
1. 乐观锁/悲观锁
2. 独享锁/共享锁
3. 互斥锁/读写锁
4. 可重入锁：可重入锁，就是说一个线程在获取某个锁后，还可以继续获取该锁，即允许一个线程多次获取同一个锁。
5. 公平锁/非公平锁
6. 分段锁
7. 偏向锁/轻量级锁/重量级锁
8. 自旋锁

### 乐观锁/悲观锁

#### 乐观锁之 CAS 
Compare And Swap 又称乐观锁，CAS是无锁的操作，在多线程的环境下不需要进行申请锁和释放锁的操作，也不用挂起当前的线程等待操作系统的调度。

在CAS操作中包含三个操作数，内存值V，期望值A，新值B，compareAndSet(obj, expected, newValue);

```java
do {
    expected = oldValue;
    // do something
} while(CAS(内存地址, expected, update))
```

实现原理

底层依靠CPU的原语实现，在intel的CPU中，使用cmpxchg指令，在更新值之前会判断是否是期望值，如果不是，则进行自旋。

- 每个线程都会先获取当前的值，接着走一个原子的CAS操作，原子的意思就是这个CAS操作一定是自己完整执行完的，不会被别人打断；

- 在CAS操作里，比较一下，现在的值跟刚才我获取到的那个值，是否相等，是则说明没有人改过这个值，那么将它设置成累加1之后的一个值
- 若有人在执行CAS时，发现自己之前获取的值与当前的值不一样，说明有其他人修改了值，导致CAS失败，失败之后进入一个循环，再次获取值，再执行CAS操作。

CAS使用：

- AtomicInteger：底层利用Unsafe类，能直接操作CPU

  ```java
  // Unsafe类的getAndAddInt 就是利用了CAS
  public final int getAndAddInt(Object var1, long var2, int var4) {
    	int var5;
    	do {
      	var5 = this.getIntVolatile(var1, var2);
        // 如果CAS失败会进行自旋重试
    	} while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));
    	
    	return var5;
  }
  ```
  

- LongAdder：Java8的新类LongAdder，尝试使用分段CAS以及自动分段迁移的方式来提升多线程高并发执行CAS操作的性能，核心思想是热点分离。

CAS 的问题：

- ABA问题：有一个线程将值更新成B，然后做了一些其他的操作，最后又将值更改为A，那么使用CAS进行检查时会发现它的值没有发生变化，但是实际上却变化了。解决方式为加版本号Version，保持递增，或者加时间戳。
AtomicMarkableReference
  AtomicStampedReference
- CAS开销问题：由于CAS失败会自旋，消耗CPU，如果线程较多、资源抢占激烈、命中率低的情况下，不断的循环会不断的消耗资源，浪费CPU资源。
- 只能保证一个共享变量的原子操作。

#### 悲观锁

![](lock.jpg)

### ReentrantLock

- 可重入锁，必须显示的去上锁和解锁，上锁和解锁的代码必须放在```try {..} finally{..}```中
- 支持设置等待超时时间```lock.tryLock(5, TimeUnit.SECONDS);```
- 支持被打断，```lock.lockInterruptibly();```
- ReentrantLock默认为非公平的锁，构造方法支持公平锁，新来的线程必选先检查是否有线程在等待锁的队列中，如果有则需要进入等待队列，
  非公平的锁对于新来的线程是有可能会抢到锁.
  
### Condition


### CountDownLatch

CountDownLatch这个类使一个线程等待其他线程各自执行完毕后再执行，和```Thread.join()```类似。
是通过一个计数器来实现的，计数器的初始值是线程的数量。每当一个线程执行完毕后，计数器的值就-1，当计数器的值为0时，表示所有线程都执行完毕，然后在闭锁上等待的线程就可以恢复工作了

### CyclicBarrier

允许一组线程互相等待，直到到达某个公共屏障点。它提供的await()可以实现让所有参与者在临界点到来之前一直处于等待状态。当到达的参与者数量达到设置的临界点（parties），则执行CyclicBarrier的Runnable函数。

```java
public CyclicBarrier(int parties, Runnable barrierAction){...}
```



### Phaser

它把多个线程协作执行的任务划分为多个阶段，编程时需要明确各个阶段的任务，每个阶段都可以有任意个参与者，线程都可以随时注册并参与到某个阶段。

- parties: 表示参与者的数量。
- arriveAndAwaitAdvance：表示当前参与者到达并等待其他参与者。
- arriveAndDeregister:表示当前参与者到达，但是不进入下一个阶段。

### ReadWriteLock
- 共享锁：读锁使用共享模式。

- 排他锁：写锁使用独占模式。

  ```java
  static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
  // 读锁
  static Lock readLock = readWriteLock.readLock();
  // 写锁
  static Lock writeLock = readWriteLock.writeLock();
  ```

### Semaphore
Semaphore字面意思是信号量。他主要用于控制有限的资源的访问数量，类似于去餐厅就餐，位置不够了需要等待，当有人离开则新的顾客可以进入就餐。

- 构造参数 permits：最大许可的数量。

- acquire(int permits)：当前申请的许可证，可以指定数量。

- release()：释放当前所占用的许可证。

  ```java
  Semaphore semaphore = new Semaphore();
  // 申请许可证
  semaphore.acquire(1);
  // do something
  semaphore.release(1);
  ```

  

### Exchanger

Exchanger 是 JDK 1.5 开始提供的一个用于两个工作线程之间交换数据的封装工具类，简单说就是一个线程在完成一定的事务后想与另一个线程交换数据，则第一个先拿出数据的线程会一直等待第二个线程，直到第二个线程拿着数据到来时才能彼此交换对应数据。

- exchange(V value): 等待另一个线程到达此交换点（除非当前线程被中断），然后将给定的对象传送给该线程，并接收该线程的对象。
- exchange(V value, long timeout, TimeUnit unit)：等待另一个线程到达此交换点（除非当前线程被中断或超出了指定的等待时间），然后将给定的对象传送给该线程，并接收该线程的对象。


## AQS

AQS指的就是juc包下的AbstractQueuedSynchronizer缩写，在AQS中有两个比较重要的概念：

- state：其实就是共享资源，用volatile修饰，上锁的时候就需要利用CAS更改这个值，释放锁的时候就需要减少该值，当state为0的时候表示锁被释放。

- FIFO线程等待队列：在AQS中维护了一个双向的链表，每个节点Node就是线程节点，多线程竞争共享资源state失败后会加入到该队列中。

![img](./_lock/AQS.png)

在AbstractQueuedSynchronizer中，线程竞争后进入队列，或者锁被释放唤醒出队已经有了实现，而对竞争资源state的获取与释放需要对应的子类去实现，具体需要实现的方法如下：
  - tryAcquire(int arg)：以独占的方式尝试去获取共享资源，成功则返回true，失败则返回false。
  - tryRelease(int arg): 尝试去释放共享资源，成功则返回true，失败则返回false。
  - tryAcquireShared(int arg)：以共享的方式尝试去获取共享资源，成功则返回true，否则返回false。
  - tryReleaseShared(int arg)：以共享的方式尝试去释放共享资源，成功则返回true，否则返回false。
  - isHeldExclusively()：判断当前线程是否以独占的方式占有共享资源，只有在用到Condition的时候才需要实现。

AQS最典型的实现就是ReentrantLock，在ReentrantLock中，有公平锁和非公平锁，所以对于占有共享资源的方式也不一样，这里以非公平锁为例：


  至于为什么是双向链表，是因为需要看一下前一个节点的状态

  对于AQS典型的实现就是ReentrantLock，在当前线程在加锁时，先通过CAS操作将state设置为1，如果CAS失败，则通过tryAcquire，在tryAcquire中第一步先判断state的值是否为0（这里因为state是volatile的，所以这一步判断可以提高效率），如果为0，则再次通过CAS操作将state设置为1，如果这时候CAS又失败了，则通过自旋CAS的方式（addWaiter->compareAndSetTail方法）加入到同步队列的尾部。

VarHandle（1.9之后才有）：

- 普通属性原子性操作；
- 比反射快，直接操纵二进制码

## wait() 和 park()

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