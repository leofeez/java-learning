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
