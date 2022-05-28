package design.proxy.v2;

import design.proxy.v1.Drive;
import design.proxy.v1.Customer;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Proxy;

/**
 * @author leofee
 */
public class TestJDKProxy {

    public static void main(String[] args) throws UnsupportedEncodingException {
        Customer customer = new Customer();
        Drive drive = (Drive)Proxy.newProxyInstance(Customer.class.getClassLoader(),
                Customer.class.getInterfaces(), new JdkDriverInvocationHandler(customer));
        drive.drive();
    }
}
