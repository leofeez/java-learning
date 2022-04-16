package design.factory.abstractfactory;

/**
 * @author leofee
 */
public class TestAbstractFactory {

    public static void main(String[] args) {
        IFactory dellFactory = new DellFactory();
        dellFactory.createKeyboard().input(" hello world");
        dellFactory.createMouse().click();


        IFactory logitechFactory = new LogitechFactory();
        logitechFactory.createKeyboard().input(" hello world");
        logitechFactory.createMouse().click();
    }
}
