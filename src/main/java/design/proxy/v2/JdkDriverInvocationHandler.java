package design.proxy.v2;

import design.proxy.v1.Drive;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * jdk 动态代理
 *
 * @author leofee
 */
public class JdkDriverInvocationHandler implements InvocationHandler {

    Drive customer;

    public JdkDriverInvocationHandler(Drive customer) {
        this.customer = customer;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("客户叫来了JDK代驾");
        return method.invoke(customer, args);
    }
}
