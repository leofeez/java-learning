package thread._reference;

import thread._threadlocal.Person;

import java.io.IOException;

/**
 * @author leofee
 * @date 2020/12/8
 */
public class NormalReference {

    public static void main(String[] args) throws IOException {
        Person person = new Person();
        person = null;

        System.gc();

        System.in.read();

    }
}
