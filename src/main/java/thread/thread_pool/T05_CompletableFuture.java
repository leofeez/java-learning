package thread.thread_pool;


import java.util.Random;
import java.util.concurrent.CompletableFuture;

/**
 *
 *
 * @author leofee
 * @date 2021/4/24
 */
public class T05_CompletableFuture {

    public static void main(String[] args) throws InterruptedException {

        CompletableFuture.supplyAsync(() -> {
            double v = new Random().nextDouble();
            System.out.println("生成的随机数为：" + v);
            return v;
        })
        // 对上一步结果进一步处理并返回结果
        .thenApplyAsync(value -> {
            value = value * 100;
            System.out.println("将随机数 * 100");
            return value;
        })
        // 对最终的结果进行最终的处理
        .thenAccept(value -> {
            System.out.println("打印结果为：" + value);
        });

        // 防止主线程提前结束
        Thread.sleep(1000);
    }
}
