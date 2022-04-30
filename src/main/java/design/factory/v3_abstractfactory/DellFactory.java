package design.factory.v3_abstractfactory;

import design.factory.product.DellKeyBoard;
import design.factory.product.DellMouse;
import design.factory.product.Keyboard;
import design.factory.product.Mouse;

/**
 * @author leofee
 */
public class DellFactory implements IFactory {

    @Override
    public Keyboard createKeyboard() {
        return new DellKeyBoard();
    }

    @Override
    public Mouse createMouse() {
        return new DellMouse();
    }

}
