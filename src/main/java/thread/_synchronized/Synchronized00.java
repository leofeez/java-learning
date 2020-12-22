package thread._synchronized;

/**
 * @author leofee
 */
public class Synchronized00 {


    public synchronized void print() {
        System.out.println("Hello world!");
    }

    public void println() {
        synchronized (this) {
            System.out.println("Hello world!");
        }
    }
}
