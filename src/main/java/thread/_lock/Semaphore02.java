package thread._lock;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 利用 {@link Semaphore} 实现餐厅就餐等待场景
 */
public class Semaphore02 extends Thread {


    public Semaphore02(Runnable target, String name) {
        super(target, name);
    }

    public static void main(String[] args) {

        // 餐厅有3张桌子
        Semaphore restaurant = new Semaphore(3);

        Thread[] customers = new Thread[10];
        for (int i = 0; i < 10; i++) {
            customers[i] = new Semaphore02(() -> {
                if (restaurant.availablePermits() == 0) {
                    System.out.println(Thread.currentThread().getName() + "说：没有位置了，我等一下吧");
                }
                try {
                    restaurant.acquire();

                    System.out.println(Thread.currentThread().getName() + "说：终于等到位置了。");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "开始就餐......");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "就餐结束......");
                restaurant.release();
            }, "顾客" + i);
        }

        for (Thread customer : customers) {
            customer.start();
        }
    }
}
