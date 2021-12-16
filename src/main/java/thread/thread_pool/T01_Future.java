package thread.thread_pool;

import java.util.concurrent.*;

/**
 * @author leofee
 * @date 2021/3/17
 */
public class T01_Future {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<String> future = executorService.submit(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("sleep " + (i + 1) + "s");
            }
            return "I'm completed";
        });
        String result = future.get();
        System.out.println("拿到任务的返回值为：" + result);
        executorService.shutdown();
        System.out.println("线程池是否关闭" + executorService.isShutdown());
    }
}
