package thread._wait_notify;

import java.util.concurrent.TimeUnit;

/**
 * @author leofee
 * @date 2020/12/30
 */
public class WaitNotify02 extends Thread {

    public WaitNotify02() {}

    public WaitNotify02(Runnable target, String name) {
        super(target, name);
    }

    /**
     * wait()被调用会立刻释放锁进入，线程进入阻塞状态
     * notify()方法被调用后并不是立刻释放锁，而是当前的同步方法或者代码块执行完成之后才会释放锁
     */
    public static void main(String[] args) throws InterruptedException {
        WaitNotify02 object = new WaitNotify02();
        new WaitNotify02(object::waitMethod, "A").start();

        TimeUnit.SECONDS.sleep(1);

        new WaitNotify02(object::notifyMethod, "B").start();
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
