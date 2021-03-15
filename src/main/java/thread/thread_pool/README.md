# 线程池

为什么要有线程池？

在没有线程池的情况下创建线程的方式如下：

```java
new Thread(() -> {
  // do something
}).start();
```

但是如果在实际项目中利用此方式，必然没有办法控制线程数的合理性，而大量的创建线程必然会引出以下两个问题：

- 创建和销毁线程对象是需要消耗CPU资源，线程对象的回收也需要依靠JVM的GC
- 创建大量的线程对象也会浪费内存空间，严重时可能会导致OOM



所以线程池就是为了能够实现线程的复用，在线程池中总会有一些线程处于活跃状态，当你需要线程对象的时候只需要从线程池中获取，当完成对应的工作之后，并不是直接将线程关闭，而是将该线程对象退回到线程池中等待别的工作去复用。



### Executor



### ExecutorService

### Future

### Callable

#### Callable 和 Runnable的区别



## 线程池对象

#### ThreadPoolExecutor

自定义的线程池，ThreadPoolExecutor的构造参数如下：

- corePoolSize：核心线程数量
- maximumPoolSize：最大线程数量
- keepAliveTime：最大闲置时间
- timeUnit：时间单位
- workQueue：为BlockingQueue，任务队列数量
- threadFactory：创建线程的工厂
- rejectHandler：拒绝策略，在JDK中有提供四种
  * Abort：抛异常
  * Discard：扔掉，不抛异常
  * DiscardOldest：扔掉排队时间最久的
  * CallerRuns:调用者处理任务，比如谁提交了任务，谁去执行

#### ForkJoinPool

- 分解汇总的任务
- 用很少的线程可以执行很多的任务（子任务）TPE做不到先执行子任务
- CPU密集型