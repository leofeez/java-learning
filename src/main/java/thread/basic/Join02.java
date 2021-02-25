package thread.basic;

/**
 * join()方法内部使用了wait()，而wait()方法是会释放锁，这里有点绕的是
 * join()方法释放的是谁的锁，从下面的测试代码可以看出，thread.join()
 * 释放的是thread对象锁，因为方法内部使用了this.wait(),所以哪个对象调用
 * 了join()方法，则对应的对象锁就被当前线程释放掉了
 *
 * @author leofee
 * @date 2021/1/16
 */
public class Join02 {

    public static void main(String[] args) throws InterruptedException {
        Thread_A threadA = new Thread_A();
        threadA.setName("A");

        Thread_B threadB = new Thread_B(threadA, "B");
        threadB.start();
    }

    static class Thread_A extends Thread {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "开始执行......");
            // 锁对象是 Thread_A 本身
            synchronized (this) {
                for (int i = 0; i < 5; i++) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " " + i);
                }
            }
            System.out.println(Thread.currentThread().getName() + "执行结束......");
        }
    }

    static class Thread_B extends Thread {

        final Thread_A threadA;

        public Thread_B(Thread_A threadA, String name) {
            this.threadA = threadA;
            super.setName(name);
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "开始执行......");
            synchronized (threadA) {
                threadA.start();
                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " " + i);
                    if (i == 5) {
                        try {
                            System.out.println("-----A.join------A的状态为：" + threadA.getState());
                            // 这个时候B线程会释放锁
                            threadA.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            System.out.println(Thread.currentThread().getName() + "执行结束......");
        }
    }
}
