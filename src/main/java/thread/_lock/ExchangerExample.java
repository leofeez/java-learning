package thread._lock;

import java.util.concurrent.Exchanger;

public class ExchangerExample extends Thread {

    public ExchangerExample(Runnable target, String name) {
        super(target, name);
    }

    public static void main(String[] args) {

        Exchanger<String> exchanger = new Exchanger<>();
        new ExchangerExample(() -> {
            System.out.println(Thread.currentThread().getName() + "开始运行");

            String value = "T1";
            String exchange = "";

            try {
                exchange = exchanger.exchange(value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + "运行结束, 交换后的值为" + exchange);
        }, "T1").start();

        new ExchangerExample(() -> {
            System.out.println(Thread.currentThread().getName() + "开始运行");

            String value = "T2";
            String exchange = "";

            try {
                exchange = exchanger.exchange(value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + "运行结束, 交换后的值为" + exchange);
        }, "T2").start();
    }
}
