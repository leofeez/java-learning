package design.strategy;

/**
 * @author leofee
 */
public interface Keyboard extends Strategy<Keyboard.KeyBordBrand> {

    @Override
    default KeyBordBrand identity() {
        return brand();
    }

    enum KeyBordBrand {
        LOGITECH,
        DELL
    }

    KeyBordBrand brand();

    void input(String in);


    class DellKeyBoard implements Keyboard {

        @Override
        public Keyboard.KeyBordBrand brand() {
            return Keyboard.KeyBordBrand.DELL;
        }

        @Override
        public void input(String args) {
            System.out.println("dell keyboard reading " + args);
        }
    }
}


