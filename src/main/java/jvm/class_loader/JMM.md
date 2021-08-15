#JMM

## 硬件层缓存一致性

- 总线锁：效率低
- intel cpu 使用 MESI: CPU每个cache line 标记四种状态，Modified，Exclusive，Shared，Invalid

> https://www.cnblogs.com/z00377750/p/9180644.html

现代CPU采用以上两种方式组合。

## Cache Line 缓存行
读取的时候以 Cache line 为基本单位，目前大多数为64 bytes。
伪共享：位于同一缓存行的两个不同数据，被两个不同CPU锁定，产生互相影响的伪共享问题。

## CPU指令乱序问题
CPU为了提高指令的执行效率，会在一条指令执行过程中（比如：去内存中读取数据，因为读取内存的速度，比读取高速缓存慢100倍）去分析当前指令后的
指令是否与当前指令无依赖关系，若无，则同时去执行另一条指令，即指令重排。

> https://www.cnblogs.com/liushaodong/p/4777308.html

合并写 WCBuffer：

## 如何保证不乱序

CPU级别的内存屏障

以下汇编指令：
- sfence: 在 sfence 指令前的写操作必须在sfence指令后的写操作前完成。
- lfence: 在lfence指令前的读操作必须在lfence指令后的读操作前完成。
- mfence: 在mfence指令前的读写操作必须在mfence指令后的读写操作前完成。



- volatile