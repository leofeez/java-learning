package thread.thread_pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author leofee
 * @date 2021/3/17
 */
public class T02_Future {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Future<?> future = executorService.submit(() -> {
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

        Thread.sleep(3000);

        while(future.isDone()) {
        }

        future.cancel(false);

        System.out.println(future.isCancelled());

        executorService.shutdown();
    }
}
