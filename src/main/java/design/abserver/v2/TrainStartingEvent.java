package design.abserver.v2;

/**
 * @author leofee
 */
public class TrainStartingEvent extends BaseEvent {

    public TrainStartingEvent(Train train) {
        super(train);
    }
}
