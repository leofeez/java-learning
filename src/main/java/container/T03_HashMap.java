package container;

import java.util.*;

/**
 * @author leofee
 * @date 2020/12/14
 */
public class T03_HashMap {

    public static int thread_count = 100;

    public static int count = 10000;

    public static UUID[] keys = new UUID[thread_count * count];

    public static UUID[] values = new UUID[thread_count * count];

    public static Map<UUID, UUID> container = new HashMap<>(thread_count * count);

    // 初始化元素
    static {
        for (int i = 0; i < thread_count * count; i++) {
            keys[i] = UUID.randomUUID();
            values[i] = UUID.randomUUID();
        }
    }

    public static void main(String[] args) throws Exception{

        List<Thread> threadList = new ArrayList<>();

        for (int i = 0; i < thread_count; i++) {
            threadList.add(new MyThread(i));
        }

        long start = System.currentTimeMillis();

        threadList.forEach(Thread::start);

        for (Thread thread : threadList) {
            thread.join();
        }

        long end = System.currentTimeMillis();

        System.out.println(container.size());

        System.out.println("总共耗时：" + (end - start));
    }

    static class MyThread extends Thread {

        private final int currentThread;

        public MyThread(int currentThread) {
            this.currentThread = currentThread;
        }

        @Override
        public void run() {
            super.run();
            for (int i = currentThread * count; i < (currentThread + 1) * count; i++) {
                container.put(keys[i], values[i]);
            }
        }
    }
}
