package thread.a1b2c3;

public class T02_WaitNotify extends Thread {

    static String[] letters = {"A", "B", "C", "D", "E", "G"};

    static String[] numbers = {"1", "2", "3", "4", "5", "6"};

    static T02_WaitNotify obj = new T02_WaitNotify();

    static volatile boolean flag ;

    public T02_WaitNotify(Runnable target) {
        super(target);
    }

    public T02_WaitNotify() {
        super();
    }

    public static void main(String[] args) {
        new T02_WaitNotify(() -> {
            obj.printNumber();
        }).start();

        new T02_WaitNotify(() -> {
            obj.printLetter();
        }).start();


    }

    public synchronized void printLetter() {
        for (String letter : letters) {
            System.out.print(letter);
            flag = true;
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
        // 保证打印数字线程后执行
        while (!flag) {
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
