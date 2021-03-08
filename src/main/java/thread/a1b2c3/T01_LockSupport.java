package thread.a1b2c3;

import java.util.concurrent.locks.LockSupport;

public class T01_LockSupport extends Thread {

    static String[] letters = {"A","B","C","D","E","G"};

    static String[] numbers = {"1","2","3","4","5","6"};

    static Thread t1;

    static Thread t2;

    public T01_LockSupport(Runnable target) {
        super(target);
    }

    public static void main(String[] args) {

        t1 = new T01_LockSupport(() -> {
            for (String letter : letters) {
                System.out.print(letter);
                LockSupport.unpark(t2);
                LockSupport.park();
            }
        });


        t2 = new T01_LockSupport(() -> {
            for (String number : numbers) {
                LockSupport.park();
                System.out.print(number);
                LockSupport.unpark(t1);
            }
        });

        t1.start();

        t2.start();
    }
}
