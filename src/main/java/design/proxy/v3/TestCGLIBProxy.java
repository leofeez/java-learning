package design.proxy.v3;

import design.proxy.v1.Customer;
import design.proxy.v1.Drive;
import design.proxy.v2.JdkDriverInvocationHandler;
import net.sf.cglib.proxy.Enhancer;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Proxy;

/**
 * @author leofee
 */
public class TestCGLIBProxy {

    public static void main(String[] args) throws UnsupportedEncodingException {
        Enhancer enhancer = new Enhancer();
        // 设置代理类的父类
        enhancer.setSuperclass(Customer.class);
        // 设置方法的回调
        enhancer.setCallback(new CglibDriver());
        // 创建代理对象
        Customer customer = (Customer) enhancer.create();
        // 执行代理方法
        customer.drive();
    }
}
