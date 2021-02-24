package thread.atomic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author leofee
 * @date 2020/12/3
 */
public class Atomic01 {

    private AtomicInteger count = new AtomicInteger(0);

    private int countNumber = 0;

    public static void main(String[] args) {
        Atomic01 example = new Atomic01();

        // 创建100个线程
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            threadList.add(new Thread(() -> {
                example.addByAtomicInteger();
                example.add();
            }));
        }

        // 执行线程
        threadList.forEach(Thread::start);

        // 因为主线程需要等待所有的子线程全部执行完，打印count值
        // 所以利用join()
        threadList.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 输出所有线程执行完之后的count值
        System.out.println(example.count);

        System.out.println(example.countNumber);
    }

    /**
     * AtomicInteger 底层利用了CAS，利用Unsafe类型操作内存，
     * 最底层利用汇编语言的lock compxchg能够锁CPU总线
     */
    public void addByAtomicInteger() {
        for (int i = 0; i < 1000; i++) {
            count.incrementAndGet();
        }
    }

    /**
     * 如果不加锁则会出现线程安全问题
     */
    public /*synchronized*/ void add() {
        for (int i = 0; i < 1000; i++) {
            countNumber++;
        }
    }
}
