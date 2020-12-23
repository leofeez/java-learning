package thread._synchronized;

import java.util.concurrent.TimeUnit;

/**
 * @author leofee
 */
public class Synchronized04 {

    private final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {

        test1();

//        test2();

//        test3();

//        test4();

//        test5();
    }

    /**
     * synchronized 用在方法上则锁住当前调用的对象
     */
    public static void test1() throws InterruptedException {
        Synchronized04 A = new Synchronized04();
        Synchronized04 B = new Synchronized04();

        // 同一个对象调用同步方法
        new Thread(A::add, "A1").start();
        new Thread(A::add, "B1").start();

        TimeUnit.SECONDS.sleep(2);

        System.out.println("------synchronized method 2--------");

        // 不同对象调用不同的同步方法
        new Thread(A::add, "A2").start();
        new Thread(A::add2, "B2").start();

        TimeUnit.SECONDS.sleep(2);

        System.out.println("------synchronized method 3--------");

        // 不同对象调用同步方法
        new Thread(A::add, "A3").start();
        new Thread(B::add, "B3").start();

    }

    /**
     * synchronized(this)
     */
    public static void test2() throws InterruptedException {
        Synchronized04 A = new Synchronized04();
        Synchronized04 B = new Synchronized04();

        // 同一个对象调用同步方法
        new Thread(A::add2, "A1").start();
        new Thread(A::add2, "B1").start();

        TimeUnit.SECONDS.sleep(2);

        System.out.println("------synchronized this 2--------");

        // 不同对象调用同步方法
        new Thread(A::add2, "A2").start();
        new Thread(B::add2, "B2").start();

    }

    /**
     * synchronized(Class)会对当前Class的所有实例加锁
     */
    public static void test3() throws InterruptedException {
        Synchronized04 A = new Synchronized04();
        Synchronized04 B = new Synchronized04();

        // 同一个对象调用同步方法
        new Thread(A::add3, "A1").start();
        new Thread(A::add3, "B1").start();

        TimeUnit.SECONDS.sleep(2);

        System.out.println("------synchronized class 2--------");

        // 不同对象调用同步方法
        new Thread(A::add3, "A2").start();
        new Thread(B::add3, "B2").start();

    }

    /**
     * synchronized 用在 static 方法上会对当前Class的所有实例加锁
     */
    public static void test4() throws InterruptedException {
        Synchronized04 A = new Synchronized04();
        Synchronized04 B = new Synchronized04();

        // 同一个对象调用同步方法
        new Thread(() -> A.add4(), "A1").start();
        new Thread(() -> A.add4(), "B1").start();

        TimeUnit.SECONDS.sleep(2);

        System.out.println("------synchronized static 2--------");

        // 不同对象调用同步方法
        new Thread(() -> A.add4(), "A2").start();
        new Thread(() -> B.add4(), "B2").start();

    }

    /**
     * synchronized 用在 LOCK 成员变量上
     */
    public static void test5() throws InterruptedException {
        Synchronized04 A = new Synchronized04();
        Synchronized04 B = new Synchronized04();

        // 同一个对象调用
        new Thread(A::add5, "A1").start();
        new Thread(A::add5, "B1").start();

        TimeUnit.SECONDS.sleep(2);

        System.out.println("------synchronized LOCK 2--------");

        new Thread(A::add, "A1").start();
        new Thread(A::add5, "B1").start();

        TimeUnit.SECONDS.sleep(2);
        System.out.println("------synchronized LOCK 3--------");

        // 不同对象调用同步方法
        new Thread(A::add5, "A2").start();
        new Thread(B::add5, "B2").start();

        TimeUnit.SECONDS.sleep(2);



    }

    public synchronized void add() {
        System.out.println(Thread.currentThread().getName());

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void add2() {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName());

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void add3() {
        synchronized (Synchronized04.class) {
            System.out.println(Thread.currentThread().getName());

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized void add4() {
        System.out.println(Thread.currentThread().getName());
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void add5() {
        synchronized (LOCK) {
            System.out.println(Thread.currentThread().getName());

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
