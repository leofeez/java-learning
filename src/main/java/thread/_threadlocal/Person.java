package thread._threadlocal;

/**
 * @author leofee
 * @date 2020/12/8
 */
public class Person {

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println(this + "被回收了");
    }
}
