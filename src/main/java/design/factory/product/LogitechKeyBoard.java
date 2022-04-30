package design.factory.product;

/**
 * @author leofee
 */
public class LogitechKeyBoard extends Keyboard {

    @Override
    public void input(String args) {
        System.out.println("Logitech keyboard reading" + args);
    }
}
