package thread.a1b2c3;

import java.util.concurrent.CountDownLatch;

public class T03_WaitNotify extends Thread {

    static String[] letters = {"A", "B", "C", "D", "E", "G"};

    static String[] numbers = {"1", "2", "3", "4", "5", "6"};

    static T03_WaitNotify obj = new T03_WaitNotify();

    static CountDownLatch latch = new CountDownLatch(1);

    public T03_WaitNotify(Runnable target) {
        super(target);
    }

    public T03_WaitNotify() {
        super();
    }

    public static void main(String[] args) {
        new T03_WaitNotify(() -> {
            // await()方法不会释放锁，所以必须在获取锁之前就要阻塞住
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            obj.printNumber();
        }).start();

        new T03_WaitNotify(() -> {
            obj.printLetter();
        }).start();
    }

    public synchronized void printLetter() {
        for (String letter : letters) {
            System.out.print(letter);
            notifyAll();
            // 在wait释放锁之前countDown唤醒另外一个线程
            latch.countDown();
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 因为数字打印完之后还是继续wait，
        // 所以这里需补充一次唤醒可以让数字线程正常结束
        notifyAll();
    }

    public synchronized void printNumber() {
        for (String number : numbers) {
            System.out.print(number);
            notifyAll();
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
