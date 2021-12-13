package jvm.t05_gc;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author leofee
 * @date 2021/8/23
 */
public class T03_HelloGC {


    private static class CardInfo {
        BigDecimal price = new BigDecimal(0.0);
        String name = "张三";
        int age = 5;
        Date birthdate = new Date();

        public void m() {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

//    private static ThreadPoolExecutor executor = getThreadPool();

    /**
     * 该线程池最大容量为{@code Integer.MAX_VALUE}，当任务量很大的时候，很容易导致OOM，因为会不断的产生FutureTask对象。
     */
    private static final ScheduledThreadPoolExecutor THREAD_POOL = new ScheduledThreadPoolExecutor(50,
            new ThreadPoolExecutor.DiscardOldestPolicy());

    public static void main(String[] args) throws Exception {

        for (; ; ) {
            modelFit();
            Thread.sleep(100);
        }
    }

    private static void modelFit() {
        List<CardInfo> taskList = getAllCardInfo();
        taskList.forEach(info -> {
            // do something
            //do sth with info
            THREAD_POOL.submit(info::m);
        });
    }

    private static List<CardInfo> getAllCardInfo() {
        List<CardInfo> taskList = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            CardInfo ci = new CardInfo();
            taskList.add(ci);
        }

        return taskList;
    }

    /**
     * 线程池大小的经验公式为：nThreads = Ncpu * Ucpu *（1 + W/C）
     *
     * @return 自定义线程池
     */
    public static ThreadPoolExecutor getThreadPool() {
        // CPU的核心数
        int processors = Runtime.getRuntime().availableProcessors();
        return new ThreadPoolExecutor(
                // 核心线程数
                2,
                // 最大线程数
                50,
                // 空闲存活时间
                60,
                // 时间单位
                TimeUnit.SECONDS,
                // 任务队列
                new ArrayBlockingQueue<>(50),
                // 创建线程的工厂（建议自定义）
                MyThreadFactory.INSTANCE,
                // 拒绝策略 JDK 提供了4种
                MyRejectedHandler.INSTANCE) {
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println("准备执行" + t.getName());
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                System.out.println("执行完成");
            }

            @Override
            protected void terminated() {
                System.out.println("线程池关闭");
            }
        };
    }

    public static class MyThreadFactory implements ThreadFactory {

        static final MyThreadFactory INSTANCE = new MyThreadFactory();


        public static final AtomicInteger atomicInteger = new AtomicInteger();

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r, "leofee_thread-" + atomicInteger.getAndIncrement());
            thread.setUncaughtExceptionHandler((t, e) -> {
                System.out.println(t.getName() + "发生了错误" + e);
            });
            return thread;
        }
    }

    public static class MyRejectedHandler implements RejectedExecutionHandler {

        static final MyRejectedHandler INSTANCE = new MyRejectedHandler();

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("丢弃了任务，保存日志" + r.toString());
        }
    }

}
