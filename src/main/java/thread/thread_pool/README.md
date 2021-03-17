# 线程池

为什么要有线程池？

首先可以看一下在没有线程池的情况下创建线程的方式如下：

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

为了能够对线程进行有效的控制，在JDK1.5之后提供了Executor框架，该框架的目的就是为了将线程的创建、运行进行集中的管理，从而避免了线程的滥用。

Executor的定义如下：

```java
public interface Executor {

    /**
     * Executes the given command at some time in the future.  The command
     * may execute in a new thread, in a pooled thread, or in the calling
     * thread, at the discretion of the {@code Executor} implementation.
     *
     * @param command the runnable task
     * @throws RejectedExecutionException if this task cannot be
     * accepted for execution
     * @throws NullPointerException if command is null
     */
    void execute(Runnable command);
}
```

Executor是整个框架的最顶层接口，execute(Runnable cmd)的实现还是交由具体的实现类去处理，比如可以直接new Thread(command).start()，或者以线程池的方式创建并执行command。

整个框架的核心成员如下：

![image-20210317213051149](Executor.png)

### ExecutorService

ExecutorService接口继承于Executor，提供了更多的扩展接口，完善了整个线程池的生命周期，

```java
/**
 * 关闭当前线程池，不再接受新的执行任务，
 * 等待最后一个submit的任务执行完毕，线程池就会关闭。
 */
void shutdown();

/**
 * 判断当前线程池是否已经关闭
 */
boolean isShutdown();

/**
 * 提交要执行的返回值任务，并返回表示任务的未决结果的Future。
 * Future的get方法将在成功完成后返回任务的结果。
 */
<T> Future<T> submit(Callable<T> task);
```

### Future和Callable

从上面的ExecutorService看到submit方法接收一个Callable任务对象，返回了一个线程执行结果Future对象，在往常的Thread线程的执行任务是实现Runnable接口，Runnable接口的run()方法没有返回值，而Future对象就是在线程池中的线程任务完成后可以通过get方法拿到线程完成后的返回值。

```java
// 创建线程池
ExecutorService executorService = Executors.newFixedThreadPool(2);
// submit线程执行任务
Future<String> future = executorService.submit(() -> {
  // 当线程池中的执行任务完成后的返回值
  return "I'm completed";
});
String result = future.get();
System.out.println("拿到任务的返回值为：" + result);
executorService.shutdown();
```

Future的get方法会等待线程池的执行任务完毕并返回，如果执行任务还未完成就调用get方法会阻塞当前线程。

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