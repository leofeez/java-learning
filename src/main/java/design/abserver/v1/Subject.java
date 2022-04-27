package design.abserver.v1;

import java.util.ArrayList;
import java.util.List;

/**
 * 被观察者抽象类，被观察者拥有观察者的清单
 *
 * @author leofee
 */
public abstract class Subject {

    /**
     * 观察者列表
     * 只依赖接口不依赖具体实现类，可以避免与实现类强耦合
     */
    List<Observer> observerList = new ArrayList<>();

    public void addObserver(Observer observer) {
        observerList.add(observer);
    }

    public void deleteObserver(Observer observer) {
        observerList.remove(observer);
    }

    /**
     * 通知所有的观察者
     */
    public void notifyAllObservers() {
        for (Observer observer : observerList) {
            observer.observe();
        }
    }
}
