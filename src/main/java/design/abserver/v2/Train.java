package design.abserver.v2;

/**
 * @author leofee
 */
public class Train {

    private String name;

    public Train(String name) {
        this.name = name;
    }

    public void running() {
        System.out.println(Thread.currentThread().getName() + ": 复兴号" + name + " running");
    }

    public void stop() {
        System.out.println(Thread.currentThread().getName() + ": 复兴号" + name + " stop");
    }
}
