# Synchronized

如果某一个资源被多个线程共享，为了避免因为资源抢占导致资源数据错乱，即线程安全问题，这时候需要对线程进行同步，
那么synchronized就是实现线程同步的关键字，可以说在并发控制中是必不可少的部分。

## 特性

- 原子性：被synchronized修饰的方法或者代码块，在多线程的场景下都是需要先获取锁，再执行，最后释放锁，这中间的
过程无法被打断（除了利用Thread#stop() 方法，已废弃！）。

- 可见性：synchronized对一个类或对象加锁时，一个线程如果要访问该类或对象必须先获得它的锁，而这个锁的状态对于其他任何线程都是可见的，
并且在释放锁之前会将对变量的修改刷新到主存当中，保证资源变量的可见性，如果某个线程占用了该锁，其他线程就必须在锁池中等待锁的释放。

- 有序性：synchronized保证了每个时刻都只有一个线程访问同步代码块，也就确定了线程执行同步代码块是分先后顺序的，保证了有序性。

- 可重入性：当一个线程试图操作一个由其他线程持有的对象锁的临界资源时，将会处于阻塞状态，但当一个线程再次请求自己持有对象锁的临界资源时，
这种情况属于重入锁。通俗一点讲就是说一个线程拥有了锁仍然还可以重复申请锁。

## 介绍

synchronized 取得的锁都是对象锁，而不是把一段代码或者方法当做锁。

synchronized 是可重入锁，在同步方法中调用另外一个同步方法可以直接获取到自己的内部锁，
可重入锁也支持父子间的继承关系，这样就不会产生死锁，因为两次获取的锁都是同一个。

synchronized 是非公平的锁。
程序出现Exception则会释放锁。

写方法加synchronized，但是读方法不加synchronized则容易产生脏读。

synchronized修饰符不支持继承，如果父类定了synchronized，子类重写方法要想实现同步，则也必须加synchronized。

synchronized用在方法上和synchronized(this){整个方法}是同等效果，但是建议锁粒度越小越好，所以推荐使用synchronized代码块。

synchronized(Object): 不能用String常量，Integer, Long, 字符串会存在常量池中
并且锁住的对象不能指向别的实例，所以以对象作为锁的时候必须加final防止指向的地址发生改变。

## 实现原理

这里我们先探讨synchronized基于重量级锁的实现原理（因为JDK1.6之后synchronized进行了优化增加了锁升级的机制），在探讨原理之前我们首先看一下
被synchronized加锁后的代码和对应的字节码：

```java
public class Synchronized00 {

    public static void main(String[] args) {
        Synchronized00 obj = new Synchronized00();
        obj.println();
    }

    public void println() {
        synchronized(this) {
            System.out.println("Hello world!");
        }
    }
}
```
对应的字节码文件内容如下：
```text
 0 aload_0
 1 dup
 2 astore_1
 3 monitorenter
 4 getstatic #5 <java/lang/System.out : Ljava/io/PrintStream;>
 7 ldc #6 <Hello world!>
 9 invokevirtual #7 <java/io/PrintStream.println : (Ljava/lang/String;)V>
12 aload_1
13 monitorexit
14 goto 22 (+8)
17 astore_2
18 aload_1
19 monitorexit
20 aload_2
21 athrow
22 return
```

从字节码文件的内容可以看到有一个`monitorenter`和两个`monitorexit`，synchronized重量级锁其实就是依赖这两个指令完成，之所以有两个`monitorexit`是因为在发生异常的时候
需要释放锁，synchronized加锁的目标其实都是对象，实际上应该是**加锁对象的monitor**，`Monitor`称为监视器，监视器的底层其实是依赖了操作系统的锁机制，Mutex Lock 。

在HotSpot虚拟机中`Monitor`监视器的对应实现就是`ObjectMonitor`，在`ObjectMonitor`有以下几个重要的属性：
```text
   ObjectMonitor() {
    _count        = 0;      // 记录数
    _recursions   = 0;      // 锁的重入次数
    _owner        = NULL;   // 指向持有ObjectMonitor对象的线程 
    _waiters      = NULL;   // 调用wait后，线程会被加入到_waiters
    _EntryList    = NULL ;  // 等待获取锁的线程，会被加入到该列表
}
```
![ObjectMonitor.png](ObjectMonitor.png)

关于加锁对象如何找到对应的`Monitor`，是由于`Monitor`在Synchronized重量级锁时对应的地址存放于加锁对象的对象头MarkWord当中，下文结合锁升级再详细说明。

整个synchronized加锁的过程可以结合ObjectMonitor的属性来分析：

1. 当多线程产生竞争访问synchronized修饰的方法或者代码块的时候，首先线程会进入到`_EntryList`的列表中，这时候产生竞争的所有线程都会被阻塞住。
   
2. 然后其中某个线程获取到锁之后，`ObjectMonitor`的_owner设置为当前获取锁的线程，同时该线程从`_EntryList`移除，`_count++`， 并且解除阻塞状态，进入到Running状态。
   
3. 当线程执行完同步方法或者代码块时，`ObjectMonitor`的_owner设置为null，_count--，如果线程在执行时调用了`wait`方法，_owner设置为null，_count--，该线程再回到`_EntryList`中，
等待下一次锁的抢夺。

## synchronized 的优化 - 锁升级

JDK1.5之前早期的时候，synchronized 是重量级的锁，效率比较低，因为synchronized重量级锁在加锁的时候需要将当前线程进行挂起，然后由用户态切换为内核态交由操作系统去实现真正的锁，这种切换的代价是很昂贵的，所以JDK1.6增加了锁升级，避免了在某些情况下去做用户态的切换，
提升了synchronized的效率。

锁升级的方向如下：

```
无锁 > 偏向锁 > 轻量级锁 > 重量级锁
```

锁升级的过程是和锁对象的MarkWord息息相关，对于加锁对象的对象头MarkWord见下图（基于32位操作系统）：

![Synchronized_MarkWord.png](Synchronized_MarkWord.png)

- 无锁状态：一个对象在创建的时候就处于无锁状态，锁标志位为01。
  
- 偏向锁：偏向锁适用于那种没有线程竞争锁对象，线程交替执行同步块的情况。
  
  当第一个线程获取锁的时候，首先会将当前锁对象的MarkWord中的是否偏向锁标记设为1，然后记录下当前线程的ID。当同一个线程再次去获取锁的时候，首先会判断偏向锁的线程ID是否和当前线程ID一致，
  若一致，则直接拿到锁。
  当第二个线程过来获取同一把锁的时候，也会去判断锁对象的MarkWord中记录的线程ID是否一致，不一致则检查当前持有偏向锁的线程是否存活，若第一个线程已经结束，则
  撤销偏向锁变成无锁状态，这时候第二个线程可以竞争申请为偏向锁（这时候偏向锁的线程是第二个线程）。若第一个线程仍在运行，并且对应的栈帧（因为有可能线程已经退出synchronized代码块）仍持有锁，则升级为轻量级锁。
  
- 轻量级锁：轻量级锁适用于线程竞争不是很激烈，并且线程持有锁的时间不需要很久。
  
  当线程申请轻量级锁的时候，首先会将锁对象的MarkWord复制一份放到当前线程的栈帧中的锁记录空间(Lock Record)，在栈帧中存储对应的锁信息，同时将栈帧中存储锁信息的指针地址利用CAS设置到锁对象的对象头中。
  这时候锁对象的MarkWord锁指针地址就是指向当前线程的栈帧锁信息的地址。
  如果同时有另外一个线程去获取同一把锁，则也会进行同样的操作，但是在设置锁对象MarkWord的锁指针的时候会发现CAS失败了（因为已经被其他线程设置成功了），为了避免真正挂起当前线程，
  则进行自旋CAS的操作。
  
  这里还有一个要注意的点是由于自旋操作其实是消耗CPU的，如果自旋的次数很多，就会浪费CPU资源，得不偿失，所以JVM就采用了**自适应自旋**以防止出现自旋次数过多，
  为什么要叫自适应，是由于轻量级锁的每一次自旋都是依据前面自旋的次数决定，但是当到达自旋的上限之后就会将轻量级锁升级为重量级锁。
  
- 重量级锁：一旦从轻量级锁升级为重量级锁，这时候JVM会将所有自旋的线程进行挂起，防止自旋消耗CPU资源，当持有锁的线程释放锁之后，将锁对象的MarkWord的锁标志修改为
  10，并将MarkWord中互斥锁的指针地址指向`Monitor`，最终会唤醒所有阻塞的线程去竞争重量级锁。
  


参考文章：
>[面试官：说一下Synchronized底层实现，锁升级的具体过程？](https://blog.csdn.net/zzti_erlie/article/details/103997713)
>[Java并发编程：Synchronized底层优化（偏向锁、轻量级锁）](https://www.cnblogs.com/paddix/p/5405678.html)