package container.list;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TicketSeller01 extends Thread {

    public TicketSeller01(Runnable target, String name) {
        super(target, name);
    }

    static List<String> tickets = new ArrayList<>();

    static {
        for (int i = 0; i < 10000; i++) {
            tickets.add(UUID.randomUUID().toString());
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new TicketSeller01(() -> {
                while (tickets.size() > 0) {
                    System.out.println(Thread.currentThread().getName() + "卖出了票，票号为 " + tickets.remove(0));
                }
            }, "销售员" + i).start();
        }
    }
}
