package thread;

import java.util.concurrent.Executors;

/**
 * @author leofee
 * @date 2020/12/1
 */
public class CreateThread {

    /**
     * 通过继承Thread来创建线程
     */
    private static class MyThread extends Thread {

        @Override
        public void run() {
            System.out.println("new thread extends Thread, " + Thread.currentThread().getName());
        }
    }

    /**
     * 实现Runnable接口
     */
    private static class MyRunnable implements Runnable {

        @Override
        public void run() {
            System.out.println("new thread implements Runnable, " + Thread.currentThread().getName());
        }
    }

    /**
     * 创建线程的三种方式
     * <ul>
     *     <li>继承Thread，重写run()方法
     *     <li>实现Runnable，实现run()方法, 推荐，因为java只能单继承，所以实现接口可以避免局限性
     *     <li>利用lambda表达式，实现run()方法/利用线程池 {@code Executors.newCachedThread()}
     *
     */
    public static void main(String[] args) {
        new MyThread().start();

        new Thread(new MyRunnable()).start();

        new Thread(new Thread(new MyRunnable())).start();

        new Thread(() -> System.out.println("new Thread with lambda")).start();

        Executors.newCachedThreadPool().submit(() -> System.out.println("executors new thread"));

        System.out.println("main thread end");
    }
}
