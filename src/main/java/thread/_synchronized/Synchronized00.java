package thread._synchronized;

/**
 * @author leofee
 */
public class Synchronized00 {

    public static void main(String[] args) {
        Synchronized00 obj = new Synchronized00();
        obj.println();
    }

    public void println() {
        synchronized (this) {
            System.out.println("Hello world!");
        }
    }

    public synchronized void println2() {
        System.out.println("Hello world!");
    }
}
