package design.factory.abstractfactory;

import design.factory.abstractfactory.product.Keyboard;
import design.factory.abstractfactory.product.LogitechKeyBoard;
import design.factory.abstractfactory.product.LogitechMouse;
import design.factory.abstractfactory.product.Mouse;

/**
 * @author leofee
 */
public class LogitechFactory implements IFactory {

    public Keyboard createKeyboard() {
        return new LogitechKeyBoard();
    }

    public Mouse createMouse() {
        return new LogitechMouse();
    }

}
