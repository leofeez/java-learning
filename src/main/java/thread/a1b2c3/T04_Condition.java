package thread.a1b2c3;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class T04_Condition extends Thread {

    static String[] letters = {"A", "B", "C", "D", "E", "G"};

    static String[] numbers = {"1", "2", "3", "4", "5", "6"};

    static ReentrantLock LOCK = new ReentrantLock();

    static Condition condition = LOCK.newCondition();

    public T04_Condition(Runnable target) {
        super(target);
    }

    public static void main(String[] args) throws InterruptedException {
        new T04_Condition(T04_Condition::printNumber).start();
        TimeUnit.SECONDS.sleep(1);
        new T04_Condition(T04_Condition::printLetter).start();
    }

    public static void printLetter() {
        LOCK.lock();
        try {
            for (String letter : letters) {
                System.out.print(letter);
                condition.signalAll();
                condition.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            condition.signalAll();
            LOCK.unlock();
        }

    }

    public static void printNumber() {
        // TODO 这里也需要保证数字线程后执行
        LOCK.lock();
        try {
            for (String number : numbers) {
                System.out.print(number);
                condition.signalAll();
                condition.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            condition.signalAll();
            LOCK.unlock();
        }
    }
}
