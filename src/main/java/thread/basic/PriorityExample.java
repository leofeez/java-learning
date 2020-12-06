package thread.basic;

/**
 * 线程优先级
 *
 * @author leofee
 * @date 2020/12/6
 */
public class PriorityExample extends Thread {

    private static int count = 1;

    public PriorityExample(String name) {
        super(name);
    }

    public static void main(String[] args) {

        System.out.println("main thread 优先级为：" + Thread.currentThread().getPriority());

        // 在主线程中创建A线程
        new PriorityExample("A线程").start();

    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "优先级为：" + Thread.currentThread().getPriority());
        if (count > 0) {
            count--;
            new PriorityExample("B线程").start();
        }
    }
}
