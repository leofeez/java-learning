package thread._synchronized;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * 在同步代码块基于指定对象作为锁对象的时候，一定要保证为final类型
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
