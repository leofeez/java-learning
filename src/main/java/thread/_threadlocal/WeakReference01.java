package thread._threadlocal;

/**
 * 引用的类型：
 * 强，软，弱，虚。
 *
 *
 * 弱引用一般用在容器中，WeakHashMap
 *
 *
 *
 * @author leofee
 * @date 2020/12/8
 */
public class WeakReference01 {

    public static void main(String[] args) {

        Person person = new Person();
        System.out.println("创建对象" + person);

        person = null;

        System.gc();
    }
}
