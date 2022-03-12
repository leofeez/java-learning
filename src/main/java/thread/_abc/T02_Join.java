package thread._abc;

public class T02_Join {


    /**
     * 实现线程顺序执行
     */
    public static void main(String[] args) throws InterruptedException {
        method02();
    }
    /**
     * 利用 Condition
     */
    static void method02() {

        Thread t1 = new Thread(() -> {
            System.out.println("第一个线程");
        });

        Thread t2 = new Thread(() -> {
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("第二个线程");
        });

        Thread t3 = new Thread(() -> {
            try {
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("第三个线程");
        });

        t3.start();
        t2.start();
        t1.start();
    }
}
