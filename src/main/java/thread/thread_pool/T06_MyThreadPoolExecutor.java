package thread.thread_pool;

import java.io.IOException;
import java.util.concurrent.*;

/**
 * @author leofee
 * @date 2021/3/15
 */
public class T06_MyThreadPoolExecutor {


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ThreadPoolExecutor threadPool = getThreadPool();

        Future<String> future = threadPool.submit(() -> {
            Thread.sleep(1000);
            return "hello my thread pool executor";
        });

        System.out.println(future.get());

        threadPool.shutdown();

        // 测试拒绝策略-阻塞任务
        Callable<Integer> callable = () -> {
            try {
                return System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return 0;
        };

        // 新建8个阻塞的任务丢到线程池
        for (int i = 0; i < 8; i++) {
            threadPool.submit(callable);
        }
        // 因为前面8个都在阻塞，所以第9个任务进去之后就会报错
        threadPool.submit(callable);
    }

    public static ThreadPoolExecutor getThreadPool() {
        return new ThreadPoolExecutor(
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
                // 拒绝策略 JDK 提供了4种
                new ThreadPoolExecutor.AbortPolicy());
    }
}
