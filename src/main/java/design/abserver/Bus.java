package design.abserver;

/**
 * @author leofee
 */
public class Bus extends Subject {

    public void running() {
        System.out.println("公共汽车发车了.......");

        // 通知观察者执行对应的行为
        notifyAllObservers();
    }
}
