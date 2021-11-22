package jvm.t05_gc;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author leofee
 * @date 2021/8/23
 */
public class T02_HelloGC {

    static final Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello GC 02");
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2,
                4, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(4),
                MyThreadFactory.INSTANCE, new ThreadPoolExecutor.AbortPolicy());

        CardInfo cardInfo = new CardInfo(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        for (int i = 0; i < 2; i++) {
            Runnable r;
            r = () -> {
                for (; ; ) {
                    BigDecimal amount = new BigDecimal(new Random().nextInt(100)).abs();
                    if (amount.compareTo(new BigDecimal(90)) > 0) {
                        cardInfo.payBigAmount(amount);
                    } else {
                        cardInfo.pay(amount);
                    }
                }
            };
            executor.execute(r);
        }

    }


    public static class MyThreadFactory implements ThreadFactory {

        static final MyThreadFactory INSTANCE = new MyThreadFactory();


        public static final AtomicInteger atomicInteger = new AtomicInteger();

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r, "payment_thread-" + atomicInteger.getAndIncrement());
            thread.setUncaughtExceptionHandler((t, e) -> {
                System.out.println(t.getName() + "发生了错误");
                System.err.println(e);
            });
            return thread;
        }
    }

    public static class CardInfo {
        String cardNo;
        BigDecimal amount = new BigDecimal(10000000);
        List<byte[]> bytes = new ArrayList<>();


        public CardInfo(String cardNo) {
            this.cardNo = cardNo;
        }

        public void pay(BigDecimal amount) {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "支付了" + amount);
                bytes.add(new byte[50]);
                TimeUnit.SECONDS.sleep(1);
                this.amount = this.amount.subtract(amount);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void payBigAmount(BigDecimal amount) {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "支付了" + amount);
                for (long i = 0; i < 100_0000L; i++) {
                    // hold on
                    bytes.add(new byte[1024]);
                }

                this.amount = this.amount.subtract(amount);

            } finally {
                lock.unlock();
            }


        }
    }

}
