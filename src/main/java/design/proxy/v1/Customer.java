package design.proxy.v1;

/**
 * @author leofee
 */
public class Customer implements Drive {

    @Override
    public void drive() {
        System.out.println("开车回家了");
    }
}
