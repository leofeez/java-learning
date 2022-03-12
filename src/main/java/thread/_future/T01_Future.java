package thread._future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class T01_Future {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<String> callable = () -> {
            TimeUnit.SECONDS.sleep(5);
            return "hello future";
        };

        // FutureTask 实现了Runnable 和 Callable
        FutureTask<String> future = new FutureTask<>(callable);

        new Thread(future).start();

        String s = future.get();
        System.out.println(s);
    }
}
