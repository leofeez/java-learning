package thread._wait_notify;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author leofee
 * @date 2021/1/12
 */
public class WaitNotify10 extends Thread {

    public WaitNotify10(Runnable target, String name) {
        super(target, name);
    }

    public static void main(String[] args) throws InterruptedException {
        List<WaitNotify10> threads = new ArrayList<>();

        MyStack stack = new MyStack();

        String producerName = "生产者";
        for (int i = 0; i < 1; i++) {
            Producer producer = new Producer(stack);
            threads.add(new WaitNotify10(producer::produce, producerName + (i + 1)));
        }

        String consumerName = "消费者";
        for (int i = 0; i < 2; i++) {
            Consumer consumer = new Consumer(stack);
            threads.add(new WaitNotify10(consumer::consume, consumerName + (i + 1)));
        }

        threads.forEach(WaitNotify10::start);

        for (WaitNotify10 thread : threads) {
            thread.join();
        }

    }

    /**
     * 生产者
     */
    static class Producer {
        private MyStack stack;

        public Producer(MyStack stack) {
            this.stack = stack;
        }

        public void produce() {
            stack.push();
        }
    }

    /**
     * 消费者
     */
    static class Consumer {
        private MyStack stack;

        public Consumer(MyStack stack) {
            this.stack = stack;
        }

        public void consume() {
            stack.pop();
        }
    }

    static class MyStack {

        private List<String> list = new ArrayList<>();

        synchronized void push() {
            if (list.size() == 1) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String element = UUID.randomUUID().toString();
            System.out.println(Thread.currentThread().getName() + " 生产了 " + element);
            list.add(element);
            notifyAll();
        }

        synchronized void pop() {
            if (list.size() == 0) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String element = list.remove(0);
            System.out.println(Thread.currentThread().getName() + " 消费了 " + element);
            notifyAll();
        }
    }
}
