package thread.basic;

import java.util.concurrent.TimeUnit;

/**
 * @author leofee
 */
public class MyShutDownHook {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println( Thread.currentThread().getName() + " shut down hook run");
        }));

        System.out.println("main thread end");
    }
}
