package design.proxy.v1;

/**
 * @author leofee
 */
public class TestProxy {

    public static void main(String[] args) {
        // 顾客
        Customer customer = new Customer();
        // 代驾小哥
        Driver driver = new Driver(customer);
        // 客户喝酒啦，需要代驾开车回家
        driver.drive();
    }
}
