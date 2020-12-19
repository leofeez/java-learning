package thread.atomic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author leofee
 * @date 2020/12/3
 */
public class AtomicExample {

    private AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) {
        AtomicExample example = new AtomicExample();

        // 创建10个线程
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            threadList.add(new Thread(example::add));
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
    }

    public void add() {
        for (int i = 0; i < 1000; i++) {
            count.incrementAndGet();
        }
    }
}
