package thread._synchronized;

import java.util.concurrent.TimeUnit;

/**
 * @author leofee
 * @date 2020/12/7
 */
public class Synchronized01 {

    private int count = 10;

    public static void main(String[] args) {

        Synchronized01 object =  new Synchronized01();

        MyThread A = new MyThread(object);
        MyThread B = new MyThread(object);
        A.start();
        B.start();

    }

    private static class MyThread extends Thread {

        Synchronized01 object;

        public MyThread(Synchronized01 object) {
            this.object = object;
        }

        @Override
        public void run() {
            super.run();
            object.add();
        }
    }

    public synchronized void add() {
        while (count > 0) {
            count--;
            System.out.println(count);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
