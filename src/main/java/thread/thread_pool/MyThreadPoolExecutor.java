package thread.thread_pool;

import java.util.concurrent.*;

/**
 * @author leofee
 * @date 2021/3/15
 */
public class MyThreadPoolExecutor {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                // 核心线程数
                2,
                // 最大线程数
                4,
                // 空闲存活时间
                60,
                // 时间单位
                TimeUnit.SECONDS,
                // 任务队列
                new ArrayBlockingQueue<>(4),
                // 创建线程的工厂（建议自定义）
                Executors.defaultThreadFactory(),
                // 拒绝策略 JDK 提供了4中
                new ThreadPoolExecutor.AbortPolicy());
        Future<String> future = threadPoolExecutor.submit(() -> {
            Thread.sleep(1000);
            return "hello my thread pool executor";
        });

        System.out.println(future.get());

        threadPoolExecutor.shutdown();
    }
}
