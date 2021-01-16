package thread.basic;

import java.util.concurrent.TimeUnit;

/**
 * @author leofee
 * @date 2021/1/16
 */
public class Join02 extends Thread {
    public Join02() {
    }

    public Join02(Runnable target, String name) {
        super(target, name);
    }

    public static void main(String[] args) {
        Join02 obj = new Join02();

        Join02 A = new Join02(obj::joinOtherThread, "A");
        A.start();

        Join02 B = new Join02(obj::print, "B");
        B.start();
    }

    public synchronized void print() {
        System.out.println(Thread.currentThread().getName() + " 正在执行......");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " 执行结束......");
    }

    public synchronized void joinOtherThread() {
        System.out.println(Thread.currentThread().getName() + " 正在执行......");
        Join02 C = new Join02(() -> {
            System.out.println(Thread.currentThread().getName() + " 正在执行......");
            for (int i = 0; i < 5; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(i);
            }
            System.out.println(Thread.currentThread().getName() + " 执行结束......");
        }, "C");
        C.start();
        try {
            C.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " 执行结束......");
    }
}
