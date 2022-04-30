package design.factory.v3_abstractfactory;

/**
 * @author leofee
 */
public class TestAbstractFactory {

    public static void main(String[] args) {
        IFactory dellFactory = new DellFactory();
        dellFactory.createKeyboard().input(" hello dell");
        dellFactory.createMouse().click();


        IFactory logitechFactory = new LogitechFactory();
        logitechFactory.createKeyboard().input(" hello logitech");
        logitechFactory.createMouse().click();
    }
}
