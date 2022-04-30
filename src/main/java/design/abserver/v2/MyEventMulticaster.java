package design.abserver.v2;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author leofee
 */
public class MyEventMulticaster {

    AtomicInteger adder = new AtomicInteger(0);
    /**
     * 利用线程池去执行事件
     */
    ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 4,
            3600, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000),
            (r) -> new Thread(r, "MyEventMulticaster-thread-" + adder.incrementAndGet()),
            new ThreadPoolExecutor.CallerRunsPolicy());

    private final Set<MyEventListener<?>> listeners = new HashSet<>();

    public void addListener(MyEventListener<?> listener) {
        listeners.add(listener);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void multicastEvent(BaseEvent event) {
        for (MyEventListener listener : listeners) {
            if (supportEvent(listener, event)) {
                executor.execute(() -> listener.onEventPublish(event));
            }
        }
    }

    /**
     * 根据 event 类型判断当前 listener 是否应该监听该事件
     *
     * @param listener 监听器
     * @param event    事件
     * @return true 支持 / false 不支持
     */
    private boolean supportEvent(MyEventListener<?> listener, BaseEvent event) {

        Class<?>[] interfaces = listener.getClass().getInterfaces();

        Type[] genericSuperclass = listener.getClass().getGenericInterfaces();
        for (Type superclass : genericSuperclass) {
            if (superclass instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) superclass;
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                for (Type actualTypeArgument : actualTypeArguments) {
                    if (event.getClass().getName().equals(actualTypeArgument.getTypeName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


}
