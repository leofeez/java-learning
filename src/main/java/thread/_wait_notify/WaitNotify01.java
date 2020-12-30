package thread._wait_notify;

/**
 * @author leofee
 * @date 2020/12/30
 */
public class WaitNotify01 {

    public static void main(String[] args) throws InterruptedException {
        WaitNotify01 object = new WaitNotify01();

        // wait() 和 notify()方法调用前都需要先获得锁，否则会抛出`IllegalMonitorStateException`
//        object.wait();
        object.notify();
    }
}
