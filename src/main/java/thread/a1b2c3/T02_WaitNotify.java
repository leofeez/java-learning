package thread.a1b2c3;

import java.util.concurrent.TimeUnit;

public class T02_WaitNotify extends Thread {

    static String[] letters = {"A", "B", "C", "D", "E", "G"};

    static String[] numbers = {"1", "2", "3", "4", "5", "6"};

    static T02_WaitNotify obj = new T02_WaitNotify();

    /**
     * 用于控制先打印字母
     */
    static boolean shouldPrintLetterFlag = true;

    public T02_WaitNotify(Runnable target) {
        super(target);
    }

    public T02_WaitNotify() {
        super();
    }

    public static void main(String[] args) throws InterruptedException {
        new T02_WaitNotify(() -> {
            obj.printNumber();
        }).start();

        TimeUnit.SECONDS.sleep(1);

        new T02_WaitNotify(() -> {
            obj.printLetter();
        }).start();


    }

    public synchronized void printLetter() {
        while (!shouldPrintLetterFlag) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        shouldPrintLetterFlag = false;
        for (String letter : letters) {
            System.out.print(letter);
            notifyAll();
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
        while (shouldPrintLetterFlag) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
