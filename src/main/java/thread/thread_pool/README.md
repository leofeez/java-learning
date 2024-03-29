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
 * 等待最后一个submit的任务执行完毕，线程池就会关闭，而不是暴力的去关闭线程池。
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

submit 和 execute 方法的区别？
submit方法其实底层都是调用了execute核心方法，在调用execute方法前，会将task转换为RunnableFuture，然后再执行execute方法真正的执行线程任务。
```java
    public <T> Future<T> submit(Callable<T> task) {
        if (task == null) throw new NullPointerException();
        // 转换为 RunnableFuture
        RunnableFuture<T> ftask = newTaskFor(task);
        // 执行线程任务
        execute(ftask);
        return ftask;
    }
```
入参区别：
 - submit入参为Callable对象
 - execute入参为Runnable对象
出参区别：
 - execute方法不会返回线程执行的结果
 - submit方法返回的Future对象可以利用get方法获取线程执行后的结果

### ScheduledExecutorService

基于ExecutorService扩展了可对线程任务进行调度的线程池，当线程任务提交到线程池中后不一定是立即执行的，可以指定任务的延迟执行时间和任务的执行周期，主要有以下几个方法：

```java 
// 在给定的延迟时间后开始执行任务
public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit);

// 创建并执行一个周期性的任务，任务开始于给定的初始延迟时间，后续的任务
// 会按照给定的周期进行执行，即后续的第一个任务将在 initialDelay + period 时执行
public ScheduledFuture<?> scheduleAtFixedRate(Runnable command,
                                                  long initialDelay,
                                                  long period,
                                                  TimeUnit unit);
// 周期性执行任务，与上一个方法同不的是，后续的第一个任务在上一个任务执行完成后再延时执行
public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command,
                                                     long initialDelay,
                                                     long delay,
                                                     TimeUnit unit);
```

### Future和Callable

#### Future

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

#### FutureTask

同时支持 Ruunable/ Callable 和 Future 的功能，即既是一个Runnable/Callable又可以作为一个Future接收子线程的返回值。

```java
// 创建线程池
ExecutorService threadPool = Executors.newFixedThreadPool(2);
// FutureTask既可以作为一个task，也可以作为一个Future返回线程执行的结果
FutureTask<String> futureTask = new FutureTask<>(() -> {
    TimeUnit.SECONDS.sleep(1);
    return "Hello world";
});
// 提交FutureTask到线程池
threadPool.submit(futureTask);
// 获取子线程的返回值
System.out.println(futureTask.get());
// 关闭线程池
threadPool.shutdown();
```



#### CompletableFuture

从Future可以看出，Future异步执行的时候，主线程如果需要拿到子线程的返回值则需要利用`get()`方法进行等待阻塞，而`CompletableFuture`在原Future的基础上进行了改进，因为在Jdk1.8之后提供了函数式编程，CompletableFuture的很多方法都可以接收`Supplier`，`Consumer`等这类支持函数式编程的对象，还支持链式的处理。

不仅仅如此，还支持多个CompletableFuture的结果的合并处理。

简单用法如下，支持链式的处理：

```java

public static void main(String[] args) throws InterruptedException {

    CompletableFuture.supplyAsync(() -> {
        double v = new Random().nextDouble();
        System.out.println("生成的随机数为：" + v);
        return v;
    })
    // 对上一步结果进一步处理并返回结果
    .thenApplyAsync(value -> {
        value = value * 100;
        System.out.println("将随机数 * 100");
        return value;
    })
    // 对最终的结果进行最终的处理
    .thenAccept(value -> {
        System.out.println("打印结果为：" + value);
    });

    // 防止主线程提前结束
    Thread.sleep(1000);
}
```

高级用法：

> 现在有一个场景，提供一个服务，同时去天猫，京东，亚马逊查询一个商品的价格。

如果采用传统的串行方式，则需要分别调用三个接口去查询。

利用CompletableFuture则可以分别建立三个子任务同时去查询，如果需要等待最终的结果可以利用allOf()将三个子任务进行汇总，再利用join()等待所有任务完成。

```java
public static void main(String[] args) throws InterruptedException {

    long start, end;

    start = System.currentTimeMillis();
    CompletableFuture<Void> tmPrice = CompletableFuture
            .supplyAsync(() -> getFromTm())
            .thenAccept(price -> System.out.println("tm price:" + price));
    CompletableFuture<Void> jdPrice = CompletableFuture
            .supplyAsync(() -> getFromJd())
            .thenAccept(price -> System.out.println("jd price:" + price));
    CompletableFuture<Void> ymxPrice = CompletableFuture
            .supplyAsync(() -> getFromYmx())
            .thenAccept(price -> System.out.println("ymx price:" + price));

    CompletableFuture<Void> allOf = CompletableFuture.allOf(tmPrice, jdPrice, ymxPrice);

    // 等待所有子线程完成
    allOf.join();

    end = System.currentTimeMillis();

    System.out.println("总共耗时：" + (end - start));

}

/** 查询天猫价格*/
public static double getFromTm() {
    sleep();
    return new Random().nextDouble() * 100;
}
/** 查询京东价格*/
public static double getFromJd() {
    sleep();
    return new Random().nextDouble() * 100;
}
/** 查询亚马逊价格*/
public static double getFromYmx() {
    sleep();
    return new Random().nextDouble() * 100;
}
public static void sleep() {
    try {
        TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}
```



### ThreadPoolExecutor

在整个Executor框架中，Executor和ExecutorService定义了线程池应该有的行为（这也就是接口的职责所在），而ThreadPoolExecutor就是线程池的具体实现，在ThreadPoolExecutor中如下7个核心的参数：

- corePoolSize：核心线程数量

- maximumPoolSize：最大线程数量

- keepAliveTime：当线程池中线程数量超过corePoolSize时，多余的空闲的线程最大闲置时间

- timeUnit：keepAliveTime的时间单位

- workQueue：为BlockingQueue，任务队列，即已提交线程池但还未被执行的任务队列，队列大致可分为以下几种：
  - 直接提交的队列SynchronousQueue: 这种队列没有实际容量，每执行一个插入操作都要等待一个相应的删除操作，反之同理，所以在提交任务到线程池中
    提交的任务不会被真实的保存而是总将新的任务提交给线程处理，如果没有空闲的线程则尝试创建新的线程，如果线程数量已经达到maximumPoolSize则
    执行拒绝策略。
  - 有界队列ArrayBlockingQueue:在初始化的时候必须制定容量，当提交任务到线程池中，优先使用核心线程处理，没有空闲的核心线程则进入队列等待，如果队列
    已经满了，则创建新的非核心线程处理，如果总的线程数大于maximumPoolSize则执行拒绝策略。
  - 无界队列LinkedBlockingQueue:使用核心线程处理任务，如果没有空闲的核心线程则任务进入队列，所以当处理速度和创建速度相差很大，则该队列会保持快速
    增长，最后耗尽系统资源。
  - 优先任务队列PriorityBlockingQueue:可以控制任务执行的顺序，可以根据任务的优先级顺序执行。

- threadFactory：创建线程的工厂，建议自定义线程工厂，实现`ThreadFactory`，指定线程名称的生成规则，在出现问题可以追溯。

- rejectHandler：拒绝策略，在线程池中线程已经用完了，同时等待队列workQueue已经无法加入新的任务的时候会执行拒绝策略，在JDK中内置了以下四种：
  * Abort：抛异常，阻止系统正常工作。
  * Discard：扔掉，不抛异常
  * DiscardOldest：扔掉排队时间最久的
  * CallerRuns:调用者处理任务，比如谁提交了任务，谁去执行

  但大多数情况都需要自定义自己的拒绝策略，实现`RejectedExecutionHandler`接口：

  ```java
  public interface RejectedExecutionHandler {
      void rejectedExecution(Runnable r, ThreadPoolExecutor executor);
  }
  ```

ThreadPoolExcutor 最终是交由内部类 Worker 进行任务的执行，Worker.runWorker() 方法中提供了beforeExcute前置处理和afterExcute后置处理，还有terminate在线程池关闭时进行一些处理。

```
线程池大小的经验公式为：nThreads = Ncpu * Ucpu *（1 + W/C）
- Ncpu: cpu的核心数，Runtime.getRuntime().availableProcessors();
- Ucpu: 期望的Cpu使用率， 0 <= Ucpu <= 1
- W/C: 等待时间与计算时间的比例
```

### ForkJoinPool

- 分解汇总的任务
- 用很少的线程可以执行很多的任务（子任务）TPE做不到先执行子任务
- CPU密集型

采用分而治之的思想

### Executors

Executors是线程池的工厂类，通过Executors可以取得各种具有特定功能的线程池，Executors其实就是封装了ThreadPoolExecutor的实例化，主要有以下几个工厂方法：

- newFixedThreadPool(int nThreads)：返回一个固定线程数量的线程池，该线程池中的线程数量始终不变，当有新的任务提交时，如果有空闲的线程则立即执行，否则进入等待队列。

  > 适用于任务量比较平稳的情况。

  ```java
  public static ExecutorService newFixedThreadPool(int nThreads) {
  		// corePoolSize 和 maximumPoolSize 都设置为 nThreads
    	return new ThreadPoolExecutor(nThreads, nThreads,
                                    0L, TimeUnit.MILLISECONDS,
                                    new LinkedBlockingQueue<Runnable>());
  }
  ```

- newWorkStealingPool(int parallelism)：

  ```java
  public static ExecutorService newWorkStealingPool(int parallelism) {
    	return new ForkJoinPool(
      				 parallelism,
               ForkJoinPool.defaultForkJoinWorkerThreadFactory,
               null, true);
  }
  ```

- newSingleThreadExecutor()：返回一个只有一个线程的线程池，若有额外的任务提交到线程池则会进入等待队列。

  > 缺点是LinkedBlockingQueue 是一个无界的队列，任务量大会导致大量任务堆积，最终可能导致OOM。

  ```java
  public static ExecutorService newSingleThreadExecutor() {
     	// corePoolSize 和 maximumPoolSize 都为 1
      return new FinalizableDelegatedExecutorService
          (new ThreadPoolExecutor(1, 1,
                                  0L, TimeUnit.MILLISECONDS,
                                  new LinkedBlockingQueue<Runnable>()));
  }
  ```

- newCachedThreadPool(): 返回一个可以根据实际情况调整线程数量的线程池，该线程池的线程数量不确定，若有空闲的可复用的线程则会优先使用可复用的线程，若没有空闲的，又有新的任务提交到线程池则会创建新的线程处理任务，所有的线程的任务都执行完毕后将返回线程池进行复用。

  > 缺点是由于SynchronousQueue是手把手的机制，提交的任务不会真实的保存到队列中，而是将新的任务提交给线程处理，
  > 如果没有空闲线程则会立即创建新的线程，在任务量大的时候会持续不断的创建线程。
  >
  > 适用于任务量忽高忽低，保证任务来了能够及时处理。
  
  ```java
  public static ExecutorService newCachedThreadPool() {
  		return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                      60L, TimeUnit.SECONDS,
                                        new SynchronousQueue<Runnable>());
  }
  ```
  
  从以上几种线程池对象可以看出，主要区别就在于实例化ThreadPoolExecutor的参数不一样。

