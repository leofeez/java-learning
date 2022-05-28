package design.proxy.v1;

/**
 * @author leofee
 */
public class Driver implements Drive {

    Customer customer;

    public Driver(Customer customer) {
        this.customer = customer;
    }

    @Override
    public void drive() {
        System.out.println("客户叫来了代驾");
        customer.drive();
    }
}
