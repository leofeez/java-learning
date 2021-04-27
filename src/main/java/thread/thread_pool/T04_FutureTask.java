package thread.thread_pool;

import java.util.concurrent.*;

/**
 * @author leofee
 * @date 2021/4/24
 */
public class T04_FutureTask {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
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

        threadPool.shutdown();
    }
}
