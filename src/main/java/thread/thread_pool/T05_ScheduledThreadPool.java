package thread.thread_pool;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

/**
 * @author leofee
 */
public class T05_ScheduledThreadPool {

    public static void main(String[] args) throws InterruptedException {
        ScheduledThreadPoolExecutor threadPool = new ScheduledThreadPoolExecutor(4, Executors.defaultThreadFactory());
        threadPool.scheduleAtFixedRate(() -> {

        }, 2000, 1000, TimeUnit.MILLISECONDS);


        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " task 1 running ");
            }
        };

        // 延迟执行
        Timer timer = new Timer("leofee timer");
        timer.schedule(task, 1000);

        // 周期性的执行task
        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " task 2 running ");
            }
        };
        timer.scheduleAtFixedRate(task2, 5000, 1000);

        new CountDownLatch(1).await();
    }
}
