package thread._volatile;

import java.util.concurrent.TimeUnit;

/**
 * @author leofee
 * @date 2020/12/2
 */
public class VolatileExample implements Runnable {

    /**
     * 每个线程都对该变量copy一份到线程工作空间中
     * 如果不加volatile则该属性在线程间不会实时同步，及其他线程不会实时拿到实际值
     */
    private /*volatile*/ boolean running = true;

    public static void main(String[] args) throws InterruptedException {
        VolatileExample t = new VolatileExample();
        new Thread(t).start();

        TimeUnit.SECONDS.sleep(1);

        t.running = false;
    }

    @Override
    public void run() {
        System.out.println("server started");
        while (running) {
            // 模拟服务器在运行
        }
        System.out.println("server ended");
    }
}
