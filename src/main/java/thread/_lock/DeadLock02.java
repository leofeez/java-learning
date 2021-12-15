package thread._lock;

import java.util.concurrent.TimeUnit;

/**
 * @author leofee
 * @date 2021/10/29
 */
public class DeadLock02 {

    static final Object MONITOR_1 = new Object();
    static final Object MONITOR_2 = new Object();




    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                print();
            }).start();
        }
    }

    public static void print() {
        synchronized (MONITOR_1) {
            for(;;) {}
        }
    }

}
