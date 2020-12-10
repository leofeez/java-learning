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
        Person person = new Person("李四");
        WeakReference<Person> weakReference = new WeakReference<>(person);
        person = null;

        WeakReference<Person> weakReference2 = new WeakReference<>(new Person("王五"));

        System.out.println("垃圾回收前1：" + weakReference.get());
        System.out.println("垃圾回收前2：" + weakReference2.get());


        System.gc();

        System.out.println("垃圾回收后1：" + weakReference.get());
        System.out.println("垃圾回收后2：" + weakReference2.get());
    }
}
