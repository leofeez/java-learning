package design.abserver.v2;

import java.util.concurrent.TimeUnit;

/**
 * @author leofee
 */
public class TestListener {
    static MyEventMulticaster eventMulticaster;
    static {
        eventMulticaster = new MyEventMulticaster();
        eventMulticaster.addListener(new TrainStartingEventListener());
        eventMulticaster.addListener(new TrainStopEventListener());
    }

    public static void main(String[] args) throws InterruptedException {
        Train train = new Train("G8080");

        // 火车开动
        TrainStartingEvent event = new TrainStartingEvent(train);
        eventMulticaster.multicastEvent(event);

        TimeUnit.SECONDS.sleep(5);

        // 火车停止
        TrainStopEvent event2 = new TrainStopEvent(train);
        eventMulticaster.multicastEvent(event2);
    }
}
