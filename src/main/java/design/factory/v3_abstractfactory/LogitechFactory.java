package design.factory.v3_abstractfactory;

import design.factory.product.Keyboard;
import design.factory.product.LogitechKeyBoard;
import design.factory.product.LogitechMouse;
import design.factory.product.Mouse;

/**
 * @author leofee
 */
public class LogitechFactory implements IFactory {

    @Override
    public Keyboard createKeyboard() {
        return new LogitechKeyBoard();
    }

    @Override
    public Mouse createMouse() {
        return new LogitechMouse();
    }

}
