package thread.a1b2c3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class T05_Condition extends Thread {

    static String[] letters = {"A", "B", "C", "D", "E", "G"};

    static String[] numbers = {"1", "2", "3", "4", "5", "6"};

    static ReentrantLock LOCK = new ReentrantLock();

    static Condition condition1 = LOCK.newCondition();

    static Condition condition2 = LOCK.newCondition();

    public T05_Condition(Runnable target) {
        super(target);
    }

    public static void main(String[] args) {

        new T05_Condition(T05_Condition::printLetter).start();
        new T05_Condition(T05_Condition::printNumber).start();
    }

    public static void printLetter() {
        LOCK.lock();
        try {
            for (String letter : letters) {

                System.out.print(letter);
                condition2.signal();
                condition1.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            condition2.signal();
            LOCK.unlock();
        }

    }

    public static void printNumber() {
        LOCK.lock();
        try {
            for (String number : numbers) {
                System.out.print(number);
                condition1.signal();
                condition2.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            condition1.signal();
            LOCK.unlock();
        }
    }
}
