package design.abserver;

/**
 * @author leofee
 */
public class TestObserver {

    public static void main(String[] args) {
        Bus bus = new Bus();
        bus.addObserver(new SafeManObserver());
        bus.addObserver(new SalesManObserver());


        bus.running();
    }
}
