package container.list;

import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class TicketSeller02 extends Thread {

    public TicketSeller02(Runnable target, String name) {
        super(target, name);
    }

    static final Vector<String> tickets = new Vector<>();

    static {
        for (int i = 0; i < 100; i++) {
            tickets.add(UUID.randomUUID().toString());
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new TicketSeller02(() -> {

//                sale();

                sale02();

            }, "销售员" + i).start();
        }
    }

    public static void sale() {
        // 虽然Vector的方法都是synchronized
        // 但下方的.size()方法和remove()操作并不是原子性，所以还是会出现线程安全问题
        // 还是要用同步锁
        while (tickets.size() > 0) {

            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + "卖出了票，票号为 " + tickets.remove(0) + "还剩 " + tickets.size());
        }
    }

    /**
     * 利用 synchronized 保证原子性操作
     */
    public static void sale02() {
        while (true) {
            synchronized (tickets) {
                if (tickets.size() == 0) {
                    break;
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + "卖出了票，票号为 " + tickets.remove(0) + "还剩 " + tickets.size());
            }
        }
    }
}
