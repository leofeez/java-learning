package thread._wait_notify;

import java.util.concurrent.TimeUnit;

/**
 * @author leofee
 * @date 2020/12/30
 */
public class WaitNotify02 {

    public static void main(String[] args) throws InterruptedException {
        WaitNotify02 object = new WaitNotify02();
        new Thread(() -> {
            object.waitMethod();
        }).start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            object.notifyMethod();
        }).start();
    }

    public synchronized void waitMethod() {
        System.out.println("准备 wait......");
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("结束 wait......");
    }

    public synchronized void notifyMethod() {
        System.out.println("准备 notify......");
        notify();
        System.out.println("结束 notify......");
    }
}
