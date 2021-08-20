#JMM

## 硬件层缓存一致性

- 总线锁：效率低
- intel cpu 使用 MESI: CPU每个cache line 标记四种状态，Modified，Exclusive，Shared，Invalid

> https://www.cnblogs.com/z00377750/p/9180644.html

现代CPU采用以上两种方式组合。当某些数据比较大，在CPU高速缓存装不下的时候还是需要锁总线。

## Cache Line 缓存行
读取的时候以 Cache line 为基本单位，目前大多数为64 bytes。
伪共享：位于同一缓存行的两个不同数据，被两个不同CPU锁定，产生互相影响的伪共享问题。

## CPU指令乱序问题
CPU为了提高指令的执行效率，会在一条指令执行过程中（比如：去内存中读取数据，因为读取内存的速度，比读取高速缓存慢100倍）去分析当前指令后的
指令是否与当前指令无依赖关系，若无，则同时去执行另一条指令，即指令重排。

> https://www.cnblogs.com/liushaodong/p/4777308.html

合并写 WCBuffer：

## 如何保证不乱序

CPU硬件级别的内存屏障，每种CPU的内存屏障的指令都是不一样的。

以下汇编指令（基于Intel CPU）：
- sfence: store fence，在 sfence 指令前的写操作必须在sfence指令后的写操作前完成。
- lfence: load fence，在lfence指令前的读操作必须在lfence指令后的读操作前完成。
- mfence: mix fence，在mfence指令前的读写操作必须在mfence指令后的读写操作前完成。

原子指令：如x86上的 lock... 指令是一个Full Barrier，执行时会锁住内存子系统来确保执行顺序，甚至会跨多个CPU。
软件层面的通常是利用了内存屏障或者原子指令来实现变量的可见性和保持程序的执行顺序。

JVM级别的屏障规范（JSR133）
- LoadLoad 屏障：即Load, LoadLoad屏障, Load2，在Load2及后续的读取操作的数据被访问前，保证Load1要读取的数据被读取完毕。

- StoreStore屏障：即 Store, StoreLoad屏障, Store2，在Store2及后续的写入操作执行前，保证Store1的写入操作对其他处理器可见。

- LoadStore屏障：即 Load, LoadStore屏障, Store2，在Store2及后续的写入操作被刷出前，保证Load1要读取的数据被读取完毕。

- StoreLoad屏障：即Store, StoreLoad屏障, Load，在Load即后续的所有读操作执行前，保证Store1的写入对所有处理可见。

> https://www.jianshu.com/p/64240319ed60

## Volatile 实现细节

1. 字节码层面：被volatile修饰的编译为字节码的时候都会加上ACC_VOLATILE标记

2. JVM层面：volatile内存的读取前后都加屏障
```
    ------StoreStoreBarrier------               ------LoadLoadBarrier ------
            volatile写操作                             volatile读操作
    ------StoreLoadBarrier ------               ------LoadStoreBarrier------
```
3. OS、硬件层
利用 hsdis-hotspot Dis Assembler
> https://www.cnblogs.com/xrq730/p/7048693.html

## synchronized 实现细节

1. 字节码层面：
    同步方法：ACC_SYNCHRONIZED   
    同步代码块：monitorenter monitorexit

2. JVM层面：利用C++操作系统的同步机制。

3. OS、硬件层：
   x86：lock comchg 指令
> https://blog.csdn.net/21aspnet/article/details/88571740

