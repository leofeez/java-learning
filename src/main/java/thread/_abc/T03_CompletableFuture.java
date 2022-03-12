package thread._abc;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class T03_CompletableFuture {
    static Thread t1;
    static Thread t2;
    static Thread t3;


    /**
     * 实现线程顺序执行
     */
    public static void main(String[] args) throws InterruptedException {
        method03();
    }

    /**
     * 利用 {@link CompletableFuture#runAsync(Runnable)}
     */
    static void method03() {
        Thread t1 = new Thread(() -> {
            System.out.println("第一个线程");
        });

        Thread t2 = new Thread(() -> {
            System.out.println("第二个线程");
        });

        Thread t3 = new Thread(() -> {
            System.out.println("第三个线程");
        });
        CompletableFuture.runAsync(t1::start).thenRun(t2::start).thenRun(t3::start);

    }
}
