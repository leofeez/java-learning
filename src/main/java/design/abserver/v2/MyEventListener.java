package design.abserver.v2;

import java.util.EventListener;

/**
 * @author leofee
 */
public interface MyEventListener<T extends BaseEvent> extends EventListener {

    void onEventPublish(T event);
}
