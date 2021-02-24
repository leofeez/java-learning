package thread.atomic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Supplier;

public class Atomic02 extends Thread {

    static LongAdder longAdder = new LongAdder();

    static AtomicLong atomicLong = new AtomicLong(0L);

    static int count = 0;

    public Atomic02(Runnable target) {
        super(target);
    }


    public static void main(String[] args) throws InterruptedException {
        startAdd(Atomic02::increment, "synchronize", () -> count);
        startAdd(Atomic02::incrementByAtomicInteger, "atomic long", () -> atomicLong.get());
        startAdd(Atomic02::incrementByLongAdder, "long adder", () -> longAdder.longValue());
    }

    public static void startAdd(Runnable target, String type, Supplier<Object> result) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            threads.add(new Atomic02(target));
        }
        long startTime = System.currentTimeMillis();
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        long endTime = System.currentTimeMillis();
        System.out.println(type + ": " + result.get() + "消耗时间为：" + (endTime - startTime));
    }

    public static void incrementByLongAdder() {
        for (int i = 0; i < 100000; i++) {
            longAdder.increment();
        }
    }

    public static void incrementByAtomicInteger() {
        for (int i = 0; i < 100000; i++) {
            atomicLong.incrementAndGet();
        }
    }

    public static synchronized void increment() {
        for (int i = 0; i < 100000; i++) {
            synchronized (Atomic02.class) {
                count++;
            }
        }
    }
}
