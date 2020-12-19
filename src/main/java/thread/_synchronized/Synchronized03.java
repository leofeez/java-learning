package thread._synchronized;

import java.util.concurrent.TimeUnit;

/**
 * 两个线程持有的锁不一样，一个是Class锁，一个是对象锁，并不会同步执行
 *
 * @author leofee
 * @date 2020/12/19
 */
public class Synchronized03 {

    public static void main(String[] args) throws Exception {

        Synchronized03 object = new Synchronized03();

        // Class锁线程
        new Thread(Synchronized03::printA, "A").start();

        // 对象锁线程
        new Thread(object::printB, "B").start();
    }

    public synchronized static void printA() {
        System.out.println(Thread.currentThread().getName() + "执行开始");

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + "执行结束");
    }

    public synchronized void printB() {
        System.out.println(Thread.currentThread().getName() + "执行开始");

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + "执行结束");
    }
}
