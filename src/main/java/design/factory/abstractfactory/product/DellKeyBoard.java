package design.factory.abstractfactory.product;

/**
 * @author leofee
 */
public class DellKeyBoard extends Keyboard {

    @Override
    public void input(String args) {
        System.out.println("dell keyboard reading " + args);
    }
}
