package design.abserver.v2;

/**
 * @author leofee
 */
public abstract class BaseEvent {

    private Object source;

    public BaseEvent(Object source) {
        this.source = source;
    }

    public Object getSource() {
        return this.source;
    }
}
