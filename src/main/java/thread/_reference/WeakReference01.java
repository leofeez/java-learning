package thread._reference;

import thread._threadlocal.Person;

import java.lang.ref.WeakReference;

/**
 * 弱引用一遇到gc就会被回收
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
        WeakReference<Person> weakReference = new WeakReference<>(new Person("李四"));

        System.out.println("垃圾回收前：" + weakReference.get());

        System.gc();

        System.out.println("垃圾回收后：" + weakReference.get());
    }
}
