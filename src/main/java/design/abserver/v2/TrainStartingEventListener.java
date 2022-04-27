package design.abserver.v2;

/**
 * @author leofee
 */
public class TrainStartingEventListener implements MyEventListener<TrainStartingEvent> {

    @Override
    public void onEventPublish(TrainStartingEvent event) {
        Train source = (Train) event.getSource();
        source.running();
        System.out.println("售票员开始检票......");
    }
}
