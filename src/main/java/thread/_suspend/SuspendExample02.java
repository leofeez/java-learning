package thread._suspend;

/**
 * suspend缺点：独占！
 * 如果使用不当，容易造成公共的同步对象的占用，使得其他线程无法访问公共的同步对象。
 *
 * @author leofee
 * @date 2020/12/6
 */
public class SuspendExample02 extends Thread {

    private int i;

    public static void main(String[] args) {
        SuspendExample02 t = new SuspendExample02();
        t.start();

        // 锁被独占，永远结束不了
        t.suspend();

        System.out.println("main thread end !");
    }


    @Override
    public void run() {
        while (true) {
            i++;
        }
    }
}
