package thread.thread_pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author leofee
 * @date 2021/3/18
 */
public class T03_FixedThreadPool {

    public static void main(String[] args) {
        // 创建一个固定线程数量为5的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        // 一共提交10个任务到线程池中
        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                System.out.printf("threadId: %s, time: %s%n", Thread.currentThread().getName(), System.currentTimeMillis());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        // 从输出结果可以看出，第一波的5个任务的线程名称和第二波的5个任务线程名称是一样的
        // 并且两波任务之间相差了约一秒钟
        executorService.shutdown();
    }
}
