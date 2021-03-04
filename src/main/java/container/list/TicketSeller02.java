package container.list;

import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class TicketSeller02 extends Thread {

    public TicketSeller02(Runnable target, String name) {
        super(target, name);
    }

    static Vector<String> tickets = new Vector<>();

    static {
        for (int i = 0; i < 10000; i++) {
            tickets.add(UUID.randomUUID().toString());
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new TicketSeller02(() -> {
                while (tickets.size() > 0) {

                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(Thread.currentThread().getName() + "卖出了票，票号为 " + tickets.remove(0));
                }
            }, "销售员" + i).start();
        }
    }
}
