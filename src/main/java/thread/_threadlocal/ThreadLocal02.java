package thread._threadlocal;

/**
 * @author leofee
 * @date 2020/12/7
 */
public class ThreadLocal02 {

    private static ThreadLocal<Person> TL = new ThreadLocal<>();

    public static void main(String[] args) {

        Person person = new Person("张三");
        TL.set(person);
        person = null;

        System.out.println(TL.get());

        System.gc();

        System.out.println(TL.get());

        TL.remove();

        System.out.println(TL.get());
    }
}
