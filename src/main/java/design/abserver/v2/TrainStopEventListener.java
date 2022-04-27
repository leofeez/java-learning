package design.abserver.v2;

/**
 * @author leofee
 */
public class TrainStopEventListener implements MyEventListener<TrainStopEvent> {

    @Override
    public void onEventPublish(TrainStopEvent event) {
        Train source = (Train) event.getSource();
        source.stop();
        System.out.println("乘客出站了......");
    }
}
