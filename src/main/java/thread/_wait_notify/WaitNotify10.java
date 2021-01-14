package thread._wait_notify;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author leofee
 * @date 2021/1/12
 */
public class WaitNotify10 extends Thread {

    private List<String> list = new ArrayList<>();

    public WaitNotify10() {
    }

    public WaitNotify10(Runnable target, String name) {
        super(target, name);
    }

    /**
     * 在多消费者的情况下，wait()的方式应该使用
     * <pre>
     *     while (waitCondition) {
     *         wait();
     *     }
     *     // 其他业务代码
     * </pre>
     * 否则如果将上述的 while 替换成 if 则当线程被唤醒时，
     * 就直接执行业务代码而会跳过waitCondition的条件判断
     */
    public static void main(String[] args) throws InterruptedException {
        List<WaitNotify10> threads = new ArrayList<>();

        WaitNotify10 obj = new WaitNotify10();

        String producerName = "生产者";
        for (int i = 0; i < 1; i++) {
            threads.add(new WaitNotify10(() -> {
                while (true) {
                    obj.produce();
                }
            }, producerName + (i + 1)));
        }

        String consumerName = "消费者";
        for (int i = 0; i < 2; i++) {
            threads.add(new WaitNotify10(() -> {
                while (true) {
                    obj.consume();
                }
            }, consumerName + (i + 1)));
        }

        threads.forEach(WaitNotify10::start);

        for (WaitNotify10 thread : threads) {
            thread.join();
        }

    }

    synchronized void produce() {
//        if (list.size() == 1) {
        while (list.size() == 1) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String element = UUID.randomUUID().toString();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " 生产了 " + element);
        list.add(element);
        notifyAll();
    }

    synchronized void consume() {
//        if (list.size() == 0) {
        while (list.size() == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String element = list.remove(0);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " 消费了 " + element);
        notifyAll();
    }
}
