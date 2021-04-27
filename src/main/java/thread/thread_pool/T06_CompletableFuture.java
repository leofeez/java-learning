package thread.thread_pool;


import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * 同时从天猫，京东，亚马逊查询商品的价格并汇总展示
 *
 * @author leofee
 * @date 2021/4/24
 */
public class T06_CompletableFuture {

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
}
