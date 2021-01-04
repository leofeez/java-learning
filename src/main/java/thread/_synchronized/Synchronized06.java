package thread._synchronized;

/**
 * @author leofee
 */
public class Synchronized06 {

    public static void main(String[] args) {
        synchronized (Synchronized06.class) {
            System.out.println("synchronized");
        }

        print();
    }

    private static void print() {
        System.out.println("print");
    }
}
