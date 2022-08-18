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
        // 将生成的代理类写入到文件中
        // JDK1.8及以前的版本
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        // JDK1.8以后的版本
        // System.getProperties().put("jdk.proxy.ProxyGenerator.saveGeneratedFiles", "true");

        Customer customer = new Customer();
        Drive drive = (Drive)Proxy.newProxyInstance(Customer.class.getClassLoader(),
                Customer.class.getInterfaces(), new JdkDriverInvocationHandler(customer));
        drive.drive();
    }
}
