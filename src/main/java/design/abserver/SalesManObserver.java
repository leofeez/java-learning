package design.abserver;

/**
 * @author leofee
 */
public class SalesManObserver implements Observer {
    @Override
    public void observe() {
        System.out.println("售票员开始售票......");
    }
}
