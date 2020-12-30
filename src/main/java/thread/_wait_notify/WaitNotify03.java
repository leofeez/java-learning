package thread._wait_notify;

import java.util.concurrent.TimeUnit;

/**
 * @author leofee
 * @date 2020/12/30
 */
public class WaitNotify03 {

    private int count = 0;

    public static void main(String[] args) {
        WaitNotify03 object = new WaitNotify03();

        new Thread(() -> {
            synchronized (object) {
                if (object.count < 5) {
                    System.out.println("count 当前小于 5 ，开始wait......");
                    try {
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                System.out.println("count 已经等于 5 了，结束wait......");
            }
        }).start();

        new Thread(() -> {
            synchronized (object) {
                while (true) {
                    object.count++;
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(object.count);
                    if (object.count == 5) {
                        System.out.println("count 已经等于 5 了，开始notify......");
                        object.notify();
                        break;
                    }
                }

            }
        }).start();


    }
}
