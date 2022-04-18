package design.abserver;

/**
 * @author leofee
 */
public class SafeManObserver implements Observer {
    @Override
    public void observe() {
        System.out.println("安全员开始检查安全带......");
    }
}
