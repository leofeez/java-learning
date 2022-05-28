package design.proxy.v3;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * CGLIB 动态代理
 *
 * @author leofee
 */
public class CglibDriver implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("客户叫来了CGLIB代驾");
        return methodProxy.invokeSuper(o, objects);
    }
}
