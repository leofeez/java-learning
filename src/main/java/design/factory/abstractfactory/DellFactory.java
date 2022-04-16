package design.factory.abstractfactory;

import design.factory.abstractfactory.product.DellKeyBoard;
import design.factory.abstractfactory.product.DellMouse;
import design.factory.abstractfactory.product.Keyboard;
import design.factory.abstractfactory.product.Mouse;

/**
 * @author leofee
 */
public class DellFactory implements IFactory {

    public Keyboard createKeyboard() {
        return new DellKeyBoard();
    }

    public Mouse createMouse() {
        return new DellMouse();
    }

}
