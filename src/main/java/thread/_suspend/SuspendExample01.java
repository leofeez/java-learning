package thread._suspend;

/**
 * {@code suspend()} 方法可以暂停线程的运行，然后使用 {@code resume()} 恢复线程的运行。
 *
 * @author leofee
 * @date 2020/12/6
 */
public class SuspendExample01 extends Thread {

    private int i;

    public static void main(String[] args) throws InterruptedException {
        SuspendExample01 t = new SuspendExample01();
        t.start();

        Thread.sleep(500);

        // 线程暂停
        t.suspend();
        System.out.println("第一次暂停时：" + t.i);

        Thread.sleep(500);

        System.out.println("第一次暂停后：" + t.i);

        // 恢复运行
        t.resume();

        Thread.sleep(500);

        // 线程暂停
        t.suspend();
        System.out.println("第二次暂停时：" + t.i);

        Thread.sleep(500);

        System.out.println("第二次暂停后：" + t.i);

        System.exit(-1);

    }


    @Override
    public void run() {
        while (true) {
            i++;
            System.out.println(i);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
