package thread._synchronized;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * 在同步代码块基于指定对象作为锁对象（对象监视器）的时候，一定要保证为final类型
 *
 * @author leofee
 * @date 2020/12/23
 */
public class Synchronized05 {

    private /*final*/ Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
        Synchronized05 obj = new Synchronized05();
        Thread A = new Thread(obj::print, "A");
        Thread B = new Thread(obj::print2, "B");

        A.start();
        // 如果不加该行代码，两个线程还是会同步的去执行，因为这时候两个线程进入同步代码块
        // 前已经产生了互斥的行为，或者说有一个线程必定会被阻塞
        TimeUnit.SECONDS.sleep(5);
        B.start();
    }

    public void print() {
        synchronized (LOCK) {
            LongAdder adder = new LongAdder();
            System.out.println(Thread.currentThread().getName() + "开始执行同步代码块1");
            while (adder.longValue() < 10) {
                if (adder.longValue() == 3L) {
                    LOCK = new Object();
                }
                System.out.println(Thread.currentThread().getName() + adder.longValue());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                adder.increment();
            }
        }
    }

    public void print2() {
        synchronized (LOCK) {
            System.out.println(Thread.currentThread().getName() + "开始执行同步代码块2");
            LongAdder adder = new LongAdder();
            while (adder.longValue() < 10) {
                System.out.println(Thread.currentThread().getName() + adder.longValue());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                adder.increment();
            }
        }
    }
}
