package design.proxy.v1;

/**
 * @author leofee
 */
public class TestProxy {

    public static void main(String[] args) {
        // 顾客
        Customer customer = new Customer();
        // 外卖小哥
        Driver driver = new Driver(customer);
        // 外卖小哥代客户买东西
        driver.drive();
    }
}
